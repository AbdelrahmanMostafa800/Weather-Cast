package com.example.weathercast.data.localdatasource

import com.example.weathercast.db.location.LocationLocalDataSourceInterface
import com.example.weathercast.data.pojo.AlertItem
import com.example.weathercast.data.pojo.Location
import com.example.weathercast.db.alert.AlarmLocalDataSourceInterface
import kotlinx.coroutines.flow.Flow

class WeatherLocalDataSource(
    private val sharedPreferenceDataLocation: SharedPreferencelLocationData,
    private val locationLocalDataSource: LocationLocalDataSourceInterface,
    private val alertLocalDataSource: AlarmLocalDataSourceInterface
):WeatherLocalDataSourceInterface {
    override fun addLocation(latitude: Double?, longitude: Double?):Int {
        return sharedPreferenceDataLocation.addLocation(latitude, longitude)
    }

    override fun getLocation(): String {
        return sharedPreferenceDataLocation.getLocation()
    }

    override fun getFavLocations(): Flow<List<Location>> {
       return locationLocalDataSource.getFavLocation()
    }

    override suspend fun deleteLocation(address: String):Int {
       return locationLocalDataSource.deleteLocation(address)
    }

    override suspend fun insertLocation(location: Location):Long {
       return locationLocalDataSource.insertLocation(location)
    }

    override suspend fun addAlertItem(alertItem: AlertItem) {
        alertLocalDataSource.insertAlarmItem(alertItem)
    }
    override suspend fun deleteAlertItem(id: Int) {
        alertLocalDataSource.deleteAlarm(id)
    }
    override fun getAlertItems(): Flow<List<AlertItem>> {
        return alertLocalDataSource.getAllAlarms()
    }

    override fun setLanguage(lang: String) {
       sharedPreferenceDataLocation.setLanguage(lang)
    }

    override fun setWind(wind: String) {
       sharedPreferenceDataLocation.setWind(wind)
    }

    override fun setTemp(temp: String) {
        sharedPreferenceDataLocation.setTemp(temp)
    }

    override fun setSettingLocation(location: String) {
       sharedPreferenceDataLocation.setSettingLocation(location)
    }
    override fun getLanguage(): String {
        return sharedPreferenceDataLocation.getLanguage()
    }
    override fun getWind(): String {
        return sharedPreferenceDataLocation.getWind()
    }
    override fun getTemp(): String {
        return sharedPreferenceDataLocation.getTemp()
    }
    override fun getSettingLocation(): String {
        return sharedPreferenceDataLocation.getSettingLocation()
    }

}