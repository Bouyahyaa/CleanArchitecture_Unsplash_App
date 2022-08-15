package com.example.cleanarchitectureunsplashapp.data.repository

import com.example.cleanarchitectureunsplashapp.data.remote.PictureApi
import com.example.cleanarchitectureunsplashapp.data.remote.dto.PictureDto
import com.example.cleanarchitectureunsplashapp.domain.repository.PictureRepository
import javax.inject.Inject

class PictureRepositoryImpl @Inject constructor(
    private val api: PictureApi
) : PictureRepository {
    override suspend fun getPictures(): List<PictureDto> {
        return api.getRecentPhotos(1, 30, "sort")
    }
}