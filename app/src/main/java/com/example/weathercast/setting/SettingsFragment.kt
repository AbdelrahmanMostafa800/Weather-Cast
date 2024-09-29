package com.example.weathercast.setting

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mvvm.network.RemoteDataSource
import com.example.weathercast.R
import com.example.weathercast.alarmandnotification.viewmodel.AlertViewModel
import com.example.weathercast.alarmandnotification.viewmodel.AlertViewModelFactory
import com.example.weathercast.data.localdatasource.SharedPreferencelLocationData
import com.example.weathercast.data.localdatasource.WeatherLocalDataSource
import com.example.weathercast.data.reposatoru.WeatherReposatory
import com.example.weathercast.databinding.FragmentFavoritBinding
import com.example.weathercast.databinding.FragmentSettingsBinding
import com.example.weathercast.db.alert.AlarmLocallDataSource
import com.example.weathercast.db.location.LocationLocalDataSource
import com.example.weathercast.db.todayweather.TodayWeatherLocallDataSource
import com.example.weathercast.homeweather.view.MainActivity
import com.example.weathercast.viemodel.SettingViewModel
import com.example.weathercast.viemodel.SettingViewModelFactory
import com.example.weathercast.viemodel.ToolBarTextViewModel
import kotlinx.coroutines.launch
import java.util.Locale

class SettingsFragment : Fragment() {
    private val toolBarTextViewModel: ToolBarTextViewModel by activityViewModels()
    lateinit var binding: FragmentSettingsBinding
    lateinit var settingViewModel: SettingViewModel
    var language=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val remoteDataSource = RemoteDataSource()
        val localRepository = WeatherLocalDataSource(
            SharedPreferencelLocationData.getInstance(requireContext()),
            LocationLocalDataSource.getInstance(requireContext()), AlarmLocallDataSource.getInstance(requireContext()))
        val weatherReposatory = WeatherReposatory.getInstance(remoteDataSource, localRepository,
            TodayWeatherLocallDataSource.getInstance(requireContext()))
        val settingViewModelFactory = SettingViewModelFactory(weatherReposatory)
        settingViewModel =
            ViewModelProvider(this, settingViewModelFactory).get(SettingViewModel::class.java)
        language=settingViewModel.getSettingLanguage()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val favoritesString = if (language == "ar") {
            "الاعدادات"
        } else {
            "Settings"
        }
        viewLifecycleOwner.lifecycleScope.launch {
            toolBarTextViewModel.setToolbarTitle(favoritesString)
        }



        // Collect the language changes from SharedFlow
                when(language){
                    "en" -> {
                        binding.radioEnglish.isChecked = true
                        // Restart activity or fragment to apply language change
                    }
                    "ar" -> {
                        binding.radioArabic.isChecked = true
                        // Restart activity or fragment to apply language change
                }
            }
        when(settingViewModel.getSettingWind()){
            "metric" -> {
                binding.radioMeter.isChecked = true
            }
            "imperial" -> {
                binding.radioMile.isChecked = true
            }
        }
        when(settingViewModel.getSettingTemp()){
            "metric" -> {
                binding.radioCelsius.isChecked = true
            }
            "standard" -> {
                binding.radioKelvin.isChecked = true
            }
            "imperial" -> {
                binding.radioFerenheit.isChecked = true
            }
        }
        when(settingViewModel.getSettingLocation()){
            "gps" -> {
                binding.radioGps.isChecked = true
            }
            "map" -> {
                binding.radioMAp.isChecked = true
            }
            }

        binding.langGroup.setOnCheckedChangeListener { _, checkedId ->
            val lang = when (checkedId) {
                R.id.radioEnglish -> {
                    setLocale("en")
                    "en"
                }
                R.id.radioArabic -> {
                    setLocale("ar")
                    "ar"
                }
                else -> "en"
            }
            settingViewModel.setLanguage(lang)

        }
        binding.windGroup.setOnCheckedChangeListener { _, checkedId ->
            val wind = when (checkedId) {
                R.id.radioMeter -> "metric"
                R.id.radioMile -> "imperial"
                else -> "metric"
            }
            settingViewModel.setWind(wind)
        }
        binding.tempGrpup.setOnCheckedChangeListener { _, checkedId ->
            val temp = when (checkedId) {
                R.id.radioCelsius -> "metric"
                R.id.radioKelvin -> "standard"
                R.id.radioFerenheit -> "imperial"
                else -> "metric"
            }
            settingViewModel.setTemp(temp)
        }
        binding.locationGroup.setOnCheckedChangeListener { _, checkedId ->
            val location = when (checkedId) {
                R.id.radioGps -> "gps"
                R.id.radioMAp -> "map"
                else -> "gps"
            }
            settingViewModel.setLocation(location)
        }


    }
    private  fun setLocale(lang: String?) {
        val locale = Locale(lang)
        Locale.setDefault(locale)

        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        config.setLayoutDirection(locale)

        // Update the configuration
        requireActivity().resources.updateConfiguration(config, resources.displayMetrics)
        if (lang == "ar") {
            requireActivity().window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        } else {
            requireActivity().window.decorView.layoutDirection = View.LAYOUT_DIRECTION_LTR
        }
       /* val locale = Locale(lang)
        Locale.setDefault(locale)

        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        config.setLayoutDirection(locale)  // Important for RTL/LTR handling

        requireActivity().resources.updateConfiguration(config, requireActivity().resources.displayMetrics)*/

        // Restart activity to apply changes
        requireActivity().recreate()
       /* val myLocale = Locale(lang)

        val dm = resources.displayMetrics
        val conf = resources.configuration
        conf.locale = myLocale
        resources.updateConfiguration(conf, dm)
        *//*val refresh = Intent(requireContext(), MainActivity::class.java)
        startActivity(refresh)*//*
        requireActivity().recreate()*/
    }
    /*fun setLocale(language: String) {

        // Create a new Configuration object and set the locale
        val locale = Locale(language)
        Locale.setDefault(locale)

        // Get a mutable Configuration object from the existing one
        val config = Configuration(resources.configuration)
        config.setLocale(locale)

        // Automatically set layout direction based on locale
        config.setLayoutDirection(locale)

        // Update the configuration
        requireActivity().resources.updateConfiguration(config, requireActivity().resources.displayMetrics)

        // Refresh the activity to apply the changes
        requireActivity().recreate()
    }*/
}