package com.example.weathercast.alarmandnotification.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weathercast.data.pojo.AlertItem
import com.example.weathercast.data.reposatoru.WeatherReposatoryInterface
import com.example.weathercast.homeweather.viewmodel.AppiState
import com.example.weathercast.homeweather.viewmodel.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AlertViewModel(private val weatherReposatory: WeatherReposatoryInterface) : ViewModel() {
    private val _allAlertData = MutableStateFlow<AlertStatus>(AlertStatus.Empty)
    val allAlertData: StateFlow<AlertStatus> = _allAlertData

    fun getAllAlerts() = viewModelScope.launch(Dispatchers.IO) {
        _allAlertData.value = AlertStatus.Loading
        weatherReposatory.getAlertItems()
            .catch { exception ->
                _allAlertData.value = AlertStatus.Failure(exception.message.toString())
            }
            .collect { alertItems ->
                _allAlertData.value = AlertStatus.Success(alertItems)
            }
    }
    fun addAlert(alertItem: AlertItem) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherReposatory.addAlertItem(alertItem)
        }

    }

    fun deleteAlert(alarmItem: AlertItem) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherReposatory.deleteAlertItem(alarmItem.id)
        }
    }
}
class AlertViewModelFactory(private val weatherReposatory: WeatherReposatoryInterface) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AlertViewModel(weatherReposatory) as T
    }
}
