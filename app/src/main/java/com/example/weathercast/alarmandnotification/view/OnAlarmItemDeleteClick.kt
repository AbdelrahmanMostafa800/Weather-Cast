package com.example.weathercast.alarmandnotification.view

import com.example.weathercast.data.pojo.AlertItem


interface OnAlarmItemDeleteClick {
    fun onDeleteClick(alarmItem: AlertItem)
}