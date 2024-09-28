package com.example.weathercast.alarmandnotification

import com.example.weathercast.data.pojo.AlertItem

interface AlertSchedulerInterface {
    fun schedule(item: AlertItem)
    fun cancel(item: AlertItem)
}
