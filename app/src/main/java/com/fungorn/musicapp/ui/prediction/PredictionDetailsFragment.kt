package com.fungorn.musicapp.ui.prediction

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
import org.koin.core.parameter.parametersOf

class PredictionDetailsFragment : Fragment(R.layout.fragment_prediction_details) {
    private val binding by viewBinding(FragmentPredictionDetailsBinding::bind)
    private val viewModel by viewModel<PredictionDetailViewModel> {
        parametersOf(track.id)
    }

    private val track by requireArg<Track>(ARG_TRACK)

    private val tracksAdapter by lazy {
        ListDelegationAdapter(
            tracksAdapterDelegate(onItemClick = {})
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
            yearSpinner.apply {
                val yearItems = (1960..2020)
                    .mapTo(mutableListOf(), Int::toString)
                    .also { items -> items.add(0, "Select") }
                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    yearItems
                )
                setAdapter(adapter)
                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        viewModel.onYearSelected(yearItems[position])
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
            }
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
        predictedTracks.observe(viewLifecycleOwner) {
            tracksAdapter.items = it
            tracksAdapter.notifyDataSetChanged()
        }
    }

    companion object {
        private const val ARG_TRACK = "track"

        fun bundleArgs(track: Track) = bundleOf(
            ARG_TRACK to track
        )
    }
}