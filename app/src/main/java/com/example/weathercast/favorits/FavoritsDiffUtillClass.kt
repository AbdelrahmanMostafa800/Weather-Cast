package com.example.weathercast.favorits

import androidx.recyclerview.widget.DiffUtil
import com.example.weathercast.data.pojo.Location
import com.example.weathercast.data.pojo.WeatherData

class FavoritsDiffUtillClass : DiffUtil.ItemCallback<Location>() {
    override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem.address == newItem.address
    }

    override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem == newItem
    }

}
