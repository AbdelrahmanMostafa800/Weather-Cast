package com.example.weathercast

import android.graphics.Rect
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.weathercast.databinding.ActivityMapBinding
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import java.util.*

class MapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapBinding
    private lateinit var geocoder: Geocoder
    private var previousMarker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Load the user agent to prevent throttling
        Configuration.getInstance().load(this, android.preference.PreferenceManager.getDefaultSharedPreferences(this))

        // Initialize the Geocoder
        geocoder = Geocoder(this, Locale.getDefault())

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupMap()
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
                        binding.choose.visibility = View.VISIBLE
                        val lat = it.latitude
                        val long = it.longitude
                        Log.d("TAG", "singleTapConfirmedHelper:${lat}/${long} ")

                        // Remove the previous marker
                        previousMarker?.let { marker ->
                            overlays.remove(marker)
                        }

                        // Add a new marker at the pressed location
                        val newMarker = Marker(this@apply)
                        newMarker.position = GeoPoint(lat, long)
                        overlays.add(newMarker)

                        // Update the previous marker reference
                        previousMarker = newMarker

                        controller.setCenter(GeoPoint(lat, long))
                        invalidate()
                    }
                    return true
                }

                override fun longPressHelper(p: GeoPoint?): Boolean {
                    return false
                }
            }
            val mapEventsOverlay=MapEventsOverlay(mapEventReceiver)
            overlays.add(mapEventsOverlay)
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