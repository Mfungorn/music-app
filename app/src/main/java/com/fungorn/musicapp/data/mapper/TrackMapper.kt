package com.fungorn.musicapp.data.mapper

import com.fungorn.musicapp.data.network.model.TrackResponse
import com.fungorn.musicapp.domain.model.Track

object TrackMapper : Mapper<TrackResponse, Track> {
    override fun map(t: TrackResponse): Track = Track(
        t.id,
        t.song,
        t.artist,
        t.year
    )
}