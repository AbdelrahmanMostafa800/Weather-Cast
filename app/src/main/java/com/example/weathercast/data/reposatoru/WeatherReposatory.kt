package com.example.weathercast.data.reposatoru

import android.content.Context
import com.example.mvvm.network.RemoteDataSource
import com.example.mvvm.network.RemoteDataSourceInterface
import com.example.weathercast.data.localdatasource.WeatherLocalDataSource
import com.example.weathercast.data.localdatasource.WeatherLocalDataSourceInterface
import com.example.weathercast.data.pojo.CurrentWeatherData
import com.example.weathercast.data.pojo.ForcastWeatherData
import com.example.weathercast.data.pojo.Location
import kotlinx.coroutines.flow.Flow

class WeatherReposatory(
    private val weatherRemoteDataSource: RemoteDataSourceInterface,
    private val WeatherLocalDataSource: WeatherLocalDataSourceInterface
) : WeatherReposatoryInterface {

    companion object {
        @Volatile
        private var instance: WeatherReposatory? = null

        fun getInstance(
            remoteDataSource: RemoteDataSourceInterface,
            localRepository: WeatherLocalDataSourceInterface
        ): WeatherReposatory {
            return instance ?: synchronized(this) {
                instance ?: WeatherReposatory(
                    remoteDataSource,
                    localRepository
                ).also { instance = it }
            }
        }
    }

    override suspend fun getForecastData(
        latitude: String,
        longitude: String,
        measurementUnit: String
    ): Flow<ForcastWeatherData> {
       return weatherRemoteDataSource.getForecastData(latitude, longitude,measurementUnit)
    }
    override suspend fun getCurrentData(
        latitude: String,
        longitude: String,
        measurementUnit: String
    ): CurrentWeatherData {
        return weatherRemoteDataSource.getCurrentData(latitude, longitude,measurementUnit)
    }

    override fun saveLocation(latitude: Double, longitude: Double) {
        WeatherLocalDataSource.addLocation(latitude, longitude)
    }

    override fun getLocation(): String? {
        return WeatherLocalDataSource.getLocation()
    }

    override fun getfavLocations(): Flow<List<Location>> {
       return WeatherLocalDataSource.getFavLocations()
    }

    override suspend fun deleteLocation(address: String) {
        WeatherLocalDataSource.deleteLocation(address)
    }

    override suspend fun insertLocation(location: Location) {
        WeatherLocalDataSource.insertLocation(location)
    }
}