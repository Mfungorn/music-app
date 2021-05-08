package com.fungorn.musicapp.ui.main

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fungorn.musicapp.R
import com.fungorn.musicapp.databinding.FragmentMainBinding
import com.fungorn.musicapp.ui.common.ext.observeNonNull
import com.fungorn.musicapp.ui.common.ext.viewBinding
import com.fungorn.musicapp.ui.common.tracksAdapterDelegate
import com.fungorn.musicapp.ui.prediction.PredictionDetailsFragment
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding by viewBinding(FragmentMainBinding::bind)
    private val viewModel by viewModel<MainViewModel>()

    private val tracksAdapter by lazy {
        ListDelegationAdapter(
            tracksAdapterDelegate {
                findNavController().navigate(
                    R.id.action_mainFragment_to_predictionDetailsFragment,
                    PredictionDetailsFragment.bundleArgs(it)
                )
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            tracksList.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = tracksAdapter
            }
        }
        observeLiveData()
    }

    private fun observeLiveData() = with(viewModel) {
        isLoading.observeNonNull(viewLifecycleOwner) {
            binding.progress.isVisible = it
        }
        tracks.observe(viewLifecycleOwner) {
            tracksAdapter.items = it
            tracksAdapter.notifyDataSetChanged()
        }
    }
}