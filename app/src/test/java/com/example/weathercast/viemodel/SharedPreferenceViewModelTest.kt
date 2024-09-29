package com.example.weathercast.viemodel

import com.example.weathercast.data.reposatoru.WeatherReposatoryInterface
import org.junit.Test


class SharedPreferenceViewModelTest{
    val fakelocation="Eg"
    var viewModel: SharedPreferenceViewModel
    var repoo: WeatherReposatoryInterface
    init {
        repoo=FakeWeatherReposatory(fakelocation)
        viewModel = SharedPreferenceViewModel(repoo)
    }
    @Test
    fun saveLocation_latAndLong_return1() {
        val result = viewModel.saveLocation(1.0,1.0)
        assert(result == 1)
    }
    @Test
    fun getLocation_returnLocatiom() {
        val result = viewModel.getLocation()
        assert(result == "Eg")
    }
}