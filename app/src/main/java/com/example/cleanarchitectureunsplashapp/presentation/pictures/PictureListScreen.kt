package com.example.cleanarchitectureunsplashapp.presentation.pictures

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.cleanarchitectureunsplashapp.R
import com.example.cleanarchitectureunsplashapp.presentation.pictures.components.PictureListItem

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun PictureListScreen(
    navController: NavController,
    viewModel: PictureListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val fontFamily = FontFamily(
        Font(R.font.gulzar_regular, FontWeight.Thin)
    )
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(5.dp)) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            cells = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(state.pictures) { picture ->
                val color = remember {
                    mutableStateOf(Color.Black)
                }
                PictureListItem(
                    painter = rememberImagePainter(picture.regular),
                    contentDescription = picture.description!!,
                    title = picture.name!!,
                    color = color,
                    fontFontFamily = fontFamily
                )
            }
        }

        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
        }

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}