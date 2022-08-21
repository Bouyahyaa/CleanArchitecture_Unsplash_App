package com.example.cleanarchitectureunsplashapp.data.repository

import android.util.Log
import com.example.cleanarchitectureunsplashapp.data.local.PictureLocalSource
import com.example.cleanarchitectureunsplashapp.data.mapper.toPicture
import com.example.cleanarchitectureunsplashapp.data.mapper.toPictureEntity
import com.example.cleanarchitectureunsplashapp.data.remote.PictureRemoteSource
import com.example.cleanarchitectureunsplashapp.domain.model.Picture
import com.example.cleanarchitectureunsplashapp.domain.repository.PictureRepository
import javax.inject.Inject

class PictureRepositoryImpl @Inject constructor(
    private val pictureLocalSource: PictureLocalSource,
    private val pictureRemoteSource: PictureRemoteSource
) : PictureRepository {
    override suspend fun getPictures(query: String, fetchFromRemote: Boolean): List<Picture> {
        val localPictures = pictureLocalSource.getPictures(query)
        val isDbEmpty = localPictures.isEmpty() && query.isBlank()
        val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
        Log.e("shouldJustLoadFromCache", "$shouldJustLoadFromCache")


        if (shouldJustLoadFromCache) {
            Log.e("consuming", "locally")
            return localPictures.map { it.toPicture() }
        }

        val remotePictures = pictureRemoteSource.getPictures()
        remotePictures.let { pictures ->
            pictureLocalSource.clearPictures()
            pictures.map {
                val pictureEntity = it.toPictureEntity()
                pictureLocalSource.insertPicture(pictureEntity)
            }
        }
        Log.e("consuming", "remotely")

        return pictureLocalSource.getPictures(query).map { it.toPicture() }
    }

    override suspend fun deletePictures(picture: Picture) {
        val pictureEntity = picture.toPictureEntity()
        pictureLocalSource.deletePicture(pictureEntity)
    }
}