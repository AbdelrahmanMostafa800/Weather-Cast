package com.example.weathercast.data.reposatoru

import com.example.weathercast.data.localdatasource.WeatherLocalDataSourceInterface
import com.example.weathercast.data.pojo.City
import com.example.weathercast.data.pojo.Clouds
import com.example.weathercast.data.pojo.Coord
import com.example.weathercast.data.pojo.ForcastWeatherData
import com.example.weathercast.data.pojo.Location
import com.example.weathercast.data.pojo.Main
import com.example.weathercast.data.pojo.Sys
import com.example.weathercast.data.pojo.WeatherData
import com.example.weathercast.data.pojo.Wind
import com.example.weathercast.db.todayweather.TodayWeatherLocallDataSourceInterface
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Test

class WeatherReposatoryTest{



    private  var weatherReposatory: WeatherReposatory
    private var weatherRemoteDataSource: FakeRemoteDataSource
    private  var weatherLocalDataSource: WeatherLocalDataSourceInterface
    private  var todaWeatherLocalDataSource: TodayWeatherLocallDataSourceInterface

     init{
        weatherRemoteDataSource =FakeRemoteDataSource()
        weatherLocalDataSource =FakeLocalDataSource()
         todaWeatherLocalDataSource = FakeTodayLocalDataSource()
        weatherReposatory = WeatherReposatory(weatherRemoteDataSource, weatherLocalDataSource,todaWeatherLocalDataSource)
    }
    @Test
    fun deleteLocation_returnzero()= runBlocking {
        val result = weatherLocalDataSource.deleteLocation("")
        assertThat(result, IsEqual(0))
    }
    @Test
    fun deleteLocation_returnone()= runBlocking {
        val result = weatherLocalDataSource.deleteLocation("cairo")
        assertThat(result, IsEqual(1))
    }

    @Test
    fun insertLocation_returnminusone()= runBlocking {
        val result = weatherLocalDataSource.insertLocation(Location("",0.0,0.0,))
        assertThat(result, IsEqual(-1))
    }
    @Test
    fun insertLocation_returnone()= runBlocking {
        val result = weatherLocalDataSource.insertLocation(Location("cairo",0.0,0.0,))
        assertThat(result, IsEqual(1))
    }

}