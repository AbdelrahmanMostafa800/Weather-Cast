package com.example.weathercast.db.todayweather

import com.example.weathercast.data.pojo.AlertItem
import com.example.weathercast.data.pojo.City
import com.example.weathercast.data.pojo.CurrentWeatherData
import com.example.weathercast.data.pojo.ForcastWeatherData
import kotlinx.coroutines.flow.Flow

interface TodayWeatherLocallDataSourceInterface {
    /*suspend fun insertAlarmItem(alertItem: AlertItem)
    suspend fun deleteAlarm(id: Int)
    fun getAllAlarms(): Flow<List<AlertItem>>*/
    suspend fun insertForecastWeatherData(forecastWeatherData: ForcastWeatherData)
    suspend  fun insertCurrentWeatherData(currentWeatherData: CurrentWeatherData)
    suspend  fun insertCity(city: City)
    suspend  fun getAllForecastWeatherData(): List<ForcastWeatherData>
    suspend  fun getAllCurrentWeatherData(): List<CurrentWeatherData>
    suspend  fun getAllCities(): List<City>
}
