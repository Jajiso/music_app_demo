package com.example.musicapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Song(
    @SerializedName("trackId")
    var songId: String = "",
    @SerializedName("trackName")
    var name: String = "",
    @SerializedName("previewUrl")
    var previewUrl: String = ""
)

@Entity(tableName = "songTable")
data class SongEntity(
    @PrimaryKey
    @ColumnInfo(name = "song_id")
    var songId: String = "",
    @ColumnInfo(name = "song_name")
    var name: String = "",
    @ColumnInfo(name= "song_preview_url")
    var previewUrl: String = ""
)

fun Song.asSongEntity(): SongEntity =
    SongEntity(this.songId, this.name, this.previewUrl)

fun SongEntity.asSong(): Song =
    Song(this.songId, this.name, this.previewUrl)
