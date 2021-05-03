package com.fungorn.musicapp.di

import com.fungorn.musicapp.ui.main.MainViewModel
import com.fungorn.musicapp.ui.prediction.PredictionDetailViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { (trackId: String) -> PredictionDetailViewModel(trackId, get()) }
}