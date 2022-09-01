package com.example.cleanarchitectureunsplashapp.presentation.stories

sealed class StoriesEvent {
    data class GetStories(val listOfImages: List<String?>) : StoriesEvent()
    data class ImageLoaded(val id: String) : StoriesEvent()
    data class RightTap(val index: Int) : StoriesEvent()
    data class LeftTap(val index: Int) : StoriesEvent()
}