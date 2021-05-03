package com.fungorn.musicapp.data.network.repository

import com.fungorn.musicapp.data.mapper.TrackMapper
import com.fungorn.musicapp.data.network.service.MusicService

class MusicRepository(
    private val musicService: MusicService
) {
    suspend fun getTracks() =
        musicService.getTracks().map(TrackMapper::map)

    suspend fun getTrackPredictions(trackId: String) =
        musicService.getTrackPrediction(trackId).map(TrackMapper::map)
}