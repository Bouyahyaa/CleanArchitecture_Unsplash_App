package com.example.cleanarchitectureunsplashapp.domain.use_case.SeePictures

import com.example.cleanarchitectureunsplashapp.domain.model.Picture
import com.example.cleanarchitectureunsplashapp.domain.repository.PictureRepository
import javax.inject.Inject

class SeePicturesUseCase @Inject constructor(
    private val repository: PictureRepository,
) {
    suspend operator fun invoke(picture: Picture) {
        repository.seePictures(picture = picture)
    }
}