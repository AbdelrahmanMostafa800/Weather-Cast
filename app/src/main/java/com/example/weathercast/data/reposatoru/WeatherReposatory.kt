package com.example.weathercast.data.reposatoru

import com.example.mvvm.network.RemoteDataSourceInterface
import com.example.weathercast.data.localdatasource.WeatherLocalDataSourceInterface
import com.example.weathercast.data.pojo.AlertItem
import com.example.weathercast.data.pojo.City
import com.example.weathercast.data.pojo.CurrentWeatherData
import com.example.weathercast.data.pojo.ForcastWeatherData
import com.example.weathercast.data.pojo.Location
import com.example.weathercast.db.todayweather.TodayWeatherLocallDataSourceInterface
import kotlinx.coroutines.flow.Flow

class WeatherReposatory(
    private val weatherRemoteDataSource: RemoteDataSourceInterface,
    private val WeatherLocalDataSource: WeatherLocalDataSourceInterface,
    val todayWeatherLocallDataSource:TodayWeatherLocallDataSourceInterface
) : WeatherReposatoryInterface {

    companion object {
        @Volatile
        private var instance: WeatherReposatory? = null

        fun getInstance(
            remoteDataSource: RemoteDataSourceInterface,
            localRepository: WeatherLocalDataSourceInterface,
            todayWeatherLocallDataSource:TodayWeatherLocallDataSourceInterface
        ): WeatherReposatory {
            return instance ?: synchronized(this) {
                instance ?: WeatherReposatory(
                    remoteDataSource,
                    localRepository,
                    todayWeatherLocallDataSource
                ).also { instance = it }
            }
        }
    }

    override suspend fun getForecastData(
        latitude: String,
        longitude: String,
        measurementUnit: String,language:String
    ): Flow<ForcastWeatherData> {
       return weatherRemoteDataSource.getForecastData(latitude, longitude,measurementUnit,language)
    }
    override suspend fun getCurrentData(
        latitude: String,
        longitude: String,
        measurementUnit: String,language:String
    ): CurrentWeatherData {
        return weatherRemoteDataSource.getCurrentData(latitude, longitude,measurementUnit,language)
    }

    override fun saveLocation(latitude: Double, longitude: Double):Int {
      return  WeatherLocalDataSource.addLocation(latitude, longitude)
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

    override suspend fun addAlertItem(alertItem: AlertItem) {
        WeatherLocalDataSource.addAlertItem(alertItem)
    }

    override suspend fun deleteAlertItem(id: Int) {
        WeatherLocalDataSource.deleteAlertItem(id)
    }

    override fun getAlertItems(): Flow<List<AlertItem>> {
       return WeatherLocalDataSource.getAlertItems()
    }



    override fun setLanguage(lang: String) {
        WeatherLocalDataSource.setLanguage(lang)
    }

    override fun setWind(wind: String) {
        WeatherLocalDataSource. setWind(wind)
    }

    override fun setTemp(temp: String) {
        WeatherLocalDataSource.setTemp(temp)
    }

    override fun setSettingLocation(location: String) {
        WeatherLocalDataSource.setSettingLocation(location)
    }
    override fun getSettingLocation(): String {
        return WeatherLocalDataSource.getSettingLocation()
    }
    override fun getSettingWind(): String {
        return WeatherLocalDataSource.getWind()
    }
    override fun getSettingTemp(): String {
        return WeatherLocalDataSource.getTemp()
    }
    override fun getSettingLanguage(): String {
        return WeatherLocalDataSource.getLanguage()
    }

    override suspend fun insertForecastWeatherData(forecastWeatherData: ForcastWeatherData){
        todayWeatherLocallDataSource.insertForecastWeatherData(forecastWeatherData)
    }
    override suspend fun insertCurrentWeatherData(currentWeatherData: CurrentWeatherData){
        todayWeatherLocallDataSource.insertCurrentWeatherData(currentWeatherData)
    }


    override suspend fun insertCity(city: City){
        todayWeatherLocallDataSource.insertCity(city)
    }

    override suspend fun getAllForecastWeatherData(): List<ForcastWeatherData>{
        return todayWeatherLocallDataSource.getAllForecastWeatherData()
    }

    override suspend fun getAllCurrentWeatherData(): List<CurrentWeatherData>{
        return todayWeatherLocallDataSource.getAllCurrentWeatherData()
    }

    override suspend fun getAllCities(): List<City>{
        return todayWeatherLocallDataSource.getAllCities()
    }

}