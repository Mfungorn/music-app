package com.fungorn.musicapp.di

import com.fungorn.musicapp.data.network.repository.MusicRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory { MusicRepository(get()) }
}