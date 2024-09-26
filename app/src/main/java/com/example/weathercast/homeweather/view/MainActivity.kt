package com.example.weathercast.homeweather.view

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.weathercast.MapActivity
import com.example.weathercast.R
import com.example.weathercast.SplashFragment
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    lateinit var navview: NavigationView
    lateinit var drawerLayout: DrawerLayout
    lateinit var navController : NavController
    lateinit var currentDestination: NavDestination

    lateinit var homeMenuItem: MenuItem
    lateinit var splashMenuItem: MenuItem

    var aCCESS_FINE_LOCATION:Boolean=false
    var aCCESS_COARSE_LOCATION:Boolean=false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)
        navview = findViewById(R.id.nav_view)
        drawerLayout = findViewById(R.id.main)

         homeMenuItem = navview.menu.findItem(R.id.homeFragment)
         splashMenuItem = navview.menu.findItem(R.id.splashFragment)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
         navController = findNavController(this, R.id.nav_host_fragment)
        setupWithNavController(navview, navController)
        currentDestination = navController.currentDestination!!
        Log.d("TAG", "Activity onCreate: start")
        if (checkPermission()) {
            Log.d("TAG", "Activity onCreate:checkPermission start")
            if (aCCESS_FINE_LOCATION){
                val intent=Intent(this,MapActivity::class.java)
                startActivity(intent)
               /* if (currentDestination.id == R.id.splashFragment) {
                    findNavController(this, R.id.nav_host_fragment).navigate(R.id.action_splashFragment_to_mapFragment)
                }*/
            }else if (aCCESS_COARSE_LOCATION){
                homeMenuItem.setVisible(true)
                splashMenuItem.setVisible(false)
                if (currentDestination.id == R.id.splashFragment) {
                    findNavController(this, R.id.nav_host_fragment).navigate(R.id.action_splashFragment_to_homeFragment2)
                }
            }

            if (isLocationEnabled()){
                Log.d("TAG", "Activity onCreate:isLocationEnabled if")
                Toast.makeText(this, "Permission granted and location is enabled", Toast.LENGTH_SHORT).show()


            }else{
                Log.d("TAG", "Activity onCreate:isLocationEnabled else")
                Toast.makeText(this, "Please enable location", Toast.LENGTH_SHORT).show()
            }
            Log.d("TAG", "Activity onCreate:checkPermission end")
        }else{
            Log.d("TAG", "Activity onCreate:checkPermission else")
            homeMenuItem.setVisible(false)
            splashMenuItem.setVisible(true)
            Log.d("TAG", "Activity onCreate:checkPermission else before if")
            if (currentDestination.id == R.id.homeFragment) {
                findNavController(this, R.id.nav_host_fragment).navigate(R.id.action_homeFragment_to_splashFragment2)
            }
            Log.d("TAG", "Activity onCreate:checkPermission else before ActivityCompat")
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION),
                2005)
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
                        val intent=Intent(this,MapActivity::class.java)
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
}