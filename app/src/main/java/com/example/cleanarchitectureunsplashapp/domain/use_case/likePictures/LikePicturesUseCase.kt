package com.example.cleanarchitectureunsplashapp.domain.use_case.likePictures

import com.example.cleanarchitectureunsplashapp.domain.model.Picture
import com.example.cleanarchitectureunsplashapp.domain.repository.PictureRepository
import javax.inject.Inject

class LikePicturesUseCase @Inject constructor(
    private val repository: PictureRepository,
) {
    suspend operator fun invoke(picture: Picture) {
        repository.likePictures(picture = picture)
    }
}