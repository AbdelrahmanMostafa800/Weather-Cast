package com.example.weathercast.db.todayweather

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.weathercast.data.pojo.City
import com.example.weathercast.data.pojo.CurrentWeatherData
import com.example.weathercast.data.pojo.ForcastWeatherData
import kotlinx.coroutines.flow.Flow


@Dao
interface WeatherDao {
    @Insert
    suspend fun insertForecastWeatherData(forecastWeatherData: ForcastWeatherData)

    @Insert
    suspend fun insertCurrentWeatherData(currentWeatherData: CurrentWeatherData)

    @Insert
    suspend fun insertCity(city: City)

    @Query("SELECT * FROM forecast_weather_data")
    suspend fun getAllForecastWeatherData(): List<ForcastWeatherData>

    @Query("SELECT * FROM current_weather_data")
    suspend fun getAllCurrentWeatherData(): List<CurrentWeatherData>

    @Query("SELECT * FROM cities")
    suspend  fun getAllCities(): List<City>
}