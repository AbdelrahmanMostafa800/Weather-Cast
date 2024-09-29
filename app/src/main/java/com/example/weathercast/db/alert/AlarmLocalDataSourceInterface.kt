package com.example.weathercast.db.alert

import androidx.room.Query
import com.example.weathercast.data.pojo.AlertItem
import com.example.weathercast.data.pojo.Location
import kotlinx.coroutines.flow.Flow

interface AlarmLocalDataSourceInterface {
    suspend fun insertAlarmItem(alertItem: AlertItem)
    suspend fun deleteAlarm(id: Int)
    fun getAllAlarms(): Flow<List<AlertItem>>
}
