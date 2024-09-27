package com.example.weathercast.favorits


interface OnFavoritItemClick {
    fun onDeleteClick(address: String)
    fun onItemClicked(latitude:Double,longitude:Double)
}