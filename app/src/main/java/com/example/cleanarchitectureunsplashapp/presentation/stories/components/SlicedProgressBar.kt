package com.example.cleanarchitectureunsplashapp.presentation.stories.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun SlicedProgressBar(listImages: List<Int>) {
    var running by remember { mutableStateOf(false) }
    val image = remember {
        mutableStateOf(listImages[0])
    }
    val progress: Float by animateFloatAsState(
        if (running) 1f else 0f, animationSpec = tween(
            durationMillis = 10_000, easing = LinearEasing
        )
    )
    Column(
        modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SegmentedProgressIndicator(
            progress = progress,
            modifier = Modifier
                .padding(top = 20.dp, start = 10.dp, end = 10.dp)
                .fillMaxWidth(),
            listImages = listImages,
            image = image
        )

        Button(
            onClick = { running = !running }, modifier = Modifier.padding(top = 32.dp)
        ) {
            Text(
                text = if (running) "Reverse Animation" else "Start Animation"
            )
        }

        Spacer(modifier = Modifier.size(30.dp))

        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = image.value),
            contentDescription = "Image"
        )
    }
}