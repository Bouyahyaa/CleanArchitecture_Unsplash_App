package com.example.cleanarchitectureunsplashapp.presentation.pictures.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun PictureListItem(
    painterBaseImage: Painter,
    painterUserImage: Painter,
    username: String,
    contentDescription: String,
    color: MutableState<Color>,
) {

    Card(
        modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(15.dp), elevation = 5.dp
    ) {
        Box(modifier = Modifier.height(200.dp)) {
            Image(
                painter = painterBaseImage,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop
            )
            Box(modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent, color.value
                        ), startY = 300f
                    )
                )
                .fillMaxSize()
                .clickable {
                    color.value = Color(
                        Random.nextFloat(), Random.nextFloat(), Random.nextFloat(), 1f
                    )
                })

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                UserImageNameItem(
                    painter = painterUserImage,
                    username = username,
                    contentDescription = contentDescription
                )
            }
        }
    }
}