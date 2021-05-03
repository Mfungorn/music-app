package com.fungorn.musicapp.data.network.model

import com.google.gson.annotations.SerializedName

data class TrackResponse(
    @SerializedName("id") val id: String,
    @SerializedName("artist") val artist: String,
    @SerializedName("song") val song: String,
    @SerializedName("year") val year: Int,
    @SerializedName("valence") val valence: Float,
    @SerializedName("tempo") val tempo: Float,
    @SerializedName("speechiness") val speechiness: Float,
    @SerializedName("loudness") val loudness: Float,
    @SerializedName("instrumentalness") val instrumentalness: Float,
    @SerializedName("energy") val energy: Float,
    @SerializedName("danceability") val danceability: Float,
    @SerializedName("acousticness") val acousticness: Float
)
