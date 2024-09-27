package com.example.weathercast.viemodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weathercast.data.pojo.Location
import com.example.weathercast.data.reposatoru.WeatherReposatoryInterface
import com.example.weathercast.homeweather.viewmodel.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DbViewModel(private val weatherReposatory: WeatherReposatoryInterface): ViewModel() {
    private val _favLocations = MutableLiveData<List<Location>>()
    val favLocations: LiveData<List<Location>> = _favLocations

    fun getFavoritsProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            weatherReposatory.getfavLocations()
                .collect {
                    _favLocations.postValue(it)
                }
        }
    }

    fun insertLocation(location: Location) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherReposatory.insertLocation(location)
        }
    }

    fun deleteLocation(address: String) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherReposatory.deleteLocation(address)
        }

    }
}
class DbViewModelFactory(private val weatherReposatory: WeatherReposatoryInterface) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DbViewModel(weatherReposatory) as T
    }
}