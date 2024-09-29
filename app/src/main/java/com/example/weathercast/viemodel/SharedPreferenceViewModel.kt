package com.example.weathercast.viemodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weathercast.data.reposatoru.WeatherReposatoryInterface

class SharedPreferenceViewModel(private val weatherReposatory: WeatherReposatoryInterface): ViewModel() {
    fun saveLocation(latitude: Double, longitude: Double):Int {
       return weatherReposatory.saveLocation(latitude, longitude)
    }
    fun getLocation(): String? {
        return weatherReposatory.getLocation()
    }
}
class SharedPreferenceViewModelFactory(private val weatherReposatory: WeatherReposatoryInterface) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SharedPreferenceViewModel(weatherReposatory) as T
    }
}