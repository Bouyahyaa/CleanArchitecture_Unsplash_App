package com.example.cleanarchitectureunsplashapp.di

import android.app.Application
import androidx.room.Room
import com.example.cleanarchitectureunsplashapp.core.Constants
import com.example.cleanarchitectureunsplashapp.data.local.UnsplashDatabase
import com.example.cleanarchitectureunsplashapp.data.remote.PictureApi
import com.example.cleanarchitectureunsplashapp.data.repository.PictureRepositoryImpl
import com.example.cleanarchitectureunsplashapp.domain.repository.PictureRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePictureDatabase(app: Application): UnsplashDatabase {
        return Room.databaseBuilder(
            app, UnsplashDatabase::class.java, UnsplashDatabase.DATABASE_NAME
        ).build()
    }

//    @Provides
//    @Singleton
//    fun provideNoteRepository(db: UnsplashDatabase): PictureRepository {
//        return PictureRepositoryImpl(db.unsplashDao)
//    }

    @Provides
    @Singleton
    fun providePictureApi(): PictureApi {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(PictureApi::class.java)
    }

    @Provides
    @Singleton
    fun providePictureRepository(api: PictureApi): PictureRepository {
        return PictureRepositoryImpl(api)
    }
}