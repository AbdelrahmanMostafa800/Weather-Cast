package com.example.weathercast.db.alert

import android.content.Context
import com.example.weathercast.data.pojo.AlertItem
import com.example.weathercast.data.pojo.Location
import kotlinx.coroutines.flow.Flow

class AlarmLocallDataSource(context: Context) : AlarmLocalDataSourceInterface {
    var dao: AlarmDAO =  AlarmAppDataBase.getInstance(context)?.getAlarmDAO()!!

    companion object {
        private var instance: AlarmLocalDataSourceInterface? = null

        @Synchronized
        fun getInstance(context: Context): AlarmLocalDataSourceInterface {
            if (instance == null) {
                instance = AlarmLocallDataSource(context)
            }
            return instance!!
        }
    }

    override suspend fun insertAlarmItem(alertItem: AlertItem) {
       dao.insertAlarmItem(alertItem)
    }

    override suspend fun deleteAlarm(id: Int) {
       dao.deleteAlarm(id)
    }

    override fun getAllAlarms(): Flow<List<AlertItem>> {
        return dao.getAllAlarms()
    }
}
