package com.example.cleanarchitectureunsplashapp.domain.use_case.getPictures

import com.example.cleanarchitectureunsplashapp.core.Resource
import com.example.cleanarchitectureunsplashapp.domain.model.Picture
import com.example.cleanarchitectureunsplashapp.domain.repository.PictureRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPicturesUseCase @Inject constructor(
    private val repository: PictureRepository
) {
     operator fun invoke(
        query: String,
        fetchFromRemote: Boolean
    ): Flow<Resource<List<Picture>>> {
        return repository.getPictures(query, fetchFromRemote)
    }
}
