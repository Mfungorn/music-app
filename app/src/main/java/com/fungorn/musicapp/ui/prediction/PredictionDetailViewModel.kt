package com.fungorn.musicapp.ui.prediction

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fungorn.musicapp.domain.model.Track
import com.fungorn.musicapp.domain.usecase.GetTrackPredictionsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PredictionDetailViewModel(
    private val trackId: String,
    private val getTrackPredictionsUseCase: GetTrackPredictionsUseCase
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _predictedTracks = MutableLiveData<List<Track>>(listOf())
    val predictedTracks: LiveData<List<Track>> = _predictedTracks

    init {
        fetchPredictions(trackId)
    }

    private fun fetchPredictions(trackId: String, year: Int? = null) = viewModelScope.launch {
        _isLoading.value = true

        withContext(Dispatchers.IO) {
            kotlin.runCatching { getTrackPredictionsUseCase(trackId, year) }
        }
            .onSuccess { _predictedTracks.value = it }
            .onFailure {
                Log.e("GetTrackPredictions", it.message.orEmpty(), it)
                _predictedTracks.value = emptyList()
            }

        _isLoading.value = false
    }

    fun onYearSelected(yearString: String) {
        val year = yearString.toIntOrNull()
        fetchPredictions(trackId, year)
    }
}