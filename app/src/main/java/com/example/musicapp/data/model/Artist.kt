package com.example.musicapp.data.model

import com.google.gson.annotations.SerializedName

data class Artist(
    @SerializedName("artistId")
    val artistId: String = "",
    @SerializedName("artistName")
    val name: String = "",
    @SerializedName("primaryGenreName")
    val genre: String = ""
)
