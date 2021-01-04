package com.example.musicapp.domain

import com.example.musicapp.data.local.LocalDataSource
import com.example.musicapp.data.remote.NetworkDataSource
import com.example.musicapp.data.model.Album
import com.example.musicapp.data.model.Artist
import com.example.musicapp.data.model.Song
import com.example.musicapp.valueObject.Resource

class Repository (
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: LocalDataSource
): IRepository {
    override suspend fun getArtistList(artistName: String): Resource<List<Artist>> {
        return networkDataSource.getArtistByName(artistName)
    }

    override suspend fun getAlbumList(artistId: String): Resource<List<Album>> {
        return networkDataSource.getAlbumsByArtistId(artistId)
    }

    override suspend fun getSongList(albumId: String): Resource<List<Song>> {
        return networkDataSource.getAllSongsByAlbumId(albumId)
    }

    override suspend fun isLikedSong(song: Song): Boolean {
        return localDataSource.isLikedSong(song)
    }

    override suspend fun insertLikedSong(song: Song) {
        localDataSource.insertSong(song)
    }

    override suspend fun deleteLikedSong(song: Song) {
        localDataSource.deleteSong(song)
    }
}