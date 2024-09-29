package com.example.weathercast.db.todayweather



import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.weathercast.data.pojo.City
import com.example.weathercast.data.pojo.CurrentWeatherData
import com.example.weathercast.data.pojo.ForcastWeatherData
import com.example.weathercast.data.pojo.WeatherData
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset


@Database(entities = [ForcastWeatherData::class, WeatherData::class, CurrentWeatherData::class, City::class], version = 1)
abstract class TodayWeatherAppDataBase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

    companion object {
        private var instance: TodayWeatherAppDataBase? = null

        fun getInstance(context: Context): TodayWeatherAppDataBase {
            if (instance == null) {
                instance = Room.databaseBuilder(context, TodayWeatherAppDataBase::class.java, "weather_database")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }
}