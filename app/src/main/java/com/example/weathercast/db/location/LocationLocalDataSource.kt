package com.example.weathercast.db.location

import android.content.Context
import com.example.weathercast.data.pojo.Location
import kotlinx.coroutines.flow.Flow

class LocationLocalDataSource(context: Context) : LocationLocalDataSourceInterface {
    var dao: LocationDAO =  LocationAppDataBase.getInstance(context)?.getLocationDAO()!!
    override suspend fun insertLocation(location: Location):Long {
        return dao.insertLocation(location)
    }

    override suspend fun deleteLocation(address: String):Int {
         return  dao.deleteLocation(address)
    }

    override  fun getFavLocation(): Flow<List<Location>> {
            return dao.getFavLocations()
    }

    companion object {
        private var instance: LocationLocalDataSourceInterface? = null

        @Synchronized
        fun getInstance(context: Context): LocationLocalDataSourceInterface {
            if (instance == null) {
                instance = LocationLocalDataSource(context)
            }
            return instance!!
        }
    }
}
