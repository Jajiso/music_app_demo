package com.example.musicapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.musicapp.data.model.Album
import com.example.musicapp.domain.IRepository
import com.example.musicapp.valueObject.Resource
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val repository: IRepository) : ViewModel() {

    val fetchAlbumList = liveData<Resource<List<Album>>>(Dispatchers.IO) {
        emit(Resource.Loading() )
        try {
            emit(repository.getAlbumList() )
        } catch (e: Exception) {
            emit(Resource.Failure(e) )
        }
    }
}