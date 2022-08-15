package com.example.cleanarchitectureunsplashapp.domain.use_case.getPictures

import com.example.cleanarchitectureunsplashapp.core.Resource
import com.example.cleanarchitectureunsplashapp.data.mapper.toPicture
import com.example.cleanarchitectureunsplashapp.data.mapper.toPictureEntity
import com.example.cleanarchitectureunsplashapp.domain.model.Picture
import com.example.cleanarchitectureunsplashapp.domain.repository.PictureRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPicturesUseCase @Inject constructor(
    private val repository: PictureRepository
) {
    operator fun invoke(): Flow<Resource<List<Picture>>> = flow {
        try {
            emit(Resource.Loading<List<Picture>>())
            val localPictures = repository.getPictures().map {
                it.toPictureEntity().toPicture()
            }
            emit(Resource.Success<List<Picture>>(localPictures))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Picture>>(e.localizedMessage ?: "An unexpected error occur"))
        } catch (e: IOException) {
            emit(Resource.Error<List<Picture>>("Couldn't reach server . Check your internet connection"))
        }
    }
}