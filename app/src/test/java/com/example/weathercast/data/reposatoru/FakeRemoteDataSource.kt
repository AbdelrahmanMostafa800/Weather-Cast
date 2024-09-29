package com.example.weathercast.data.reposatoru
import com.example.mvvm.network.RemoteDataSourceInterface
import com.example.weathercast.data.pojo.Clouds
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.weathercast.data.pojo.CurrentWeatherData
import com.example.weathercast.data.pojo.ForcastWeatherData
import com.example.weathercast.data.pojo.Main
import com.example.weathercast.data.pojo.Sys
import com.example.weathercast.data.pojo.Weather
import com.example.weathercast.data.pojo.WeatherData
import com.example.weathercast.data.pojo.Wind

class FakeRemoteDataSource: RemoteDataSourceInterface {

    override suspend fun getForecastData(
        latitude: String,
        longitude: String,
        measurementUnit: String,
        language: String
    ): Flow<ForcastWeatherData> = flow {
        // Emit predefined fake forecast data
        emit(dummyForecastData)
    }

    override suspend fun getCurrentData(
        latitude: String,
        longitude: String,
        measurementUnit: String,
        language: String
    ): CurrentWeatherData {
        // Return predefined fake current weather data
      return CurrentWeatherData(null,null,null,null,null,null,null,0,null,null,null,null,"null",2)
    }
    val dummyForecastData = ForcastWeatherData(
        id = 1,
        cod = "200",
        message = 0,
        cnt = 40,
        cityId = 1,
        list = listOf(
            WeatherData(
                dt = 1643723400,
                main = Main(
                    temp = 20.0,
                    feelsLike = 18.0,
                    tempMin = 18.0,
                    tempMax = 22.0,
                    pressure = 1013,
                    humidity = 60,
                    seaLevel = 1013,
                    grndLevel = 1013,
                    tempKf = 2.0
                ),
                weather = listOf(
                    Weather(
                        id = 800,
                        main = "Clear",
                        description = "clear sky",
                        icon = "01d"
                    )
                ),
                clouds = Clouds(
                    all = 0
                ),
                wind = Wind(
                    speed = 10.0,
                    deg = 270,
                    gust = 15.0
                ),
                visibility = 10000,
                pop = 0.0,
                sys = Sys(
                    pod = "d"
                ),
                dtTxt = "2022-02-01 12:00:00"
            ),
            WeatherData(
                dt = 1643724000,
                main = Main(
                    temp = 22.0,
                    feelsLike = 20.0,
                    tempMin = 20.0,
                    tempMax = 24.0,
                    pressure = 1013,
                    humidity = 50,
                    seaLevel = 1013,
                    grndLevel = 1013,
                    tempKf = 2.0
                ),
                weather = listOf(
                    Weather(
                        id = 800,
                        main = "Clear",
                        description = "clear sky",
                        icon = "01d"
                    )
                ),
                clouds = Clouds(
                    all = 0
                ),
                wind = Wind(
                    speed = 12.0,
                    deg = 270,
                    gust = 18.0
                ),
                visibility = 10000,
                pop = 0.0,
                sys = Sys(
                    pod = "d"
                ),
                dtTxt = "2022-02-01 13:00:00"
            )
        )
    )
}
