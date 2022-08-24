package com.example.cleanarchitectureunsplashapp.presentation.stories.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.progressSemantics
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SegmentedProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    backgroundColor: Color = color.copy(alpha = 0.25f),
    strokeWidth: Dp = 4.dp,
    listImages: List<Int>,
    image: MutableState<Int>,
    numberOfSegments: Int = listImages.size,
    segmentGap: Dp = 8.dp,
) {
    val gap: Float
    val stroke: Float
    with(LocalDensity.current) {
        gap = segmentGap.toPx()
        stroke = strokeWidth.toPx()
    }
    Canvas(
        modifier = modifier
            .progressSemantics(progress)
            .fillMaxWidth()
            .height(strokeWidth)
            .focusable()
    ) {
        drawSegments(1f, backgroundColor, stroke, numberOfSegments, gap, listImages, image)
        drawSegments(progress, color, stroke, numberOfSegments, gap, listImages, image)
    }
}

private fun DrawScope.drawSegments(
    progress: Float,
    color: Color,
    strokeWidth: Float,
    segments: Int,
    segmentGap: Float,
    listImages: List<Int>,
    image: MutableState<Int>
) {
    val width = size.width
    val start = 0f
    val gaps = (segments - 1) * segmentGap
    val segmentWidth = (width - gaps) / segments
    val barsWidth = segmentWidth * segments
    val end = barsWidth * progress + (progress * segments).toInt() * segmentGap

    repeat(segments) { index ->
        Log.e("segmentsIndex", "$index")
        val offset = index * (segmentWidth + segmentGap)
        image.value = listImages[index]
        if (offset < end) {
            val barEnd = (offset + segmentWidth).coerceAtMost(end)
            drawLine(
                color,
                Offset(start + offset, 0f),
                Offset(barEnd, 0f),
                strokeWidth
            )
        }
    }
}