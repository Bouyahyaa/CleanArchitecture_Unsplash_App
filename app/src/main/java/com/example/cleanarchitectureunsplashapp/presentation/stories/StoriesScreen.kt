package com.example.cleanarchitectureunsplashapp.presentation.stories

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cleanarchitectureunsplashapp.presentation.Screen
import com.example.cleanarchitectureunsplashapp.presentation.stories.components.LinearIndicator
import com.example.cleanarchitectureunsplashapp.presentation.stories.components.StoryImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalPagerApi::class)
@Composable
fun StoriesScreen(
    pictureUrl: String?,
    userPictureUrl: String?,
    viewModel: StoriesViewModel = hiltViewModel(),
    navController: NavController,
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

    viewModel.onEvent(StoriesEvent.GetStories(listOfImages = listImages))

    val state = viewModel.state.value

    val pagerState = rememberPagerState(pageCount = state.stories.size)

    val coroutineScope = rememberCoroutineScope()

    var currentPage by remember {
        mutableStateOf(0)
    }

    val isPressed = remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        StoryImage(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize()
                .pointerInput(Unit) {
                    val maxWidth = this.size.width // (2)
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
                                    if (currentPage < state.stories.size - 1) {
                                        viewModel.onEvent(StoriesEvent.RightTap(currentPage))
                                        currentPage++
                                        pagerState.animateScrollToPage(currentPage)
                                    }
                                } else {
                                    Log.e("TapFinger", "left")
                                    if (currentPage > 0) {
                                        viewModel.onEvent(StoriesEvent.LeftTap(currentPage))
                                        currentPage--
                                        pagerState.animateScrollToPage(currentPage)
                                    }
                                }
                            }
                            isPressed.value = false
                        },
                        onLongPress = {
                            Log.e("TapFinger", "LongPressed")
                        })
                },
            pagerState = pagerState,
            listOfStories = state.stories,
            setImageLoaded = { id ->
                viewModel.onEvent(StoriesEvent.ImageLoaded(id = id))
            }
        )

        LazyRow {
            items(state.stories) { story ->
                Spacer(modifier = Modifier.padding(4.dp))

                LinearIndicator(
                    modifier = Modifier.width(175.dp),
                    progressValue = story.progress,
                    startProgress = story.isLoaded
                ) {
                    coroutineScope.launch {
                        if (currentPage < state.stories.size - 1) {
                            viewModel.onEvent(StoriesEvent.RightTap(currentPage))
                            currentPage++
                            pagerState.animateScrollToPage(currentPage)
                        } else {
                            navController.navigate(Screen.PictureListScreen.route)
                        }
                    }
                }
                Spacer(modifier = Modifier.padding(4.dp))
            }
        }
    }
}