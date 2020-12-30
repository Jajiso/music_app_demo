package com.example.musicapp.data.model

import com.google.gson.annotations.SerializedName

data class Song(
    @SerializedName("trackName")
    var name: String = ""
)
