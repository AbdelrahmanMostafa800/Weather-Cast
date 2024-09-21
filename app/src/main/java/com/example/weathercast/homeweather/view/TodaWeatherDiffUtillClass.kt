package com.example.weathercast.homeweather.view

import androidx.recyclerview.widget.DiffUtil
import com.example.weathercast.data.pojo.WeatherData

/*
class TodaWeatherDiffUtillClass : DiffUtil.ItemCallback<WeatherData>() {
        class MyDiffUtillClass : DiffUtil.ItemCallback<ForcastWeatherData>() {
    override fun areItemsTheSame(oldItem: ForcastWeatherData, newItem: ForcastWeatherData): Boolean {
        return oldItem.dt == newItem.dt
    }

    override fun areContentsTheSame(oldItem: ForcastWeatherData, newItem: ForcastWeatherData): Boolean {
        return oldItem.main.temp == newItem.main.temp &&
               oldItem.main.feels_like == newItem.main.feels_like &&
               oldItem.main.temp_min == newItem.main.temp_min &&
               oldItem.main.temp_max == newItem.main.temp_max &&
               oldItem.main.pressure == newItem.main.pressure &&
               oldItem.main.humidity == newItem.main.humidity
    }

}*/
