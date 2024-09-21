package com.example.weathercast.homeweather.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weathercast.R
import com.example.weathercast.data.reposatoru.WeatherReposatory
import com.example.weathercast.data.reposatoru.WeatherReposatoryInterface
import com.example.weathercast.databinding.FragmentHomeBinding
import com.example.weathercast.homeweather.viewmodel.HomeViewModel
import com.example.weathercast.homeweather.viewmodel.HomeViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class HomeFragment : Fragment() {
    lateinit var homeViewModel: HomeViewModel
    lateinit var binding: FragmentHomeBinding
    private lateinit var weatherReposatory: WeatherReposatoryInterface
    lateinit var adapter: TodayWeatherDiffUtillAdapter
    private lateinit var recyclersView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclersView = view.findViewById(R.id.today_weather_recyclerView)
        recyclersView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        adapter = TodayWeatherDiffUtillAdapter()

        weatherReposatory = WeatherReposatory.getInstance()!!
        val viewModelFactory = HomeViewModelFactory(weatherReposatory)
        homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        binding.hfvModel=homeViewModel
        binding.lifecycleOwner = this
        homeViewModel.getForecastData("30.0444","31.2357","metric")
        homeViewModel.getCurrentData("30.0444","31.2357","metric")

        val dateFormat = SimpleDateFormat("EEE, dd MMMM  h:mm a", Locale.getDefault())
        val currentDateAndTime: String = dateFormat.format(Date())
        binding.currentdate=currentDateAndTime

        homeViewModel.forcastWeatherData.observe(viewLifecycleOwner, Observer { weatherData ->
            adapter.submitList(weatherData.list.filter { it.dtTxt?.contains(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())) == true })
            recyclersView.adapter = adapter
        })
    }


}
@BindingAdapter("countryCode", "cityName")
fun setCountryAndCityName(view: TextView, countryCode: String?, cityName: String?) {
    if (!countryCode.isNullOrEmpty() && !cityName.isNullOrEmpty()) {
        // Convert ISO code to full country name using Locale
        val fullCountryName = Locale("", countryCode).displayCountry
        view.text = "$cityName/ $fullCountryName"
    } else {
        view.text = "N/A"
    }
}


@BindingAdapter("imageIcon", "error")
fun weatherImage(view: ImageView, icon: String?, error: Drawable) {
    if (!icon.isNullOrEmpty()) {
        val resourceId = view.context.resources.getIdentifier(
            "icon_$icon",
            "drawable",
            view.context.packageName
        )

        if (resourceId != 0) {
            view.setImageResource(resourceId)
        }
    } else {
        view.setImageDrawable(error)
    }
}
