package com.example.weathercast.homeweather.view

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.weathercast.db.location.LocationLocalDataSource
import com.example.mvvm.network.RemoteDataSource
import com.example.weathercast.map.view.MapActivity
import com.example.weathercast.R
import com.example.weathercast.viemodel.SharedPreferenceViewModel
import com.example.weathercast.viemodel.SharedPreferenceViewModelFactory
import com.example.weathercast.data.localdatasource.SharedPreferencelLocationData
import com.example.weathercast.data.localdatasource.WeatherLocalDataSource
import com.example.weathercast.data.reposatoru.WeatherReposatory
import com.example.weathercast.db.alert.AlarmLocallDataSource
import com.example.weathercast.db.todayweather.TodayWeatherLocallDataSource
import com.example.weathercast.viemodel.SettingViewModel
import com.example.weathercast.viemodel.SettingViewModelFactory
import com.example.weathercast.viemodel.ToolBarTextViewModel
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale


class MainActivity : AppCompatActivity() {
    lateinit var navview: NavigationView
    lateinit var drawerLayout: DrawerLayout
    lateinit var navController : NavController
    lateinit var currentDestination: NavDestination
    lateinit var sharedPreferenceViewModel: SharedPreferenceViewModel
    lateinit var settingViewModel: SettingViewModel
    lateinit var homeMenuItem: MenuItem
    lateinit var splashMenuItem: MenuItem

    var aCCESS_FINE_LOCATION:Boolean=false
    var aCCESS_COARSE_LOCATION:Boolean=false

    private  val toolBarTextViewModel: ToolBarTextViewModel by viewModels()
    /*private val _toolbarTitleFlow = MutableSharedFlow<String>(replay = 1)
    val toolbarTitleFlow = _toolbarTitleFlow.asSharedFlow()*/



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val remoteDataSource = RemoteDataSource()
        val localRepository = WeatherLocalDataSource(
            SharedPreferencelLocationData.getInstance(this),
            LocationLocalDataSource.getInstance(this), AlarmLocallDataSource.getInstance(this))
        val weatherReposatory = WeatherReposatory.getInstance(remoteDataSource, localRepository,
            TodayWeatherLocallDataSource.getInstance(this))
        val viewModelFactory = SharedPreferenceViewModelFactory(weatherReposatory)
        sharedPreferenceViewModel =
            ViewModelProvider(this, viewModelFactory).get(SharedPreferenceViewModel::class.java)
        val settingViewModelFactory = SettingViewModelFactory(weatherReposatory)
        settingViewModel =
            ViewModelProvider(this, settingViewModelFactory).get(SettingViewModel::class.java)
        changeLanguage(settingViewModel.getSettingLanguage())
        val language=settingViewModel.getSettingLanguage()
        var isRtl=false
        if(language=="ar") isRtl=true
        setLocale(language, isRtl)
        setContentView(R.layout.activity_main)
//        toolBarTextViewModel = ViewModelProvider(this).get(ToolBarTextViewModel::class.java)
       /* val latitude = intent.getDoubleExtra("latitude", 0.0)
        val longitude = intent.getDoubleExtra("longitude", 0.0)
        if (latitude!=0.0 && longitude!=0.0){
            findNavController(
                this,
                R.id.nav_host_fragment
            ).navigate(R.id.action_splashFragment_to_homeFragment2)
        }else {*/


        navview = findViewById(R.id.nav_view)
        drawerLayout = findViewById(R.id.main)

        homeMenuItem = navview.menu.findItem(R.id.homeFragment)
        splashMenuItem = navview.menu.findItem(R.id.splashFragment)

            val toolbar: Toolbar = findViewById(R.id.toolbar)
            setSupportActionBar(toolbar)
        lifecycleScope.launch {
            toolBarTextViewModel.toolbarTitleFlow.collectLatest { title ->
                supportActionBar?.title = title
            }
        }
        navController = findNavController(this, R.id.nav_host_fragment)
            setupWithNavController(navview, navController)
            currentDestination = navController.currentDestination!!
            Log.d("TAG", "Activity onCreate: start")

