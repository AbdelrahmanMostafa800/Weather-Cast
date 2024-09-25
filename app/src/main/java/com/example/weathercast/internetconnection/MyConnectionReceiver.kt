package com.example.lab1.internetconnection

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.util.Log

class MyConnectionReceiver(private val callback: (Boolean) -> Unit) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("TAG", "onReceive: start")
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        val isConnected = activeNetwork?.isConnectedOrConnecting == true
        Log.d("TAG", "onReceive: end")

        callback(isConnected)  // Pass the network status to the callback
    }
}


/*class MyConnectionReceiver(private val onNetworkChange: OnNetworkChange) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
            onNetworkChange.onNetworkChange(true)
        } else {
            onNetworkChange.onNetworkChange(false)
        }
    }
}*/
