package com.example.musicapp.domain

import com.example.musicapp.data.DataSource
import com.example.musicapp.data.model.Album
import com.example.musicapp.valueObject.Resource

class Repository (private val dataSource: DataSource): IRepository {

    override fun getAlbumList(): Resource<List<Album>> {
        return dataSource.getAlbumList()
    }
}