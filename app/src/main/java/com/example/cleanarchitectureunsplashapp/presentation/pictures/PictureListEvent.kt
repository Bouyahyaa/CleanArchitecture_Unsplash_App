package com.example.cleanarchitectureunsplashapp.presentation.pictures

import com.example.cleanarchitectureunsplashapp.domain.model.Picture

sealed class PictureListEvent {
    data class OnSearchQueryChange(val query: String) : PictureListEvent()
    data class DeletePicture(val picture: Picture, val query: String) : PictureListEvent()
    data class LikePicture(val id: String) : PictureListEvent()
    object Refresh : PictureListEvent()
}