package com.example.weathercast.data.reposatoru

import com.example.weathercast.data.pojo.AlertItem
import com.example.weathercast.data.pojo.City
import com.example.weathercast.data.pojo.CurrentWeatherData
import com.example.weathercast.data.pojo.ForcastWeatherData
import com.example.weathercast.data.pojo.Location
import kotlinx.coroutines.flow.Flow

interface WeatherReposatoryInterface {
    suspend fun getForecastData(latitude: String, longitude: String, measurementUnit: String,language:String): Flow<ForcastWeatherData>
    suspend fun getCurrentData(
        latitude: String,
        longitude: String,
        measurementUnit: String,language:String
    ): CurrentWeatherData

     fun saveLocation(latitude: Double, longitude: Double):Int
     fun getLocation(): String?
    fun getfavLocations(): Flow<List<Location>>
    suspend fun deleteLocation(address: String)
    suspend fun insertLocation(location: Location)
    suspend fun addAlertItem(alertItem: AlertItem)
    suspend fun deleteAlertItem(id: Int)
    fun getAlertItems(): Flow<List<AlertItem>>
     fun setLanguage(lang: String)
    fun setWind(wind: String)

    fun setTemp(temp: String)

    fun setSettingLocation(location: String)
    fun getSettingLocation(): String
    fun getSettingWind(): String
    fun getSettingTemp(): String
    fun getSettingLanguage(): String

    suspend fun insertForecastWeatherData(forecastWeatherData: ForcastWeatherData)
    suspend fun insertCurrentWeatherData(currentWeatherData: CurrentWeatherData)
    suspend fun insertCity(city: City)
    suspend fun getAllForecastWeatherData():List<ForcastWeatherData>
    suspend fun getAllCurrentWeatherData(): List<CurrentWeatherData>
    suspend fun getAllCities():List<City>
}