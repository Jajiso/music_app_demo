package com.example.musicapp.viewmodel

import androidx.lifecycle.*
import com.example.musicapp.data.model.Song
import com.example.musicapp.domain.IRepository
import kotlinx.coroutines.launch

class SongViewModel(private val repository: IRepository): ViewModel() {

    private val _song = MutableLiveData<Song>()
    val song: LiveData<Song> by this::_song

    private val _isLiked = MutableLiveData(false)
    val isLiked: LiveData<Boolean> by this::_isLiked

    fun setSong(song: Song) {
        _song.value = song
    }

    fun setIsLiked(song: Song) {
        viewModelScope.launch {
            _isLiked.value = repository.isLikedSong(song)
        }
    }

    fun deleteOrInsertLikedSong(song: Song) {
        viewModelScope.launch {
            if (repository.isLikedSong(song) ) {
                repository.deleteLikedSong(song)
                _isLiked.value = repository.isLikedSong(song)
            } else {
                repository.insertLikedSong(song)
                _isLiked.value = repository.isLikedSong(song)
            }
        }
    }

    fun insertLikedSong() {
        viewModelScope.launch {
            repository.insertLikedSong(song.value!!)
        }
    }

    fun deleteLikedSong() {
        viewModelScope.launch {
            repository.deleteLikedSong(song.value!!)
        }
    }

    suspend fun isLikedSong(song: Song): Boolean = repository.isLikedSong(song)
}