package com.example.weathercast.data.reposatoru

import com.example.mvvm.network.RemoteDataSource
import com.example.mvvm.network.RemoteDataSourceInterface
import com.example.weathercast.data.pojo.CurrentWeatherData
import com.example.weathercast.data.pojo.ForcastWeatherData

class WeatherReposatory(
    private val weatherRemoteDataSource: RemoteDataSourceInterface
) : WeatherReposatoryInterface {

    companion object {
        private var instance: WeatherReposatory? = null
        @Synchronized
        fun getInstance(): WeatherReposatory? {
            if (instance == null) {
                instance = WeatherReposatory(
                    RemoteDataSource()
                )
            }
            return instance
        }
    }

    override suspend fun getForecastData(
        latitude: String,
        longitude: String,
        measurementUnit: String
    ): ForcastWeatherData {
       return weatherRemoteDataSource.getForecastData(latitude, longitude,measurementUnit)
    }
    override suspend fun getCurrentData(
        latitude: String,
        longitude: String,
        measurementUnit: String
    ): CurrentWeatherData {
        return weatherRemoteDataSource.getCurrentData(latitude, longitude,measurementUnit)
    }
}