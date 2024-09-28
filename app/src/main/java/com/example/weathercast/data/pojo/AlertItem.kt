package com.example.weathercast.data.pojo

import java.time.LocalDateTime

data class AlertItem(val time: LocalDateTime, val city:String="", val country:String="", val lat:Double=0.0, val lon:Double=0.0)