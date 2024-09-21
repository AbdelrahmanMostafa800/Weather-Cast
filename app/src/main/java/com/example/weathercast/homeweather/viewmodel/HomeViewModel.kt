package com.example.weathercast.homeweather.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weathercast.data.pojo.ForcastWeatherData
import com.example.weathercast.data.reposatoru.WeatherReposatoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val weatherReposatory: WeatherReposatoryInterface): ViewModel() {
    private var _forcastWeatherData = MutableLiveData<ForcastWeatherData>()
    val forcastWeatherData: LiveData<ForcastWeatherData> = _forcastWeatherData

    fun getForecastData(latitude: String, longitude: String, measurementUnit: String) {
        Log.d("TAG", "getForecastData: viewmodel")
        viewModelScope.launch(Dispatchers.IO) {
            _forcastWeatherData.postValue(weatherReposatory.getForecastData(latitude, longitude,measurementUnit))

        }
    }
}


class HomeViewModelFactory(private val weatherReposatory: WeatherReposatoryInterface) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(weatherReposatory) as T
    }
}