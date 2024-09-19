package com.example.weathercast.data

import android.app.Application

class WeatherReposatory :WeatherReposatoryInterface{
    /*companion object {
        @Volatile
        private var INSTANCE: WeatherReposatory? = null

        fun getRepositoryInstance(app: Application): WeatherReposatory {
            return INSTANCE ?: synchronized(this) {
                WeatherReposatory().also {
                    INSTANCE = it
                }
            }
        }
    }*/
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