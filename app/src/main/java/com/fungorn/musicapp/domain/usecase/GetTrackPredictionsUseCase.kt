package com.fungorn.musicapp.domain.usecase

import com.fungorn.musicapp.data.network.repository.MusicRepository

class GetTrackPredictionsUseCase(
    private val musicRepository: MusicRepository
) {
    suspend operator fun invoke(trackId: String, year: Int? = null) =
        musicRepository.getTrackPredictions(trackId, year)
}