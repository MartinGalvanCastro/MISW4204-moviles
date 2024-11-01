package com.example.vinilosapp.services.adapters

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AdaptersModule {

    @Provides
    @Singleton
    fun provideAlbumServiceAdapter(
        albumServiceRetrofit: AlbumServiceRetrofit,
    ): AlbumServiceAdapter {
        return albumServiceRetrofit
    }
}
