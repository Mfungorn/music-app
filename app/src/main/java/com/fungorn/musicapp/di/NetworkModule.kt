package com.fungorn.musicapp.di

import com.fungorn.musicapp.BuildConfig
import com.fungorn.musicapp.common.Const
import com.fungorn.musicapp.data.network.DateTimeDeserializer
import com.fungorn.musicapp.data.network.DateTimeSerializer
import com.fungorn.musicapp.data.network.service.MusicService
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.joda.time.DateTime
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        GsonBuilder()
            .setDateFormat(Const.API_DATE_FORMAT)
            .registerTypeAdapter(DateTime::class.java, DateTimeSerializer())
            .registerTypeAdapter(DateTime::class.java, DateTimeDeserializer())
            .setPrettyPrinting()
            .create()
    }

    single {
        OkHttpClient().newBuilder()
            .cache(Cache(androidApplication().cacheDir, 10 * 1024 * 1024)) // 10 MB
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .callTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(0, TimeUnit.SECONDS)
            .readTimeout(0, TimeUnit.SECONDS)
            .connectTimeout(0, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    single { get<Retrofit>().create<MusicService>() }
}