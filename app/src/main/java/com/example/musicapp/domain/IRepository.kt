package com.example.musicapp.domain

import com.example.musicapp.data.model.Album
import com.example.musicapp.valueObject.Resource

interface IRepository {
    fun getAlbumList():Resource<List<Album>>
}