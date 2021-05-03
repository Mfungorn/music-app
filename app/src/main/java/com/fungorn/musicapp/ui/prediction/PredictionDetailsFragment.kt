package com.fungorn.musicapp.ui.prediction

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.fungorn.musicapp.R
import com.fungorn.musicapp.databinding.FragmentPredictionDetailsBinding
import com.fungorn.musicapp.ui.common.ext.observeNonNull
import com.fungorn.musicapp.ui.common.ext.viewBinding
import org.koin.android.viewmodel.ext.android.viewModel

class PredictionDetailsFragment : Fragment(R.layout.fragment_prediction_details) {
    private val binding by viewBinding(FragmentPredictionDetailsBinding::bind)
    private val viewModel by viewModel<PredictionDetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {

        }
        observeLiveData()
    }

    private fun observeLiveData() = with(viewModel) {
        isLoading.observeNonNull(viewLifecycleOwner) {

        }
        predictedTracks.observeNonNull(viewLifecycleOwner) {

        }
    }
}