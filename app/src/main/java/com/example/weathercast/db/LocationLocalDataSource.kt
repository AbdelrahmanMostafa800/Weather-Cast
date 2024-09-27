package com.example.mvvm.db

import android.content.Context
import com.example.weathercast.data.pojo.Location
import kotlinx.coroutines.flow.Flow

class LocationLocalDataSource(context: Context) : LocationLocalDataSourceInterface {
    var dao: LocationDAO =  AppDataBase.getInstance(context)?.getLocationDAO()!!
//    var storeProduct: LiveData<List<Productdb>> = dao.allProducts
    override suspend fun insertLocation(location: Location) {
         dao.insertLocation(location)
    }

    override suspend fun deleteLocation(address: String) {
            dao.deleteLocation(address)
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
