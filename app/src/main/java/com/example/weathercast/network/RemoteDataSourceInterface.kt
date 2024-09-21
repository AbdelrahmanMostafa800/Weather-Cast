package com.example.mvvm.network

import com.example.weathercast.data.pojo.CurrentWeatherData
import com.example.weathercast.data.pojo.ForcastWeatherData


interface RemoteDataSourceInterface {
    suspend fun getForecastData(latitude: String, longitude: String, measurementUnit: String): ForcastWeatherData
    suspend fun getCurrentData(
        latitude: String,
        longitude: String,
        measurementUnit: String
    ): CurrentWeatherData
}
