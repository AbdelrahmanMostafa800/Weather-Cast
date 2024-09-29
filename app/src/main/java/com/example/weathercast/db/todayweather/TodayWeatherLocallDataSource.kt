package com.example.weathercast.db.todayweather

import android.content.Context
import androidx.room.Insert
import androidx.room.Query
import com.example.weathercast.data.pojo.AlertItem
import com.example.weathercast.data.pojo.City
import com.example.weathercast.data.pojo.CurrentWeatherData
import com.example.weathercast.data.pojo.ForcastWeatherData
import kotlinx.coroutines.flow.Flow

class TodayWeatherLocallDataSource(context: Context) : TodayWeatherLocallDataSourceInterface {
    var dao: WeatherDao =  TodayWeatherAppDataBase.getInstance(context).weatherDao()

    companion object {
        private var instance: TodayWeatherLocallDataSourceInterface? = null

        @Synchronized
        fun getInstance(context: Context): TodayWeatherLocallDataSourceInterface {
            if (instance == null) {
                instance = TodayWeatherLocallDataSource(context)
            }
            return instance!!
        }
    }

   override suspend fun insertForecastWeatherData(forecastWeatherData: ForcastWeatherData){
        dao.insertForecastWeatherData(forecastWeatherData)
    }
   override suspend fun insertCurrentWeatherData(currentWeatherData: CurrentWeatherData){
        dao.insertCurrentWeatherData(currentWeatherData)
    }


   override suspend fun insertCity(city: City){
        dao.insertCity(city)
    }

   override suspend fun getAllForecastWeatherData(): List<ForcastWeatherData>{
        return dao.getAllForecastWeatherData()
    }

  override suspend fun getAllCurrentWeatherData(): List<CurrentWeatherData>{
        return dao.getAllCurrentWeatherData()
    }

   override suspend fun getAllCities(): List<City>{
        return dao.getAllCities()
    }
}
