package com.fungorn.musicapp

import android.app.Application
import com.fungorn.musicapp.di.appModule
import com.fungorn.musicapp.di.networkModule
import com.fungorn.musicapp.di.repositoryModule
import com.fungorn.musicapp.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MusicApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MusicApplication)
            modules(appModule + networkModule + useCaseModule + repositoryModule)
        }
    }
}