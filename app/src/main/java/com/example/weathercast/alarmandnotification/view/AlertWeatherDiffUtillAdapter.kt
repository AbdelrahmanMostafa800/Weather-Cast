package com.example.weathercast.alarmandnotification.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weathercast.R
import com.example.weathercast.data.pojo.AlertItem
import com.example.weathercast.data.pojo.Location
import com.example.weathercast.databinding.AlertRecycleviewItemBinding
import com.example.weathercast.databinding.FavoritRecycleviewItemBinding


class AlertWeatherDiffUtillAdapter(private val onAlarmItemDeleteClick: OnAlarmItemDeleteClick): ListAdapter<AlertItem, AlertWeatherDiffUtillAdapter.ViewHolder> (
    AlertDiffUtillClass()
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listRowBinding:AlertRecycleviewItemBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.alert_recycleview_item,parent,false)
        return ViewHolder(listRowBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val alertItem:AlertItem=getItem(position)
        holder.listRowBinding.aletitem=alertItem
        holder.listRowBinding.clickHandler=onAlarmItemDeleteClick
    }
    class ViewHolder(var listRowBinding: AlertRecycleviewItemBinding) : RecyclerView.ViewHolder(listRowBinding.root)
}


