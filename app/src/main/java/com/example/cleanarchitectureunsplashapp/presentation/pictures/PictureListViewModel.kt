package com.example.cleanarchitectureunsplashapp.presentation.pictures

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitectureunsplashapp.core.Resource
import com.example.cleanarchitectureunsplashapp.domain.use_case.DeletePictures.DeletePicturesUseCase
import com.example.cleanarchitectureunsplashapp.domain.use_case.getPictures.GetPicturesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PictureListViewModel @Inject constructor(
    private val getPicturesUseCase: GetPicturesUseCase,
    private val deletePicturesUseCase: DeletePicturesUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(PictureListState())
    val state: State<PictureListState> = _state
    private var searchJob: Job? = null

    init {
        getPictures(
            query = "", fetchFromRemote = true
        )
    }

    fun onEvent(event: PictureListEvent) {
        when (event) {

            is PictureListEvent.Refresh -> {
                getPictures(query = "", fetchFromRemote = true)
            }

            is PictureListEvent.OnSearchQueryChange -> {
                _state.value = state.value.copy(
                    searchQuery = event.query
                )
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getPictures(
                        query = state.value.searchQuery, fetchFromRemote = false
                    )
                }
            }

            is PictureListEvent.DeletePicture -> {
                viewModelScope.launch {
                    deletePicturesUseCase.invoke(event.picture)
                    getPictures(query = event.query, fetchFromRemote = false)
                }
            }

            is PictureListEvent.LikePicture -> {
                val pictures = _state.value.pictures.map { picture ->
                    if (picture.id == event.id) {
                        picture.copy(isLiked = !picture.isLiked)
                    } else {
                        picture
                    }
                }
                _state.value = state.value.copy(
                    pictures = pictures
                )
            }
        }
    }

    private fun getPictures(query: String, fetchFromRemote: Boolean) {
        viewModelScope.launch {
            getPicturesUseCase.invoke(query, fetchFromRemote).collect { result ->
                when (result) {
                    is Resource.Success -> {

                        Log.e("dataPictures", "${result.data}")

                        _state.value = PictureListState(
                            pictures = result.data ?: emptyList(),
                        )

                    }
                    is Resource.Error -> _state.value = PictureListState(
                        error = result.message ?: "An unexpected error occur ",
                    )

                    is Resource.Loading -> {

                        Log.e("dataPictures", "${result.data}")

                        _state.value = PictureListState(
                            isLoading = true,
                        )
                    }
                }
            }
        }
    }
}