package com.fungorn.musicapp.di

import com.fungorn.musicapp.domain.usecase.GetAllTracksUseCase
import com.fungorn.musicapp.domain.usecase.GetTrackPredictionsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetAllTracksUseCase(get()) }
    factory { GetTrackPredictionsUseCase(get()) }
}