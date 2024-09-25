package com.example.weathercast.homeweather.view

import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weathercast.R
import com.example.weathercast.data.pojo.WeatherData
import com.example.weathercast.databinding.TodayRecycleRecycleviewItemBinding
import java.util.Date
import java.util.Locale


class TodayWeatherDiffUtillAdapter: ListAdapter<WeatherData, TodayWeatherDiffUtillAdapter.ViewHolder> (
    TodaWeatherDiffUtillClass()
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listRowBinding:TodayRecycleRecycleviewItemBinding=
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.today_recycle_recycleview_item,parent,false)
        return ViewHolder(listRowBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weatherData:WeatherData=getItem(position)
        holder.listRowBinding.todayweatherData=weatherData
    }
    class ViewHolder(var listRowBinding: TodayRecycleRecycleviewItemBinding) : RecyclerView.ViewHolder(listRowBinding.root)
}
@BindingAdapter("recycletext")
fun setTime(view: TextView, recycletext: String?) {
    if (recycletext != null) {
        val locale = Locale.getDefault()  // Get the current locale
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale)  // Use locale-aware date parsing
        try {
            val date: Date = sdf.parse(recycletext)
            val outputSdf = SimpleDateFormat("h a", locale)  // Locale-aware time formatting
            view.text = outputSdf.format(date)
        } catch (e: Exception) {
            view.text = "N/A"
        }
    } else {
        view.text = "N/A"
    }
}

