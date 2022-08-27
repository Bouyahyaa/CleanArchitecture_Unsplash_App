package com.example.cleanarchitectureunsplashapp.presentation.stories

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cleanarchitectureunsplashapp.presentation.stories.components.LinearIndicator
import com.example.cleanarchitectureunsplashapp.presentation.stories.components.StoryImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun StoriesScreen(
    pictureUrl: String?,
    userPictureUrl: String?,
) {

    val basePicture = pictureUrl?.replace(
        "%3A",
        ":"
    )?.replace("%2F", "/")

    val userPicture = userPictureUrl?.replace(
        "%3A",
        ":"
    )?.replace("%2F", "/")

    val listImages = listOf(
        basePicture, userPicture
    )
    val pagerState = rememberPagerState(pageCount = listImages.size)
    val coroutineScope = rememberCoroutineScope()
    var currentPage by remember {
        mutableStateOf(0)
    }
    val pictureLoaded = remember {
        mutableStateOf(false)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        StoryImage(
            pagerState = pagerState,
            listOfImages = listImages,
            pictureLoaded = pictureLoaded
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.padding(4.dp))

            for (index in listImages.indices) {
                LinearIndicator(
                    modifier = Modifier.weight(1f),
                    startProgress = index == currentPage && pictureLoaded.value
                ) {
                    coroutineScope.launch {
                        if (currentPage < listImages.size - 1) {
                            currentPage++
                        }
                        pagerState.animateScrollToPage(currentPage)
                    }
                }
                Spacer(modifier = Modifier.padding(4.dp))
            }
        }
    }
}