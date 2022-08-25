package com.example.cleanarchitectureunsplashapp.presentation.stories

import android.util.Log
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
    pictureUrl: String?
) {
    val listImages = listOf(
        pictureUrl?.replace(
            "%3A",
            ":"
        )?.replace("%2F", "/")
    )
    val pagerState = rememberPagerState(pageCount = listImages.size)
    val coroutineScope = rememberCoroutineScope()
    var currentPage by remember {
        mutableStateOf(0)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        StoryImage(pagerState = pagerState, listOfImages = listImages)

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.padding(4.dp))

            for (index in listImages.indices) {
                LinearIndicator(
                    modifier = Modifier.weight(1f), startProgress = index == currentPage
                ) {
                    coroutineScope.launch {
                        if (currentPage < listImages.size - 1) {
                            currentPage++
                        }
                        Log.e("currentPage", "$currentPage")
                        pagerState.animateScrollToPage(currentPage)
                    }
                }

                Spacer(modifier = Modifier.padding(4.dp))
            }
        }
    }
}