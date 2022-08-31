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
    progressValue: Float,
    startProgress: Boolean,
    onAnimationEnd: () -> (Unit),
) {

    val progress = remember {
        mutableStateOf(progressValue)
    }

    if (startProgress) {
        LaunchedEffect(key1 = Unit) {
            while (progress.value < 1f) {
                progress.value += 0.01f
                delay(50)
            }
            onAnimationEnd()
        }
    }

    LinearProgressIndicator(
        backgroundColor = Color.LightGray,
        color = Color.White,
        modifier = modifier
            .padding(top = 12.dp, bottom = 12.dp)
            .clip(RoundedCornerShape(12.dp)),
        progress = progress.value
    )


    /** Animation ********************************************************
    val animatedProgress by animateFloatAsState(
    targetValue = progress,
    animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )
     ***************************************************************** **/
}