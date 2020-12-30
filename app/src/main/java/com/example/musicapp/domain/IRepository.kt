package com.example.musicapp.domain

import com.example.musicapp.data.model.Album
import com.example.musicapp.data.model.Artist
import com.example.musicapp.data.model.Song
import com.example.musicapp.valueObject.Resource

interface IRepository {
    suspend fun getArtistList(artistName: String):Resource<List<Artist>>
    suspend fun getAlbumList(artistId: String):Resource<List<Album>>
    suspend fun getSongList(albumId: String):Resource<List<Song>>
}