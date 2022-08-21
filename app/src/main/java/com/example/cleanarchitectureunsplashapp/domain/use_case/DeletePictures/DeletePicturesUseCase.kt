package com.example.cleanarchitectureunsplashapp.domain.use_case.DeletePictures

import com.example.cleanarchitectureunsplashapp.domain.model.Picture
import com.example.cleanarchitectureunsplashapp.domain.repository.PictureRepository
import javax.inject.Inject

class DeletePicturesUseCase @Inject constructor(
    private val repository: PictureRepository
) {
    suspend operator fun invoke(picture: Picture) {
        repository.deletePictures(picture = picture)
    }
}