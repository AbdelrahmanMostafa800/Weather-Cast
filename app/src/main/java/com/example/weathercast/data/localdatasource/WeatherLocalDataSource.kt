package com.example.weathercast.data.localdatasource

import com.example.mvvm.db.LocationLocalDataSourceInterface
import com.example.weathercast.data.pojo.Location
import kotlinx.coroutines.flow.Flow

class WeatherLocalDataSource(
    private val sharedPreferenceDataLocation: SharedPreferencelLocationData,
    private val locationLocalDataSource: LocationLocalDataSourceInterface
):WeatherLocalDataSourceInterface {
    override fun addLocation(latitude: Double?, longitude: Double?) {
        sharedPreferenceDataLocation.addLocation(latitude, longitude)
    }

    override fun getLocation(): String {
        return sharedPreferenceDataLocation.getLocation()
    }

    override fun getFavLocations(): Flow<List<Location>> {
       return locationLocalDataSource.getFavLocation()
    }

    override suspend fun deleteLocation(address: String) {
        locationLocalDataSource.deleteLocation(address)
    }

    override suspend fun insertLocation(location: Location) {
        locationLocalDataSource.insertLocation(location)
    }

}