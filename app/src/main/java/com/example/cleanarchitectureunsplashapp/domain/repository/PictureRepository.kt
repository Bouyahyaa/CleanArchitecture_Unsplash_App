package com.example.cleanarchitectureunsplashapp.domain.repository

import com.example.cleanarchitectureunsplashapp.data.remote.dto.PictureDto

interface PictureRepository {
    suspend fun getPictures(): List<PictureDto>
}