package com.example.weathercast.homeweather.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weathercast.alarmandnotification.viewmodel.AlertStatus
import com.example.weathercast.data.pojo.City
import com.example.weathercast.data.pojo.CurrentWeatherData
import com.example.weathercast.data.pojo.ForcastWeatherData
import com.example.weathercast.data.reposatoru.WeatherReposatoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val weatherReposatory: WeatherReposatoryInterface): ViewModel() {
    private var _forcastWeatherData = MutableLiveData<AppiState>()
    val forcastWeatherData: LiveData<AppiState> = _forcastWeatherData

    private var _currenWeatherData = MutableLiveData<CurrentWeatherData>()
    val currenWeatherData: LiveData<CurrentWeatherData> = _currenWeatherData

    private val _todayForecastWeatherData = MutableLiveData<List<ForcastWeatherData>>()
    val todayForecastWeatherData:LiveData<List<ForcastWeatherData>> = _todayForecastWeatherData

    private val _todayCurrentWeatherData = MutableLiveData<List<CurrentWeatherData>>()
    val todayCurrentWeatherData:LiveData<List<CurrentWeatherData>> = _todayCurrentWeatherData

    private val _cities = MutableLiveData<List<City>>()
    val cities: LiveData<List<City>> = _cities

    fun getForecastData(latitude: String, longitude: String, measurementUnit: String,language:String)= viewModelScope.launch(Dispatchers.IO) {
        _forcastWeatherData.postValue(AppiState.Loading)
        weatherReposatory.getForecastData(latitude, longitude,measurementUnit,language)
            .catch {
                _forcastWeatherData.postValue(AppiState.Failure(it.message.toString())) // post failure state
            }
            .collect {
                _forcastWeatherData.postValue(AppiState.Success(it)) // post success state
            }
    }
    fun getCurrentData(latitude: String, longitude: String, measurementUnit: String,language:String) {
        viewModelScope.launch(Dispatchers.IO) {
            _currenWeatherData.postValue(weatherReposatory.getCurrentData(latitude, longitude,measurementUnit,language))

        }
    }

    fun insertForecastWeatherData(forecastWeatherData: ForcastWeatherData){
        viewModelScope.launch(Dispatchers.IO) {
            weatherReposatory.insertForecastWeatherData(forecastWeatherData)
        }
    }
    fun insertCurrentWeatherData(currenWeatherData:CurrentWeatherData){
        viewModelScope.launch(Dispatchers.IO) {
            weatherReposatory.insertCurrentWeatherData(currenWeatherData)
        }
    }
    fun insertCity(city: City){
        viewModelScope.launch(Dispatchers.IO) {
            weatherReposatory.insertCity(city)
        }
    }
    fun getAllForecastWeatherData() {
        viewModelScope.launch(Dispatchers.IO) {
            _todayForecastWeatherData.postValue(weatherReposatory.getAllForecastWeatherData())

        }
    }
    fun getAllCurrentWeatherData() {
        viewModelScope.launch(Dispatchers.IO) {
            _todayCurrentWeatherData.postValue(weatherReposatory.getAllCurrentWeatherData())

        }
    }
    fun getAllCities() {
        viewModelScope.launch(Dispatchers.IO) {
            _cities.postValue(weatherReposatory.getAllCities())

        }
    }
}


class HomeViewModelFactory(private val weatherReposatory: WeatherReposatoryInterface) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(weatherReposatory) as T
    }
}