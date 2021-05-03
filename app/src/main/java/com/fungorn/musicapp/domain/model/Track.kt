package com.fungorn.musicapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Track(
    val id: String,
    val song: String,
    val artist: String,
    val year: Int
) : Parcelable
