package com.example.mvvm.network

import com.example.lab1.retrofitapi.ApiInterface
import com.example.lab1.retrofitapi.RetrofitClient
import com.example.weathercast.data.pojo.WeatherData

class RemoteDataSource() : RemoteDataSourceInterface {
    private val apiService = RetrofitClient.retrofitInstance.create(ApiInterface::class.java)
    override suspend fun getAllProducts(lat: String, lon: String): WeatherData {
        try {
            val response = apiService.getForecastData(lat, lon)
            return response
        } catch (e: Exception) {
            throw e
        }
    }
}