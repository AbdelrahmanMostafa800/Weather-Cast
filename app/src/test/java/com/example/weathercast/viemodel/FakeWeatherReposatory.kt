package com.example.weathercast.viemodel

import com.example.weathercast.data.pojo.AlertItem
import com.example.weathercast.data.pojo.City
import com.example.weathercast.data.pojo.CurrentWeatherData
import com.example.weathercast.data.pojo.ForcastWeatherData
import com.example.weathercast.data.pojo.Location
import com.example.weathercast.data.reposatoru.WeatherReposatoryInterface
import kotlinx.coroutines.flow.Flow

class FakeWeatherReposatory(val fakelocation:String): WeatherReposatoryInterface {
    var fakelanguage = "en"
    var fakeTemp = "metric"

    override suspend fun getForecastData(
        latitude: String,
        longitude: String,
        measurementUnit: String,
        language: String
    ): Flow<ForcastWeatherData> {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentData(
        latitude: String,
        longitude: String,
        measurementUnit: String,
        language: String
    ): CurrentWeatherData {
        TODO("Not yet implemented")
    }

    override fun saveLocation(latitude: Double, longitude: Double): Int {
        return 1
    }

    override fun getLocation(): String {
       return fakelocation
    }

    override fun getfavLocations(): Flow<List<Location>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteLocation(address: String) {
        TODO("Not yet implemented")
    }

    override suspend fun insertLocation(location: Location) {
        TODO("Not yet implemented")
    }

    override suspend fun addAlertItem(alertItem: AlertItem) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAlertItem(id: Int) {
        TODO("Not yet implemented")
    }

    override fun getAlertItems(): Flow<List<AlertItem>> {
        TODO("Not yet implemented")
    }

    override fun setLanguage(lang: String) {
        fakelanguage = lang
    }

    override fun setWind(wind: String) {
        TODO("Not yet implemented")
    }

    override fun setTemp(temp: String) {
        fakeTemp=temp
    }

    override fun setSettingLocation(location: String) {
        TODO("Not yet implemented")
    }

    override fun getSettingLocation(): String {
        TODO("Not yet implemented")
    }

    override fun getSettingWind(): String {
        TODO("Not yet implemented")
    }

    override fun getSettingTemp(): String {
       return fakeTemp
    }

    override fun getSettingLanguage(): String {
        return fakelanguage
    }

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