            if (checkPermission()) {
                if (!isLocationEnabled()) {
                    Log.d("TAG", "Activity onCreate:isLocationEnabled if")
                    val intent= Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(intent)

                }
                Log.d("TAG", "Activity onCreate:checkPermission start")
                if (aCCESS_FINE_LOCATION) {
                    if(sharedPreferenceViewModel.getLocation()!="null,null"){
                        Log.d("TAG", "location: ${sharedPreferenceViewModel.getLocation()}")
                        homeMenuItem.setVisible(true)
                        splashMenuItem.setVisible(false)
                        if (currentDestination.id == R.id.splashFragment) {
//                            navController.popBackStack(R.id.splashFragment, true)
                            Log.d("TAG", "popBackStack:${currentDestination.id}, ${R.id.splashFragment}, ${R.id.homeFragment} ")
                            navController.navigate(R.id.action_splashFragment_to_homeFragment2)
                           // findNavController(this, R.id.nav_host_fragment).navigate(R.id.action_splashFragment_to_homeFragment2)
                        }

                    }else {
                        val intent = Intent(this, MapActivity::class.java)
                        startActivity(intent)
                    }
                    /* if (currentDestination.id == R.id.splashFragment) {
                    findNavController(this, R.id.nav_host_fragment).navigate(R.id.action_splashFragment_to_mapFragment)
                }*/
                } else if (aCCESS_COARSE_LOCATION) {
                    homeMenuItem.setVisible(true)
                    splashMenuItem.setVisible(false)
                    if (currentDestination.id == R.id.splashFragment) {
                        findNavController(
                            this,
                            R.id.nav_host_fragment
                        ).navigate(R.id.action_splashFragment_to_homeFragment2)
                    }
                }


                Log.d("TAG", "Activity onCreate:checkPermission end")
            } else {
                Log.d("TAG", "Activity onCreate:checkPermission else")
                homeMenuItem.setVisible(false)
                splashMenuItem.setVisible(true)
                Log.d("TAG", "Activity onCreate:checkPermission else before if")
                if (currentDestination.id == R.id.homeFragment) {
                    findNavController(
                        this,
                        R.id.nav_host_fragment
                    ).navigate(R.id.action_homeFragment_to_splashFragment2)
                }
                Log.d("TAG", "Activity onCreate:checkPermission else before ActivityCompat")
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ),
                    2005
                )
            }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            2005 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    // Permission denied
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
                        // Show explanation or rationale
                        Toast.makeText(this, "Location permission is required to provide accurate results", Toast.LENGTH_SHORT).show()


                    } else {
                        // Permission denied permanently, handle accordingly
                        Toast.makeText(this, "Location permission denied permanently", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d("TAG", "onRequestPermissionsResult: Permission granted ")
                    // Update the permission variables
                    aCCESS_FINE_LOCATION = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    aCCESS_COARSE_LOCATION = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

                    // Now you can check the permission variables
                    if (aCCESS_FINE_LOCATION){
                        Log.d("TAG", "onRequestPermissionsResult: aCCESS_FINE_LOCATION")
                        val intent=Intent(this, MapActivity::class.java)
                        startActivity(intent)
                        /*if (currentDestination.id == R.id.splashFragment) {
                            findNavController(this, R.id.nav_host_fragment).navigate(R.id.action_splashFragment_to_mapFragment)
                        }*/

                    }else if (aCCESS_COARSE_LOCATION){
                        Log.d("TAG", "onRequestPermissionsResult: aCCESS_COARSE_LOCATION")
                        if (currentDestination.id == R.id.splashFragment) {
                            homeMenuItem.setVisible(true)
                            splashMenuItem.setVisible(false)
                            findNavController(this, R.id.nav_host_fragment).navigate(R.id.action_splashFragment_to_homeFragment2)
                        }
                    }

                }
            }
        }
    }


    fun checkPermission(): Boolean {
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ){
            aCCESS_FINE_LOCATION=true
        }
        if ( ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            aCCESS_COARSE_LOCATION=true
        }
       /* return ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED*/
        return aCCESS_FINE_LOCATION || aCCESS_COARSE_LOCATION
    }
    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
    private fun changeLanguage(lang: String) {
        Log.d("lang", "changeLanguage: $lang")
        val locale = Locale(lang)
        Log.d("lang", "changeLanguage: $lang")
        Locale.setDefault(locale)
        val config = resources.configuration
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    drawerLayout.openDrawer(GravityCompat.START)
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun setLocale(language: String, isRtl: Boolean) {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        config.setLayoutDirection(locale)

        // Update the configuration
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}