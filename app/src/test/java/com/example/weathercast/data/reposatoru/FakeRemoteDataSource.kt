package com.example.weathercast.data.reposatoru
import com.example.mvvm.network.RemoteDataSourceInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.weathercast.data.pojo.CurrentWeatherData
import com.example.weathercast.data.pojo.ForcastWeatherData

class FakeRemoteDataSource: RemoteDataSourceInterface {

    override suspend fun getForecastData(
        latitude: String,
        longitude: String,
        measurementUnit: String,
        language: String
    ): Flow<ForcastWeatherData> = flow {
        // Emit predefined fake forecast data
        emit(ForcastWeatherData(null,null,null, listOf(),null))
    }

    override suspend fun getCurrentData(
        latitude: String,
        longitude: String,
        measurementUnit: String,
        language: String
    ): CurrentWeatherData {
        // Return predefined fake current weather data
      return CurrentWeatherData(null,null,null,null,null,null,null,null,null,null,null,null,null)
    }
}
