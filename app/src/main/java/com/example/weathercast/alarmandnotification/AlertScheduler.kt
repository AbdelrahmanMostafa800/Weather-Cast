package com.example.weathercast.alarmandnotification

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.weathercast.data.pojo.AlertItem
import java.time.ZoneId

class AlertScheduler(val context: Context):AlertSchedulerInterface {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    @SuppressLint("ScheduleExactAlarm")
    override fun schedule(item: AlertItem) {
        val intent = Intent(context,AlarmReceiver::class.java)
        intent.putExtra("alertlat",item.lat)
        intent.putExtra("alertlong",item.lon)
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC,item.time.atZone(ZoneId.systemDefault()).toEpochSecond()*1000,
            PendingIntent.getBroadcast(context,item.time.hashCode(),intent,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE))
    }

    override fun cancel(item: AlertItem) {
        alarmManager.cancel(PendingIntent.getBroadcast(context,item.time.hashCode(),
            Intent(context,AlarmReceiver::class.java),PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE))
    }
}