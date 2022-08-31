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
                if (_state.value.stories.isEmpty()) {
                    getStories(listOfImages = event.listOfImages)
                }
            }

            is StoriesEvent.ImageLoaded -> {
                val stories = _state.value.stories.map { story ->
                    if (story.id == event.id) {
                        story.copy(isLoaded = true)
                    } else {
                        story.copy(isLoaded = false)
                    }
                }
                _state.value = state.value.copy(
                    stories = stories
                )
            }

            is StoriesEvent.RightTap -> {
                val stories = _state.value.stories.mapIndexed { index, story ->
                    if (index == event.index) {
                        story.copy(progress = 1f)
                    } else if (index < event.index) {
                        story.copy(progress = 1f)
                    } else {
                        story.copy(progress = 0.00f)
                    }
                }
                _state.value = state.value.copy(
                    stories = stories
                )
            }

            is StoriesEvent.LeftTap -> {
                val stories = _state.value.stories.mapIndexed { index, story ->
                    if (index == event.index) {
                        story.copy(progress = 0.00f)
                    } else if (index == event.index - 1) {
                        story.copy(progress = 0.00f)
                    } else if (index < event.index) {
                        story.copy(progress = 1f)
                    } else {
                        story.copy(progress = 0.00f)
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
            stories = listOfImages.map { picture ->
                Story(id = UUID.randomUUID().toString(), picture = picture)
            }
        )
    }
}