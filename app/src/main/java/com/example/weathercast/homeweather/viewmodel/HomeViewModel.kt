package com.example.weathercast.homeweather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weathercast.data.reposatoru.WeatherReposatoryInterface

class HomeViewModel(private val weatherReposatory: WeatherReposatoryInterface): ViewModel() {
}


class HomeViewModelFactory(private val weatherReposatory: WeatherReposatoryInterface) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(weatherReposatory) as T
    }
}