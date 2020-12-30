package com.example.musicapp.data.model

import com.google.gson.annotations.SerializedName

data class RequestedObject<T>(
    @SerializedName("resultCount")
    val resultCount: String,
    @SerializedName("results")
    var result: List<T>
)