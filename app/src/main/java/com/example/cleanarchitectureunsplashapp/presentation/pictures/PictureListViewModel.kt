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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
                    getPictures(event.query, false)
                }
            }
        }
    }

    private fun getPictures(query: String, fetchFromRemote: Boolean) {
        getPicturesUseCase.invoke(query, fetchFromRemote).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = PictureListState(pictures = result.data ?: emptyList())
                }

                is Resource.Error -> {

                    Log.e("messageError", "${result.message}")

                    _state.value = PictureListState(
                        error = result.message ?: "An unexpected error occur "
                    )
                }

                is Resource.Loading -> {
                    _state.value = PictureListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}