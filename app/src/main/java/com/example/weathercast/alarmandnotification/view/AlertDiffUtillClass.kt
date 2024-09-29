package com.example.weathercast.alarmandnotification.view

import androidx.recyclerview.widget.DiffUtil
import com.example.weathercast.data.pojo.AlertItem

class AlertDiffUtillClass : DiffUtil.ItemCallback<AlertItem>() {
    override fun areItemsTheSame(oldItem: AlertItem, newItem: AlertItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AlertItem, newItem: AlertItem): Boolean {
        return oldItem == newItem
    }

}
