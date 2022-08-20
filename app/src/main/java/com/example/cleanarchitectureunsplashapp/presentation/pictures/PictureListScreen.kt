package com.example.cleanarchitectureunsplashapp.presentation.pictures

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.cleanarchitectureunsplashapp.presentation.pictures.components.PictureListItem
import com.example.cleanarchitectureunsplashapp.presentation.pictures.components.StoryListItem

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun PictureListScreen(
    navController: NavController, viewModel: PictureListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val text = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
    ) {
        LazyRow {
            items(state.pictures) { picture ->
                StoryListItem(
                    painterStoryImage = rememberImagePainter(picture.large),
                    contentDescription = picture.description!!,
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {
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
                        painterBaseImage = rememberImagePainter(picture.regular),
                        painterUserImage = rememberImagePainter(picture.small),
                        username = picture.username!!,
                        contentDescription = picture.description!!,
                        color = color,
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
}
