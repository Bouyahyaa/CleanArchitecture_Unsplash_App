package com.example.cleanarchitectureunsplashapp.data.remote

import com.example.cleanarchitectureunsplashapp.data.remote.dto.PictureDto
import javax.inject.Inject

class PictureRemoteSource @Inject constructor(
    private val api: PictureApi
) {
    suspend fun getPictures(): List<PictureDto> {
        return api.getPictures(1, 30, "sort")
    }
}