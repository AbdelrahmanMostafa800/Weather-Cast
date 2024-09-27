package com.example.weathercast.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.weathercast.R
import com.example.weathercast.viemodel.ToolBarTextViewModel
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {
    private val toolBarTextViewModel: ToolBarTextViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        toolBarTextViewModel = ViewModelProvider(this).get(ToolBarTextViewModel::class.java)
//        toolBarTextViewModel.setToolbarTitle("Setting")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            toolBarTextViewModel.setToolbarTitle("Setting")
        }
    }
}