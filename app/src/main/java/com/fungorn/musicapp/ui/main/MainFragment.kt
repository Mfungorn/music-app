package com.fungorn.musicapp.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.fungorn.musicapp.R
import com.fungorn.musicapp.databinding.FragmentMainBinding
import com.fungorn.musicapp.ui.common.ext.observeNonNull
import com.fungorn.musicapp.ui.common.ext.viewBinding
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding by viewBinding(FragmentMainBinding::bind)
    private val viewModel by viewModel<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {

        }
        observeLiveData()
    }

    private fun observeLiveData() = with(viewModel) {
        isLoading.observeNonNull(viewLifecycleOwner) {

        }
        tracks.observeNonNull(viewLifecycleOwner) {

        }
    }
}