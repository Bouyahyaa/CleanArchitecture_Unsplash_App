package com.example.cleanarchitectureunsplashapp.presentation.stories

import com.example.cleanarchitectureunsplashapp.domain.model.Story

data class StoriesState(
    val stories: List<Story> = emptyList(),
)
