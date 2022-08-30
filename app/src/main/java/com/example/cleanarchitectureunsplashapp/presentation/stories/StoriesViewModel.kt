package com.example.cleanarchitectureunsplashapp.presentation.stories

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.cleanarchitectureunsplashapp.domain.model.Story
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class StoriesViewModel @Inject constructor() : ViewModel() {

    private val _state = mutableStateOf(StoriesState())
    val state: State<StoriesState> = _state

    fun onEvent(event: StoriesEvent) {
        when (event) {
            is StoriesEvent.GetStories -> {
                getStories(listOfImages = event.listOfImages)
            }

            is StoriesEvent.ImageLoaded -> {
                val stories = state.value.stories.map {
                    if (it.id == event.id) {
                        it.copy(isLoaded = true)
                    } else {
                        it
                    }
                }
                _state.value = state.value.copy(
                    stories = stories
                )
            }
        }
    }

    private fun getStories(listOfImages: List<String?>) {
        _state.value = StoriesState(
            stories = listOfImages.map {
                Story(id = UUID.randomUUID().toString(), picture = it)
            }
        )
    }
}