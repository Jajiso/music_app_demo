package com.example.musicapp.data.local

import androidx.room.*
import com.example.musicapp.data.model.SongEntity

@Dao
interface SongDao {
    @Query("SELECT * FROM songTable WHERE song_id = :songId")
    suspend fun getSongById(songId: String): SongEntity?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSong(songEntity: SongEntity)
    @Delete
    suspend fun deleteSong(songEntity: SongEntity)
}