package com.example.weathercast.data.reposatoru

import com.example.weathercast.data.localdatasource.WeatherLocalDataSourceInterface
import com.example.weathercast.data.pojo.AlertItem
import com.example.weathercast.data.pojo.City
import com.example.weathercast.data.pojo.CurrentWeatherData
import com.example.weathercast.data.pojo.ForcastWeatherData
import com.example.weathercast.data.pojo.Location
import com.example.weathercast.db.todayweather.TodayWeatherLocallDataSourceInterface
import kotlinx.coroutines.flow.Flow

class FakeTodayLocalDataSource:
    TodayWeatherLocallDataSourceInterface {
    override suspend fun insertForecastWeatherData(forecastWeatherData: ForcastWeatherData) {
        TODO("Not yet implemented")
    }

    override suspend fun insertCurrentWeatherData(currentWeatherData: CurrentWeatherData) {
        TODO("Not yet implemented")
    }

    override suspend fun insertCity(city: City) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllForecastWeatherData(): List<ForcastWeatherData> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllCurrentWeatherData(): List<CurrentWeatherData> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllCities(): List<City> {
        TODO("Not yet implemented")
    }

}