package com.example.weathercast.homeweather.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weathercast.R
import com.example.weathercast.data.reposatoru.WeatherReposatory
import com.example.weathercast.data.reposatoru.WeatherReposatoryInterface
import com.example.weathercast.databinding.FragmentHomeBinding
import com.example.weathercast.homeweather.viewmodel.HomeViewModel
import com.example.weathercast.homeweather.viewmodel.HomeViewModelFactory

class HomeFragment : Fragment() {
    lateinit var homeViewModel: HomeViewModel
    lateinit var binding: FragmentHomeBinding
    private lateinit var weatherReposatory: WeatherReposatoryInterface
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherReposatory = WeatherReposatory.getInstance()!!
        val viewModelFactory = HomeViewModelFactory(weatherReposatory)
        homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        binding.hfvModel=homeViewModel
        binding.lifecycleOwner = this
        homeViewModel.getForecastData("30.0444","31.2357","metric")
        homeViewModel.getCurrentData("30.0444","31.2357","metric")
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
        } else {
            view.setImageDrawable(error)
        }
    } else {
        view.setImageDrawable(error)
    }
}
