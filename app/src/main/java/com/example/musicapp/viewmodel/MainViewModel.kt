package com.example.musicapp.viewmodel

import androidx.lifecycle.*
import com.example.musicapp.data.model.Album
import com.example.musicapp.data.model.Artist
import com.example.musicapp.data.model.Song
import com.example.musicapp.domain.IRepository
import com.example.musicapp.valueObject.Resource
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val repository: IRepository) : ViewModel() {

    var searchViewText = ""
    private val currentSearchByArtistName = MutableLiveData<String>()
    private val currentArtistId = MutableLiveData<String>()
    private val currentAlbumId = MutableLiveData<String>()

    fun setSearchByArtistName(artistName: String) {
        currentSearchByArtistName.value = artistName
    }

    fun setArtistId(artistId: String) {
        currentArtistId.value = artistId
    }

    fun setAlbumId(albumId: String) {
        currentAlbumId.value = albumId
    }

    val fetchArtistList = currentSearchByArtistName.distinctUntilChanged().switchMap { searchedName ->
        liveData<Resource<List<Artist>>>(Dispatchers.IO) {
            emit(Resource.Loading() )
            try {
                emit(repository.getArtistList(searchedName) )
            } catch (e: Exception) {
                emit(Resource.Failure(e) )
            }
        }
    }

    val fetchAlbumList = currentArtistId.distinctUntilChanged().switchMap { artistId ->
        liveData<Resource<List<Album>>>(Dispatchers.IO) {
            emit(Resource.Loading() )
            try {
                emit(repository.getAlbumList(artistId) )
            } catch (e: Exception) {
                emit(Resource.Failure(e) )
            }
        }
    }

    val fetchSongList = currentAlbumId.distinctUntilChanged().switchMap{ albumId ->
        liveData<Resource<List<Song>>>(Dispatchers.IO) {
            emit(Resource.Loading() )
            try {
                emit(repository.getSongList(albumId) )
            } catch (e: Exception) {
                emit(Resource.Failure(e) )
            }
        }
    }
}