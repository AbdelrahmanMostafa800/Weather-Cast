package com.example.weathercast.data.reposatoru

import com.example.weathercast.data.pojo.CurrentWeatherData
import com.example.weathercast.data.pojo.ForcastWeatherData
import com.example.weathercast.data.pojo.Location
import kotlinx.coroutines.flow.Flow

interface WeatherReposatoryInterface {
    suspend fun getForecastData(latitude: String, longitude: String, measurementUnit: String): Flow<ForcastWeatherData>
    suspend fun getCurrentData(
        latitude: String,
        longitude: String,
        measurementUnit: String
    ): CurrentWeatherData

     fun saveLocation(latitude: Double, longitude: Double)
     fun getLocation(): String?
    fun getfavLocations(): Flow<List<Location>>
    suspend fun deleteLocation(address: String)
    suspend fun insertLocation(location: Location)
}