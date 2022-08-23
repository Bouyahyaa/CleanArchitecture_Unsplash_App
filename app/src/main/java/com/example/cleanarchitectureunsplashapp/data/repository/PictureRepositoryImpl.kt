package com.example.cleanarchitectureunsplashapp.data.repository

import android.util.Log
import com.example.cleanarchitectureunsplashapp.core.Resource
import com.example.cleanarchitectureunsplashapp.data.local.PictureLocalSource
import com.example.cleanarchitectureunsplashapp.data.mapper.toPicture
import com.example.cleanarchitectureunsplashapp.data.mapper.toPictureEntity
import com.example.cleanarchitectureunsplashapp.data.remote.PictureRemoteSource
import com.example.cleanarchitectureunsplashapp.data.remote.dto.PictureDto
import com.example.cleanarchitectureunsplashapp.domain.model.Picture
import com.example.cleanarchitectureunsplashapp.domain.repository.PictureRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PictureRepositoryImpl @Inject constructor(
    private val pictureLocalSource: PictureLocalSource,
    private val pictureRemoteSource: PictureRemoteSource
) : PictureRepository {

    override fun getPictures(
        query: String,
        fetchFromRemote: Boolean
    ): Flow<Resource<List<Picture>>> = flow {
        if (fetchFromRemote) {
            emit(Resource.Loading<List<Picture>>())
        }
        val localPictures = pictureLocalSource.getPictures(query)
        var remotePictures: List<PictureDto> = emptyList()

        val isDbEmpty = localPictures.isEmpty() && query.isBlank()
        val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
        Log.e("shouldJustLoadFromCache", "${localPictures.size}")
        Log.e("shouldJustLoadFromCache", query)
        Log.e("shouldJustLoadFromCache", "$fetchFromRemote")
        Log.e("shouldJustLoadFromCache", "$shouldJustLoadFromCache")

        if (!shouldJustLoadFromCache) {
            try {
                remotePictures = pictureRemoteSource.getPictures()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error<List<Picture>>("Couldn't load data"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error<List<Picture>>("Couldn't load data"))
            }

            remotePictures.let { pictures ->
                pictureLocalSource.clearPictures()
                pictures.map {
                    pictureLocalSource.insertPicture(it.toPictureEntity())
                }
            }
        }

        emit(
            Resource.Success<List<Picture>>(
                data = pictureLocalSource.getPictures(query).map {
                    it.toPicture()
                }
            )
        )

    }

    override suspend fun deletePictures(picture: Picture) {
        val pictureEntity = picture.toPictureEntity()
        pictureLocalSource.deletePicture(pictureEntity)
    }

}