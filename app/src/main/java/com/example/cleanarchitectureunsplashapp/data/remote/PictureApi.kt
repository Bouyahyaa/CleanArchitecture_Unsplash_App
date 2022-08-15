package com.example.cleanarchitectureunsplashapp.data.remote

import com.example.cleanarchitectureunsplashapp.core.Constants
import com.example.cleanarchitectureunsplashapp.data.remote.dto.PictureDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureApi {
    @GET("photos/?client_id=${Constants.APP_ID}")
    fun getRecentPhotos(
        @Query("page") page: Int,
        @Query("per_page") pageLimit: Int,
        @Query("order_by") order: String
    ): List<PictureDto>
}