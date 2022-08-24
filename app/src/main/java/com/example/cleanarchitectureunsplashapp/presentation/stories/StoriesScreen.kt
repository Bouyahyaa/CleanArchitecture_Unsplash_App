package com.example.cleanarchitectureunsplashapp.presentation.stories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.cleanarchitectureunsplashapp.R
import com.example.cleanarchitectureunsplashapp.presentation.stories.components.SlicedProgressBar

@Composable
fun StoriesScreen() {
    val listImages = listOf(
        R.drawable.ic_launcher_background,
        R.drawable.ic_launcher_foreground
    )

    Column(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {
        SlicedProgressBar(listImages = listImages)
    }
}