package com.example.cleanarchitectureunsplashapp.presentation.pictures

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitectureunsplashapp.core.Resource
import com.example.cleanarchitectureunsplashapp.domain.use_case.getPictures.GetPicturesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PictureListViewModel @Inject constructor(
    private val getPicturesUseCase: GetPicturesUseCase
) : ViewModel() {

    private val _state = mutableStateOf(PictureListState())
    val state: State<PictureListState> = _state

    init {
        getPictures()
    }

    private fun getPictures() {
        getPicturesUseCase.invoke().onEach { result ->
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