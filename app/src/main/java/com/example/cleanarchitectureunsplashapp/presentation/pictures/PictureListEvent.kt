package com.example.cleanarchitectureunsplashapp.presentation.pictures

sealed class PictureListEvent {
    data class OnSearchQueryChange(val query: String) : PictureListEvent()
    object Refresh : PictureListEvent()
}