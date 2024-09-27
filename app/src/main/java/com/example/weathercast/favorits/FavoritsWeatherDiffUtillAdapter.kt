package com.example.weathercast.favorits

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weathercast.R
import com.example.weathercast.data.pojo.Location
import com.example.weathercast.databinding.FavoritRecycleviewItemBinding


class FavoritsWeatherDiffUtillAdapter(private val favoritsFragment: OnFavoritItemClick): ListAdapter<Location, FavoritsWeatherDiffUtillAdapter.ViewHolder> (
    FavoritsDiffUtillClass()
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val listRowBinding: FavoritRecycleviewItemBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.favorit_recycleview_item,parent,false)
        return ViewHolder(listRowBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val locationData:Location=getItem(position)
        holder.listRowBinding.location=locationData
        holder.listRowBinding.clickHandler=favoritsFragment
    }
    class ViewHolder(var listRowBinding: FavoritRecycleviewItemBinding) : RecyclerView.ViewHolder(listRowBinding.root)
}


