package com.example.cleanarchitectureunsplashapp.presentation.stories.components

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun LinearIndicator(
    modifier: Modifier, startProgress: Boolean = false, onAnimationEnd: () -> Unit
) {

    Log.e("pictureLoaded", "$startProgress")


    var progress by remember {
        mutableStateOf(0.00f)
    }

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )

    if (startProgress) {
        LaunchedEffect(key1 = Unit) {
            while (progress < 1f) {
                progress += 0.01f
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
        progress = animatedProgress
    )
}