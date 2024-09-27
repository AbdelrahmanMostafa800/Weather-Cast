package com.example.mvvm.db

import com.example.weathercast.data.pojo.Location
import kotlinx.coroutines.flow.Flow

interface LocationLocalDataSourceInterface {
    suspend fun insertLocation(location: Location)
    suspend fun deleteLocation(address: String)
    fun getFavLocation(): Flow<List<Location>>
}
