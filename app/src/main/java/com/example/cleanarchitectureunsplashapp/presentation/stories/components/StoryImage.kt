package com.example.cleanarchitectureunsplashapp.presentation.stories.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState

@OptIn(ExperimentalPagerApi::class, ExperimentalCoilApi::class)
@Composable
fun StoryImage(
    modifier: Modifier,
    pagerState: PagerState,
    listOfImages: List<String?>,
    pictureLoaded: MutableState<Boolean>,
) {
    HorizontalPager(
        state = pagerState,
        dragEnabled = false,
        modifier = modifier
    ) { page ->

        val painter = rememberImagePainter(data = listOfImages[pagerState.currentPage])
        val painterState = painter.state
        Log.e("pictureLoaded", "${page}")

        if (page == pagerState.currentPage) {
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
                )
            }
        }
    }
}