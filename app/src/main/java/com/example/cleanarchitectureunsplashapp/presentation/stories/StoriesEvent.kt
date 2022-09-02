package com.example.cleanarchitectureunsplashapp.presentation.stories

sealed class StoriesEvent {
    data class ImageLoaded(val id: String) : StoriesEvent()
    data class RightTap(val index: Int) : StoriesEvent()
    data class LeftTap(val index: Int) : StoriesEvent()
}