package com.example.weathercast.viemodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ToolBarTextViewModel: ViewModel() {
    private val _toolbarTitleFlow = MutableSharedFlow<String>(replay = 1)
    val toolbarTitleFlow = _toolbarTitleFlow.asSharedFlow()

    // Function to emit a new toolbar title
    suspend fun setToolbarTitle(title: String) {
        _toolbarTitleFlow.emit(title)
    }
}