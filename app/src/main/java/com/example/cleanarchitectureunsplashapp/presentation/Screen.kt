package com.example.cleanarchitectureunsplashapp.presentation

sealed class Screen(val route: String) {
    object PictureListScreen : Screen("picture_list_screen")
    object StoriesScreen : Screen("stories_screen")
}