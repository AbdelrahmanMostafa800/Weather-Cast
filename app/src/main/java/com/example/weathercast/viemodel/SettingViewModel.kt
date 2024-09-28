package com.example.weathercast.viemodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weathercast.data.reposatoru.WeatherReposatoryInterface
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SettingViewModel(private val weatherReposatory: WeatherReposatoryInterface): ViewModel() {
    fun setLanguage(lang: String) {
        weatherReposatory.setLanguage(lang)
    }
    fun getSettingLanguage() :String{
           return weatherReposatory.getSettingLanguage()
    }

    fun setWind(wind: String) {
        weatherReposatory.setWind(wind)
    }

    fun setTemp(temp: String) {
        weatherReposatory.setTemp(temp)
    }

    fun setLocation(location: String) {
        weatherReposatory.setSettingLocation(location)
    }
    fun getSettingLocation(): String {
        return weatherReposatory.getSettingLocation()
    }
    fun getSettingWind(): String {
        return weatherReposatory.getSettingWind()
    }
    fun getSettingTemp(): String {
        return weatherReposatory.getSettingTemp()
    }

}
class SettingViewModelFactory(private val weatherReposatory: WeatherReposatoryInterface) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SettingViewModel(weatherReposatory) as T
    }
}