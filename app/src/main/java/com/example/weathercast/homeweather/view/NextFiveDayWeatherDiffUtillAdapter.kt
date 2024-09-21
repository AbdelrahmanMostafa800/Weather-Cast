package com.example.weathercast.homeweather.view

import android.icu.text.SimpleDateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weathercast.R
import com.example.weathercast.data.pojo.WeatherData
import com.example.weathercast.databinding.NextFiveDaysRecycleviewItemBinding
import java.util.Date


class NextFiveDayWeatherDiffUtillAdapter: ListAdapter<WeatherData, NextFiveDayWeatherDiffUtillAdapter.ViewHolder> (
    FiveDaysWeatherDiffUtillClass()
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listRowBinding:NextFiveDaysRecycleviewItemBinding=
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.next_five_days_recycleview_item,parent,false)
        return ViewHolder(listRowBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weatherData:WeatherData=getItem(position)
        holder.listRowBinding.fivedaysweatherData=weatherData
    }
    class ViewHolder(var listRowBinding: NextFiveDaysRecycleviewItemBinding) : RecyclerView.ViewHolder(listRowBinding.root)
}
@BindingAdapter("minDegree", "maxDegree")
fun setMinAndMaxDegree(view: TextView, minDegree: String?, maxDegree: String?) {
    if (!minDegree.isNullOrEmpty() && !maxDegree.isNullOrEmpty()) {
        view.text = "$maxDegree/ $minDegree"
    } else {
        view.text = "N/A"
    }
}
@BindingAdapter("day")
fun setDay(view: TextView, day: String?) {
    if (day != null) {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        try {
            val date: Date = sdf.parse(day)
            val outputSdf = SimpleDateFormat("EEE")
            view.text = outputSdf.format(date)
        } catch (e: Exception) {
            view.text = "N/A"
        }
    } else {
        view.text = "N/A"
    }
}
