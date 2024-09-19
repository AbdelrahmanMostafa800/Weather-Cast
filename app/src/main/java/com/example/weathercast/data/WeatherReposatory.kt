package com.example.weathercast.data

import android.app.Application

class WeatherReposatory :WeatherReposatoryInterface{

    companion object {
        private var instance: WeatherReposatory? = null
        @Synchronized
        fun getInstance(): WeatherReposatory? {
            if (instance == null) {
                instance = WeatherReposatory()
            }
            return instance
        }
    }
}