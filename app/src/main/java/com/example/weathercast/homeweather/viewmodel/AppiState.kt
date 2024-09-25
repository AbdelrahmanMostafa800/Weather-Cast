package com.example.weathercast.homeweather.viewmodel

import com.example.weathercast.data.pojo.ForcastWeatherData
import com.example.weathercast.data.pojo.WeatherData
import kotlinx.coroutines.flow.Flow


sealed class AppiState {
    class Success(val data:ForcastWeatherData ) : AppiState()
    class Failure(val message: String) : AppiState()
    object Loading : AppiState()
    object Empty : AppiState()
}