package com.example.cleanarchitectureunsplashapp.data.repository

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
    override suspend fun getPictures(): List<Picture> {
        val localPictures = pictureLocalSource.getPictures()

        if (localPictures.isNotEmpty()) {
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
        return pictureLocalSource.getPictures().map { it.toPicture() }
    }
}