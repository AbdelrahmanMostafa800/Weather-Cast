package com.example.weathercast.map.view

import android.content.Intent
import android.graphics.Rect
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.db.LocationLocalDataSource
import com.example.mvvm.network.RemoteDataSource
import com.example.weathercast.viemodel.SharedPreferenceViewModel
import com.example.weathercast.viemodel.SharedPreferenceViewModelFactory
import com.example.weathercast.data.localdatasource.SharedPreferencelLocationData
import com.example.weathercast.data.localdatasource.WeatherLocalDataSource
import com.example.weathercast.data.pojo.Location
import com.example.weathercast.data.reposatoru.WeatherReposatory
import com.example.weathercast.databinding.ActivityMapBinding
import com.example.weathercast.homeweather.view.MainActivity
import com.example.weathercast.viemodel.DbViewModel
import com.example.weathercast.viemodel.DbViewModelFactory
import com.example.weathercast.viemodel.ToolBarTextViewModel
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import java.util.*

class MapActivity : AppCompatActivity() {
    lateinit var dbViewModel: DbViewModel
    private lateinit var binding: ActivityMapBinding
    lateinit var sharedPreferenceViewModel: SharedPreferenceViewModel
    private lateinit var geocoder: Geocoder
    private var previousMarker: Marker? = null
    var long: Double? =null
    var lat:Double?=null
    var isFavoritsFragment=false
    var address=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isFavoritsFragment=intent.getBooleanExtra("isFromFavorits",false)
        // Load the user agent to prevent throttling
        Configuration.getInstance().load(this, android.preference.PreferenceManager.getDefaultSharedPreferences(this))

        // Initialize the Geocoder
        geocoder = Geocoder(this, Locale.getDefault())

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupMap()
        val remoteDataSource = RemoteDataSource()
        val localRepository = WeatherLocalDataSource(SharedPreferencelLocationData.getInstance(this),
            LocationLocalDataSource.getInstance(this))
        val weatherReposatory = WeatherReposatory.getInstance(remoteDataSource, localRepository)
        val sharedViewModelFactory = SharedPreferenceViewModelFactory(weatherReposatory)
         sharedPreferenceViewModel =
            ViewModelProvider(this, sharedViewModelFactory).get(SharedPreferenceViewModel::class.java)
        val dBViewModelFactory = DbViewModelFactory(weatherReposatory)
        dbViewModel =
            ViewModelProvider(this, dBViewModelFactory).get(DbViewModel::class.java)
    }

    private fun setupMap() {
        binding.map.apply {
            setTileSource(TileSourceFactory.MAPNIK) // Use OpenStreetMap tiles
            setMultiTouchControls(true)
            getLocalVisibleRect(Rect())
            controller.setZoom(15) // Initial zoom level
            controller.setCenter(GeoPoint(40.7128, -74.0060)) // Initial center point (New York City)

            // Add a marker at the initial center point
            val initialMarker = Marker(this)
            initialMarker.position = GeoPoint(40.7128, -74.0060)
            overlays.add(initialMarker)

            // Set up a map event receiver to handle single tap events
            val mapEventReceiver = object : MapEventsReceiver {
                override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
                    p?.let {
                         lat = it.latitude
                         long = it.longitude
                        Log.d("TAG", "singleTapConfirmedHelper:${lat}/${long} ")
                        // Get the address from the coordinates
                        val addresses = geocoder.getFromLocation(lat!!, long!!, 1)
                         address = if (addresses?.isNotEmpty() == true) addresses.get(0)?.getAddressLine(0)
                             .toString() else ""


                        // Remove the previous marker
                        previousMarker?.let { marker ->
                            overlays.remove(marker)
                        }

                        // Add a new marker at the pressed location
                        val newMarker = Marker(this@apply)
                        newMarker.position = GeoPoint(lat!!, long!!)
                        overlays.add(newMarker)

                        // Update the previous marker reference
                        previousMarker = newMarker

                        controller.setCenter(GeoPoint(lat!!, long!!))
                        invalidate()
                    }
                    binding.choose.visibility = View.VISIBLE
                    return true
                }

                override fun longPressHelper(p: GeoPoint?): Boolean {
                    return false
                }
            }
            val mapEventsOverlay=MapEventsOverlay(mapEventReceiver)
            overlays.add(mapEventsOverlay)
        }
        binding.choose.setOnClickListener {
            if (lat != null && long != null) {
                if (isFavoritsFragment){

                    val location = Location(address = address, latitude = lat!!, longitude = long!!)
                    dbViewModel.insertLocation(location)
                }else{
                    sharedPreferenceViewModel.saveLocation(lat!!, long!!)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.map.onResume() // Refresh the map on resume
    }

    override fun onPause() {
        super.onPause()
        binding.map.onPause() // Pause the map on pause
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.map.onDetach() // Detach the map view to prevent memory leaks
    }
}