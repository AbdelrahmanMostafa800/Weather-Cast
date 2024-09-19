package com.example.lab1.retrofitapi


import com.example.weathercast.data.pojo.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("forecast?lat={lat}&lon={lon} & appid = BuildConfig.OPEN_WEATHER_MAP_API_KEY")
   suspend fun getForecastData(@Query("lat") lat: String, @Query("lon") lon: String): WeatherData

}
