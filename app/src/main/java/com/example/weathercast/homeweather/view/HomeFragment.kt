package com.example.weathercast.homeweather.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weathercast.R
import com.example.weathercast.data.reposatoru.WeatherReposatory
import com.example.weathercast.data.reposatoru.WeatherReposatoryInterface
import com.example.weathercast.homeweather.viewmodel.HomeViewModel
import com.example.weathercast.homeweather.viewmodel.HomeViewModelFactory

class HomeFragment : Fragment() {
    lateinit var homeViewModel: HomeViewModel
    private lateinit var weatherReposatory: WeatherReposatoryInterface
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherReposatory = WeatherReposatory.getInstance()!!
        val viewModelFactory = HomeViewModelFactory(weatherReposatory)
        homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        /*homeViewModel.products.observe(this, Observer { products ->
            adapter.submitList(products)
            recyclerView.adapter = adapter
        })
        homeViewModel.getAllProducts()*/
    }
}