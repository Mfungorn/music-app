package com.fungorn.musicapp.data.network.service

import com.fungorn.musicapp.data.network.model.TrackResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MusicService {

    @GET("/")
    suspend fun getTracks(): List<TrackResponse>

    @GET("/prediction/{track_id}")
    suspend fun getTrackPrediction(
        @Path("track_id") id: String,
        @Query("y") year: Int?
    ): List<TrackResponse>
}