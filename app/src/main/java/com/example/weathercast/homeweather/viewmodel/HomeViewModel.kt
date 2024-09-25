package com.example.weathercast.homeweather.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weathercast.data.pojo.CurrentWeatherData
import com.example.weathercast.data.pojo.ForcastWeatherData
import com.example.weathercast.data.reposatoru.WeatherReposatoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val weatherReposatory: WeatherReposatoryInterface): ViewModel() {
    private var _forcastWeatherData = MutableLiveData<AppiState>()
    val forcastWeatherData: LiveData<AppiState> = _forcastWeatherData

    private var _currenWeatherData = MutableLiveData<CurrentWeatherData>()
    val currenWeatherData: LiveData<CurrentWeatherData> = _currenWeatherData

    fun getForecastData(latitude: String, longitude: String, measurementUnit: String)= viewModelScope.launch(Dispatchers.IO) {
        _forcastWeatherData.postValue(AppiState.Loading)
        weatherReposatory.getForecastData(latitude, longitude,measurementUnit)
            .catch {
                _forcastWeatherData.postValue(AppiState.Failure(it.message.toString())) // post failure state
            }
            .collect {
                _forcastWeatherData.postValue(AppiState.Success(it)) // post success state
            }
    }
    fun getCurrentData(latitude: String, longitude: String, measurementUnit: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _currenWeatherData.postValue(weatherReposatory.getCurrentData(latitude, longitude,measurementUnit))

        }
    }
}


class HomeViewModelFactory(private val weatherReposatory: WeatherReposatoryInterface) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(weatherReposatory) as T
    }
}