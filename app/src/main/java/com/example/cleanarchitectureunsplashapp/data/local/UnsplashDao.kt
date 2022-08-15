package com.example.cleanarchitectureunsplashapp.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UnsplashDao {
    @Query("SELECT * FROM pictures")
    fun getPictures(): Flow<List<PictureEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPicture(picture: PictureEntity)

    @Delete
    suspend fun deletePicture(picture: PictureEntity)
}