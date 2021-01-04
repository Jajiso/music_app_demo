package com.example.musicapp.data.local

import com.example.musicapp.data.model.Song
import com.example.musicapp.data.model.asSongEntity

class LocalDataSource( private val appDatabase: AppDatabase ) {

    suspend fun insertSong(song: Song) {
        appDatabase.songDao().insertSong( song.asSongEntity() )
    }

    suspend fun deleteSong(song: Song) {
        appDatabase.songDao().deleteSong( song.asSongEntity() )
    }

    suspend fun isLikedSong(song: Song): Boolean {
        return appDatabase.songDao().getSongById(song.songId) != null
    }
}