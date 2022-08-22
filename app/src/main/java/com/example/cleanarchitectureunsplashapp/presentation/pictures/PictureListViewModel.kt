package com.example.cleanarchitectureunsplashapp.presentation.pictures

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
    private val deletePicturesUseCase: DeletePicturesUseCase
) : ViewModel() {

    private val _state = mutableStateOf(PictureListState())
    val state: State<PictureListState> = _state
    private var searchJob: Job? = null
    private var getJob: Job? = null

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
                        query = state.value.searchQuery,
                        fetchFromRemote = false
                    )
                }
            }

            is PictureListEvent.DeletePicture -> {
                viewModelScope.launch {
                    deletePicturesUseCase.invoke(event.picture)
                }
            }
        }
    }

    private fun getPictures(query: String, fetchFromRemote: Boolean) {
        viewModelScope.launch {
            getPicturesUseCase
                .invoke(query, fetchFromRemote)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {

                            _state.value = state.value.copy(
                                isLoading = false,
                                pictures = result.data ?: emptyList(),
                                error = "",
                                searchQuery = "",
                                isRefreshing = false,
                            )

                        }
                        is Resource.Error ->
                            _state.value = state.value.copy(
                                isLoading = false,
                                pictures = emptyList(),
                                error = result.message ?: "An unexpected error occur ",
                                searchQuery = "",
                                isRefreshing = false,
                            )

                        is Resource.Loading -> {
                            _state.value = state.value.copy(
                                isLoading = result.isLoading,
                                pictures = emptyList(),
                                error = "",
                                searchQuery = "",
                                isRefreshing = false,
                            )
                        }
                    }
                }
        }
    }
}