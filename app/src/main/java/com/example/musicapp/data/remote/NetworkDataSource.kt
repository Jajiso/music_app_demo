package com.example.musicapp.data.remote

import com.example.musicapp.data.model.Album
import com.example.musicapp.data.model.Artist
import com.example.musicapp.data.model.Song
import com.example.musicapp.valueObject.Resource
import com.example.musicapp.valueObject.RetrofitClient

class NetworkDataSource {

    suspend fun getArtistByName(artistName: String): Resource<List<Artist>> {
        return Resource.Success(
                RetrofitClient
                        .webService
                        .getArtistsByName(artistName)
                        .result
                        .filter {
                            it != Artist()
                        }
        )
    }

    suspend fun getAlbumsByArtistId(artistId: String): Resource<List<Album>> {
        return Resource.Success(
                RetrofitClient
                        .webService
                        .getAlbumsByArtistId(artistId)
                        .result
                        .filter {
                            it != Album()
                        }
        )
    }

    suspend fun getAllSongsByAlbumId(albumId: String): Resource<List<Song>> {
        return Resource.Success(
                RetrofitClient
                        .webService
                        .getAllSongsByAlbumId(albumId)
                        .result
                        .filter {
                            it != Song()
                        }
        )
    }
}