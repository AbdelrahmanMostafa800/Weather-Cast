package com.example.weathercast.data.localdatasource

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencelLocationData private constructor(private val context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("Location", Context.MODE_PRIVATE)
    private val sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()

    companion object {
        private var instance: SharedPreferencelLocationData? = null

        @Synchronized
        fun getInstance(context: Context): SharedPreferencelLocationData {
            return instance ?: SharedPreferencelLocationData(context).also { instance = it }
        }
    }

    fun addLocation(latitude: Double?, longitude: Double?) {
        sharedPreferencesEditor.putString("Lat", latitude.toString())
        sharedPreferencesEditor.putString("Long", longitude.toString())
        sharedPreferencesEditor.apply()
    }
    fun getLocation(): String {
        val lat = sharedPreferences.getString("Lat", null)
        val long = sharedPreferences.getString("Long", null)
        return "$lat,$long"
    }
}