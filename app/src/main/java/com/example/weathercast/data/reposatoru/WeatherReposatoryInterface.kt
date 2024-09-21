package com.example.weathercast.data.reposatoru

import com.example.weathercast.data.pojo.CurrentWeatherData
import com.example.weathercast.data.pojo.ForcastWeatherData

interface WeatherReposatoryInterface {
    suspend fun getForecastData(latitude: String, longitude: String, measurementUnit: String): ForcastWeatherData
    suspend fun getCurrentData(
        latitude: String,
        longitude: String,
        measurementUnit: String
    ): CurrentWeatherData
}