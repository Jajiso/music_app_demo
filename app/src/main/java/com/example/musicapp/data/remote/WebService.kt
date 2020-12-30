package com.example.musicapp.data.remote

import com.example.musicapp.data.model.Album
import com.example.musicapp.data.model.Artist
import com.example.musicapp.data.model.RequestedObject
import com.example.musicapp.data.model.Song
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {
    @GET("search?entity=musicArtist")
    suspend fun getArtistsByName(@Query("term") artistName: String): RequestedObject<Artist> //List<Artist>
    @GET("lookup?entity=album")
    suspend fun getAlbumsByArtistId(@Query("id") artistId: String): RequestedObject<Album> //List<Album>
    @GET("lookup?entity=song")
    suspend fun getAllSongsByAlbumId(@Query("id")albumId: String): RequestedObject<Song> //List<Song>
}