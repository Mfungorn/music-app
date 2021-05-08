package com.fungorn.musicapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fungorn.musicapp.domain.model.Track
import com.fungorn.musicapp.domain.usecase.GetAllTracksUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    getAllTracksUseCase: GetAllTracksUseCase
) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _tracks = MutableLiveData<List<Track>>(listOf())
    val tracks: LiveData<List<Track>> = _tracks

    init {
        viewModelScope.launch {
            _isLoading.value = true

            withContext(Dispatchers.IO) {
                kotlin.runCatching { getAllTracksUseCase() }
            }
                .onSuccess { _tracks.value = it }
                .onFailure {
                    Log.e("GetAllTracks", it.message.orEmpty(), it)
                    _tracks.value = emptyList()
                }

            _isLoading.value = false
        }
    }
}