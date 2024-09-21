package com.example.lab1.retrofitapi


import com.example.weathercast.data.pojo.CurrentWeatherData
import com.example.weathercast.data.pojo.ForcastWeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("data/2.5/forecast")
    suspend fun getForecastData(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String = "metric",
        @Query("appid") appId: String
    ): ForcastWeatherData
    @GET("data/2.5/weather")
    suspend fun getCurrentWeathertData(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String = "metric",
        @Query("appid") appId: String
    ): CurrentWeatherData
}
