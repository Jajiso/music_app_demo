package com.example.musicapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.musicapp.data.model.SongEntity

@Database(entities = [SongEntity::class], version = 1 )
abstract class AppDatabase: RoomDatabase() {
    abstract fun songDao(): SongDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            INSTANCE = INSTANCE ?: Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "song_table").build()
            return INSTANCE!!
        }
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}