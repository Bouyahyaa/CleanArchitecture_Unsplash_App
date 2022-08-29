package com.example.cleanarchitectureunsplashapp.presentation.stories

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
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
        "%3A", ":"
    )?.replace("%2F", "/")

    val userPicture = userPictureUrl?.replace(
        "%3A", ":"
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

    val isPressed = remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        StoryImage(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize()
                .pointerInput(Unit) {
                    val maxWidth = this.size.width // (2)
                    Log.e("offestX", "${maxWidth}")
                    detectTapGestures(
                        onPress = {
                            val pressStartTime = System.currentTimeMillis()
                            isPressed.value = true
                            this.tryAwaitRelease() // (4)
                            val pressEndTime = System.currentTimeMillis()
                            val totalPressTime = pressEndTime - pressStartTime // (5)
                            if (totalPressTime < 200) {
                                val isTapOnRightTwoTiers = (it.x > (maxWidth / 4)) // (6)
                                if (isTapOnRightTwoTiers) {
                                    Log.e("TapFinger", "right")
                                } else {
                                    Log.e("TapFinger", "left")
                                }
                            }
                            isPressed.value = false
                        },
                        onLongPress = {
                            Log.e("TapFinger", "LongPressed")
                        })
                },
            pagerState = pagerState,
            listOfImages = listImages,
            pictureLoaded = pictureLoaded
        )

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.padding(4.dp))

            for (index in listImages.indices) {
                LinearIndicator(
                    modifier = Modifier.weight(1f),
                    startProgress = pictureLoaded.value && index == currentPage,
                ) {
                    Log.e("animationEnd", "animationEnd")
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