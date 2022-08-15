package com.example.cleanarchitectureunsplashapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PictureEntity::class],
    version = 1
)

abstract class UnsplashDatabase : RoomDatabase() {
    abstract val unsplashDao: UnsplashDao

    companion object {
        const val DATABASE_NAME = "unsplash_db"
    }
}