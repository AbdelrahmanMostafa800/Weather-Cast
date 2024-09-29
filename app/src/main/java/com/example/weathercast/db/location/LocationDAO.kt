package com.example.weathercast.db.location

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.weathercast.data.pojo.Location
import kotlinx.coroutines.flow.Flow


@Dao
interface LocationDAO {
    @Query("SELECT * FROM favoritplaces")
     fun  getFavLocations(): Flow<List<Location>>
    @Insert
    suspend fun insertLocation(location: Location):Long
    @Query("DELETE FROM favoritplaces WHERE address = :address")
    suspend fun deleteLocation(address: String):Int
}