package com.example.cleanarchitectureunsplashapp.presentation.stories.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun LinearIndicator(
    modifier: Modifier,
    onProgress: Pair<Int, Boolean>,
    index: Int,
    onAnimationEnd: () -> (Unit),
) {

    val progress = remember {
        mutableStateOf(0.00f)
    }

    if (onProgress.second && onProgress.first == index) {
        LaunchedEffect(key1 = Unit) {
            progress.value = 0.00f
            while (progress.value < 1f) {
                progress.value += 0.01f
                delay(50)
            }
            onAnimationEnd()
        }
    }

    val targetProgress = if (onProgress.first > index) {
        1f
    } else if (onProgress.first < index) {
        0.00f
    } else {
        progress.value
    }

    LinearProgressIndicator(
        backgroundColor = Color.LightGray,
        color = Color.White,
        modifier = modifier
            .padding(top = 12.dp, bottom = 12.dp)
            .clip(RoundedCornerShape(12.dp)),
        progress = targetProgress
    )


    /** Animation ********************************************************
    val animatedProgress by animateFloatAsState(
    targetValue = progress,
    animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )
     ***************************************************************** **/
}