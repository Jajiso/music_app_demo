package com.example.musicapp.data

import com.example.musicapp.data.model.Album
import com.example.musicapp.valueObject.Resource

class DataSource {

    private val generateAlbumList = listOf(
            Album("1", "name1", "date1", "https://www.zooplus.es/magazine/wp-content/uploads/2018/04/fotolia_169457098.jpg"),
            Album("2", "name2", "date2", "https://www.zooplus.es/magazine/wp-content/uploads/2018/04/fotolia_169457098.jpg"),
            Album("3", "name3", "date3", "https://www.zooplus.es/magazine/wp-content/uploads/2018/04/fotolia_169457098.jpg"),
            Album("4", "name4", "date4", "https://www.zooplus.es/magazine/wp-content/uploads/2018/04/fotolia_169457098.jpg")
    )

    fun getAlbumList(): Resource<List<Album>> {
        return Resource.Success(generateAlbumList)
    }
}