package com.fungorn.musicapp.ui.prediction

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fungorn.musicapp.R
import com.fungorn.musicapp.databinding.FragmentPredictionDetailsBinding
import com.fungorn.musicapp.domain.model.Track
import com.fungorn.musicapp.ui.common.ext.observeNonNull
import com.fungorn.musicapp.ui.common.ext.requireArg
import com.fungorn.musicapp.ui.common.ext.viewBinding
import com.fungorn.musicapp.ui.common.tracksAdapterDelegate
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class PredictionDetailsFragment : Fragment(R.layout.fragment_prediction_details) {
    private val binding by viewBinding(FragmentPredictionDetailsBinding::bind)
    private val viewModel by viewModel<PredictionDetailViewModel>()

    private val track by requireArg<Track>(ARG_TRACK)

    private val tracksAdapter by lazy {
        ListDelegationAdapter(
            tracksAdapterDelegate(onItemClick = {})
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
            predictionsList.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = tracksAdapter
            }
            trackName.text = track.song
            artistAndYear.text = getString(R.string.artist_and_year, track.artist, track.year)
        }
        observeLiveData()
    }

    private fun observeLiveData() = with(viewModel) {
        isLoading.observeNonNull(viewLifecycleOwner) {
            binding.progress.isVisible = it
        }
        predictedTracks.observeNonNull(viewLifecycleOwner) {
            tracksAdapter.items = it
        }
    }

    companion object {
        private const val ARG_TRACK = "track"

        fun bundleArgs(track: Track) = bundleOf(
            ARG_TRACK to track
        )
    }
}