package com.example.cleanarchitectureunsplashapp.presentation.stories.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.cleanarchitectureunsplashapp.ui.theme.Shapes
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState

@OptIn(ExperimentalPagerApi::class, ExperimentalCoilApi::class)
@Composable
fun StoryImage(
    pagerState: PagerState,
    listOfImages: List<String?>,
    pictureLoaded: MutableState<Boolean>,
) {
    HorizontalPager(
        state = pagerState,
        dragEnabled = false,
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) { page ->

        val painter = rememberImagePainter(data = listOfImages[page])

        val painterState = painter.state

        if (page == pagerState.currentPage) {
            Log.e("pictureLoaded", "${pictureLoaded.value}")
            pictureLoaded.value = painterState !is ImagePainter.State.Loading
            if (!pictureLoaded.value) {
                CircularProgressIndicator(
                    color = Color.LightGray,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}