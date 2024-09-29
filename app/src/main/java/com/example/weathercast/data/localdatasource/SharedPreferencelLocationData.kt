package com.example.weathercast.data.localdatasource

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencelLocationData private constructor(private val context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("Location", Context.MODE_PRIVATE)
    private val sharedPreferencesEditor: SharedPreferences.Editor = sharedPreferences.edit()

    private val settingSharedPreferences: SharedPreferences = context.getSharedPreferences("Setting", Context.MODE_PRIVATE)
    private val settingSharedPreferencesEditor: SharedPreferences.Editor = settingSharedPreferences.edit()

    companion object {
        private var instance: SharedPreferencelLocationData? = null

        @Synchronized
        fun getInstance(context: Context): SharedPreferencelLocationData {
            return instance ?: SharedPreferencelLocationData(context).also { instance = it }
        }
    }

    fun addLocation(latitude: Double?, longitude: Double?):Int {
        sharedPreferencesEditor.putString("Lat", latitude.toString())
        sharedPreferencesEditor.putString("Long", longitude.toString())
        sharedPreferencesEditor.apply()
        return 1
    }
    fun getLocation(): String {
        val lat = sharedPreferences.getString("Lat", null)
        val long = sharedPreferences.getString("Long", null)
        return "$lat,$long"
    }

    fun setLanguage(lang: String) {
        settingSharedPreferencesEditor.putString("lang", lang)
        settingSharedPreferencesEditor.apply()
    }

    fun setWind(wind: String) {
        settingSharedPreferencesEditor.putString("wind", wind)
        settingSharedPreferencesEditor.apply()
    }

    fun setTemp(temp: String) {
        settingSharedPreferencesEditor.putString("temp", temp)
        settingSharedPreferencesEditor.apply()
    }

    fun setSettingLocation(location: String) {
        settingSharedPreferencesEditor.putString("location", location)
        settingSharedPreferencesEditor.apply()
    }

    fun getLanguage(): String {
        return settingSharedPreferences.getString("lang", "en") ?: "en"
    }
    fun getWind(): String {
        return settingSharedPreferences.getString("wind", "metric") ?: "metric"
    }
    fun getTemp(): String {
        return settingSharedPreferences.getString("temp", "metric") ?: "metric"
    }
    fun getSettingLocation(): String {
        return settingSharedPreferences.getString("location", "gps") ?: "gps"
    }
}