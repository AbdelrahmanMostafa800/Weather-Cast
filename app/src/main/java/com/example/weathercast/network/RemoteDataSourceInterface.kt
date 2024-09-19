package com.example.mvvm.network

import com.example.weathercast.data.pojo.WeatherData


interface RemoteDataSourceInterface {
    suspend fun getAllProducts(lat: String, lon: String): WeatherData
}
