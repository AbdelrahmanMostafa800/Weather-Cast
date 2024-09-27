package com.example.weathercast.favorits

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm.db.LocationLocalDataSource
import com.example.mvvm.network.RemoteDataSource
import com.example.weathercast.R
import com.example.weathercast.data.localdatasource.SharedPreferencelLocationData
import com.example.weathercast.data.localdatasource.WeatherLocalDataSource
import com.example.weathercast.data.reposatoru.WeatherReposatory
import com.example.weathercast.databinding.FragmentFavoritBinding
import com.example.weathercast.map.view.MapActivity
import com.example.weathercast.viemodel.DbViewModel
import com.example.weathercast.viemodel.DbViewModelFactory
import com.example.weathercast.viemodel.ToolBarTextViewModel
import kotlinx.coroutines.launch

class FavoritsFragment : Fragment(), OnFavoritItemClick {

    lateinit var dbViewModel: DbViewModel
    lateinit var binding: FragmentFavoritBinding
    private val toolBarTextViewModel: ToolBarTextViewModel by activityViewModels()
    lateinit var favoritsAdapter: FavoritsWeatherDiffUtillAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        toolBarTextViewModel = ViewModelProvider(this).get(ToolBarTextViewModel::class.java)
//        toolBarTextViewModel.setToolbarTitle("Favorits")
        viewLifecycleOwner.lifecycleScope.launch {
            toolBarTextViewModel.setToolbarTitle("Favorites")
        }
        val remoteDataSource = RemoteDataSource()
        val localRepository = WeatherLocalDataSource(SharedPreferencelLocationData.getInstance(requireContext()),
            LocationLocalDataSource.getInstance(requireContext()))
        val weatherReposatory = WeatherReposatory.getInstance(remoteDataSource, localRepository)
        val viewModelFactory = DbViewModelFactory(weatherReposatory)
        dbViewModel =
            ViewModelProvider(this, viewModelFactory).get(DbViewModel::class.java)
        dbViewModel.getFavoritsProducts()
        binding.favoritsRecyclerView.layoutManager = LinearLayoutManager(context)
        favoritsAdapter = FavoritsWeatherDiffUtillAdapter(this)
        dbViewModel.favLocations.observe(viewLifecycleOwner, Observer { products ->
            favoritsAdapter.submitList(products)
            binding.favoritsRecyclerView.adapter = favoritsAdapter
        })

        binding.addButton.setOnClickListener {
            val intent = Intent(requireContext(), MapActivity::class.java)
            intent.putExtra("isFromFavorits", true)
            startActivity(intent)
        }

    }

    override fun onDeleteClick(address: String) {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.custom_dialog_layout)

        val yesButton = dialog.findViewById<Button>(R.id.yes_button)
        val noButton = dialog.findViewById<Button>(R.id.no_button)

        yesButton.setOnClickListener {
            dbViewModel.deleteLocation(address)
            dialog.dismiss()
        }

        noButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


    override fun onItemClicked(latitude: Double, longitude: Double) {
        val bundle = Bundle()
        bundle.putBoolean("isFromFavorits", true)
        bundle.putDouble("latitude", latitude)
        bundle.putDouble("longitude", longitude)

        findNavController(requireActivity(), R.id.nav_host_fragment)
            .navigate(R.id.action_favoritFragment_to_homeFragment, bundle)
    }

}