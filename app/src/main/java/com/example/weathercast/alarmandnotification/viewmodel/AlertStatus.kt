package com.example.weathercast.alarmandnotification.viewmodel

import com.example.weathercast.data.pojo.AlertItem


sealed class AlertStatus {
    class Success(val data: List<AlertItem>) : AlertStatus()
    class Failure(val message: String) : AlertStatus()
    object Loading : AlertStatus()
    object Empty : AlertStatus()
}