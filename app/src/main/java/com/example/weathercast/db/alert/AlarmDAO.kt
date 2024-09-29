package com.example.weathercast.db.alert

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.weathercast.data.pojo.AlertItem
import com.example.weathercast.data.pojo.Location
import kotlinx.coroutines.flow.Flow


@Dao
interface AlarmDAO {
    @Query("SELECT * FROM alarmtable")
     fun  getAllAlarms(): Flow<List<AlertItem>>
    @Insert
    suspend fun insertAlarmItem(alertItem: AlertItem)
    @Query("DELETE FROM alarmtable WHERE id = :id")
    suspend fun deleteAlarm(id: Int)
}