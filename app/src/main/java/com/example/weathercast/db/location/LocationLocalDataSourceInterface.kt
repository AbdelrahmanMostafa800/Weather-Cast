package com.example.weathercast.db.location

import com.example.weathercast.data.pojo.Location
import kotlinx.coroutines.flow.Flow

interface LocationLocalDataSourceInterface {
    suspend fun insertLocation(location: Location):Long
    suspend fun deleteLocation(address: String):Int
    fun getFavLocation(): Flow<List<Location>>
}
