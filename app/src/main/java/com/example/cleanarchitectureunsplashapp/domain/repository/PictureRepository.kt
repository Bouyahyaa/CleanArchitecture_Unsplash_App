package com.example.cleanarchitectureunsplashapp.domain.repository

import com.example.cleanarchitectureunsplashapp.core.Resource
import com.example.cleanarchitectureunsplashapp.domain.model.Picture
import kotlinx.coroutines.flow.Flow

interface PictureRepository {
    fun getPictures(query: String, fetchFromRemote: Boolean): Flow<Resource<List<Picture>>>
    suspend fun deletePictures(picture: Picture)
}