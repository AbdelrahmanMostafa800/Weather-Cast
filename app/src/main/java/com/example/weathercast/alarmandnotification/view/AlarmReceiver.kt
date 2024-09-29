package com.example.weathercast.alarmandnotification.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        // Start the AlarmSoundService to play the alarm sound
        val serviceIntent = Intent(context, FloatingAlarmService::class.java)
        context.startService(serviceIntent)
    }

fun stopAlarm(context: Context) {
    val serviceIntent = Intent(context, FloatingAlarmService::class.java)
    context.stopService(serviceIntent)
}
}
