package com.example.weathercast.data.localdatasource

import com.example.weathercast.data.pojo.Location
import kotlinx.coroutines.flow.Flow

interface WeatherLocalDataSourceInterface {
    fun addLocation(latitude: Double?, longitude: Double?)
     fun getLocation(): String?
    fun getFavLocations(): Flow<List<Location>>
    suspend fun deleteLocation(address: String)
    suspend fun insertLocation(location: Location)
}