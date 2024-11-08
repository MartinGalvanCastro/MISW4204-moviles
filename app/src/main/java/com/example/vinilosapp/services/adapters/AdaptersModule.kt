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

    @Provides
    @Singleton
    fun provideBandServiceAdapter(
        bandServiceRetrofit: BandServiceRetrofit,
    ): BandServiceAdapter {
        return bandServiceRetrofit
    }

    @Provides
    @Singleton
    fun provideMusicianServiceAdapter(
        musicianServiceRetrofit: MusicianServiceRetrofit,
    ): MusicianServiceAdapter {
        return musicianServiceRetrofit
    }

    @Provides
    @Singleton
    fun providePrizeServiceAdapter(
        premioServiceRetrofit: PremioServiceRetrofit,
    ): PremioServiceAdapter {
        return premioServiceRetrofit
    }

    @Provides
    @Singleton
    fun provideCollecionistaServiceAdapter(
        collecionistaServiceRetrofit: ColecionistaServiceRetrofit,
    ): ColecionistaServiceAdapter {
        return collecionistaServiceRetrofit
    }
}
