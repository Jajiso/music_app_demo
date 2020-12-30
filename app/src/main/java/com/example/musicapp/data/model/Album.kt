package com.example.musicapp.data.model

import com.google.gson.annotations.SerializedName

data class Album(
    @SerializedName("collectionId")
    val albumId: String = "",
    @SerializedName("collectionName")
    val name: String = "",
    @SerializedName("releaseDate")
    val releaseDate: String = "",
    @SerializedName("artworkUrl100")
    val image: String = ""
)
