package com.fungorn.musicapp.domain.usecase

import com.fungorn.musicapp.data.network.repository.MusicRepository

class GetAllTracksUseCase(
    private val musicRepository: MusicRepository
) {
    suspend operator fun invoke() = musicRepository.getTracks()
}