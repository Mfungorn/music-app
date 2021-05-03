package com.fungorn.musicapp.di

import com.fungorn.musicapp.BuildConfig
import com.fungorn.musicapp.common.Const
import com.fungorn.musicapp.data.network.DateTimeDeserializer
import com.fungorn.musicapp.data.network.DateTimeSerializer
import com.fungorn.musicapp.data.network.service.MusicService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.joda.time.DateTime
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

val networkModule = module {
    factory {
        GsonBuilder()
            .setDateFormat(Const.API_DATE_FORMAT)
            .registerTypeAdapter(DateTime::class.java, DateTimeSerializer())
            .registerTypeAdapter(DateTime::class.java, DateTimeDeserializer())
            .setPrettyPrinting()
            .serializeNulls()
            .create()
    }

    factory {
        OkHttpClient().newBuilder()
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

    factory { get<Retrofit>().create<MusicService>() }
}