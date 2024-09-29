package com.example.weathercast.data.localdatasource

import com.example.weathercast.data.pojo.AlertItem
import com.example.weathercast.data.pojo.Location
import kotlinx.coroutines.flow.Flow

interface WeatherLocalDataSourceInterface {
    fun addLocation(latitude: Double?, longitude: Double?):Int
     fun getLocation(): String?
    fun getFavLocations(): Flow<List<Location>>
    suspend fun deleteLocation(address: String):Int
    suspend fun insertLocation(location: Location):Long

    suspend fun addAlertItem(alertItem: AlertItem)
    suspend fun deleteAlertItem(id: Int)
    fun getAlertItems(): Flow<List<AlertItem>>
     fun setLanguage(lang: String)
     fun setWind(wind: String)
     fun setTemp(temp: String)
     fun setSettingLocation(location: String)
    fun getSettingLocation(): String
    fun getTemp(): String
    fun getWind(): String
    fun getLanguage(): String
}