package com.example.vinilosapp.services

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.openapitools.client.infrastructure.BigDecimalAdapter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

val logging = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(BigDecimalAdapter())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://54.242.71.56:3000/")
            .addConverterFactory(MoshiConverterFactory.create(moshi)) // Use MoshiConverterFactory
            .client(OkHttpClient.Builder().addInterceptor(logging).build())
            .build()
    }

    @Provides
    @Singleton
    fun provideAlbumService(retrofit: Retrofit): AlbumService {
        return retrofit.create(AlbumService::class.java)
    }
}
