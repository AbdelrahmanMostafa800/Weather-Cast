package com.example.weathercast.viemodel

import com.example.weathercast.data.reposatoru.WeatherReposatoryInterface
import org.junit.Test


class SettingViewModelTest {
     var viewModel: SettingViewModel
     var repoo:WeatherReposatoryInterface
    init {
        repoo=FakeWeatherReposatory("")
        viewModel = SettingViewModel(repoo)
    }
    @Test
    fun getSettingLanguage_returnEnLanguage() {
        val result = viewModel.getSettingLanguage()
        assert(result == "en")
    }

    @Test
    fun getSettingLocation_returnArLanguage() {
        viewModel.setLanguage("ar")
        val result = viewModel.getSettingLanguage()
        assert(result == "ar")
    }

    @Test
    fun getSettingTemp_returnMetric() {
        val result = viewModel.getSettingTemp()
        assert(result == "metric")
    }

    @Test
    fun getSettingTemp_returnStandard() {
        viewModel.setTemp("Standard")
        val result = viewModel.getSettingTemp()
        assert(result == "Standard")

    }
    @Test
    fun getSettingTemp_returnimperial () {
        viewModel.setTemp("imperial")
        val result = viewModel.getSettingTemp()
        assert(result == "imperial")
    }
}