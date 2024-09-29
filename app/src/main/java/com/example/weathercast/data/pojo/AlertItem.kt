package com.example.weathercast.data.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "alarmtable")
data class AlertItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, val time: LocalDateTime, val city:String="", val country:String="", val lat:Double=0.0, val lon:Double=0.0)