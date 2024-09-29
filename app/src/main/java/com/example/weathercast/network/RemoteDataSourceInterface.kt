package com.example.mvvm.network

import com.example.weathercast.data.pojo.CurrentWeatherData
import com.example.weathercast.data.pojo.ForcastWeatherData
import kotlinx.coroutines.flow.Flow


interface RemoteDataSourceInterface {
    suspend fun getForecastData(latitude: String, longitude: String, measurementUnit: String,language:String): Flow<ForcastWeatherData>
    suspend fun getCurrentData(
        latitude: String,
        longitude: String,
        measurementUnit: String,language:String
    ): CurrentWeatherData
}
