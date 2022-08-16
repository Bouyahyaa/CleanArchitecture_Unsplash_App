package com.example.cleanarchitectureunsplashapp.domain.repository

import com.example.cleanarchitectureunsplashapp.domain.model.Picture

interface PictureRepository {
    suspend fun getPictures(): List<Picture>
}