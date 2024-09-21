package com.example.weathercast.homeweather.view

import androidx.recyclerview.widget.DiffUtil
import com.example.weathercast.data.pojo.WeatherData

class TodaWeatherDiffUtillClass : DiffUtil.ItemCallback<WeatherData>() {
    override fun areItemsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
        return oldItem.dt == newItem.dt
    }

    override fun areContentsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean {
        return oldItem == newItem
    }

}
