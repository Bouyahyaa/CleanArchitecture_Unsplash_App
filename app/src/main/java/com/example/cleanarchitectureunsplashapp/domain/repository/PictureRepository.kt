package com.example.cleanarchitectureunsplashapp.domain.repository

import com.example.cleanarchitectureunsplashapp.domain.model.Picture

interface PictureRepository {
    suspend fun getPictures(query: String, fetchFromRemote: Boolean): List<Picture>
    suspend fun deletePictures(picture: Picture)
}