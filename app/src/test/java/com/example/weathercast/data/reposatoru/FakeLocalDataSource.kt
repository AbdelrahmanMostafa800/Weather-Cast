package com.example.weathercast.data.reposatoru

import com.example.weathercast.data.localdatasource.WeatherLocalDataSourceInterface
import com.example.weathercast.data.pojo.AlertItem
import com.example.weathercast.data.pojo.Location
import kotlinx.coroutines.flow.Flow

class FakeLocalDataSource: WeatherLocalDataSourceInterface {
    override fun getWind(): String {
        TODO("Not yet implemented")
    }
    override fun addLocation(latitude: Double?, longitude: Double?): Int {
        TODO("Not yet implemented")
    }

    override fun getLocation(): String? {
        TODO("Not yet implemented")
    }

    override fun getFavLocations(): Flow<List<Location>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteLocation(address: String):Int {
      if (address == ""){
          return 0
      }else{
          return 1
      }
    }

    override suspend fun insertLocation(location: Location):Long {
        if (location.address == ""){
            return -1
        }
        else{
            return 1
        }
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
        TODO("Not yet implemented")
    }

    override fun setWind(wind: String) {
        TODO("Not yet implemented")
    }

    override fun setTemp(temp: String) {
        TODO("Not yet implemented")
    }

    override fun setSettingLocation(location: String) {
        TODO("Not yet implemented")
    }

    override fun getSettingLocation(): String {
        TODO("Not yet implemented")
    }

    override fun getTemp(): String {
        TODO("Not yet implemented")
    }


    override fun getLanguage(): String {
        TODO("Not yet implemented")
    }
}