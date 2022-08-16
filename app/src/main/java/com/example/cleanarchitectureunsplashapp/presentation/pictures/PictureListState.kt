package com.example.cleanarchitectureunsplashapp.presentation.pictures

import com.example.cleanarchitectureunsplashapp.domain.model.Picture

data class PictureListState(
    val isLoading: Boolean = false,
    val pictures: List<Picture> = emptyList(),
    val error: String = ""
)