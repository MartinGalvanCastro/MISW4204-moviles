package com.example.vinilosapp.di

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import com.example.vinilosapp.config.TrustFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import javax.inject.Singleton

class RequestHeaderInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Cache-Control", "no-cache")
            .build()
        return chain.proceed(request)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideContext(application: android.app.Application): Context {
        return application
    }

    @Provides
    fun provideConnectivityManager(context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        context: Context,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            val (socketFactory, trustManager) = TrustFactory.getTrustFactoryManager(context)
            builder.sslSocketFactory(socketFactory, trustManager)
        }

        builder.addNetworkInterceptor(RequestHeaderInterceptor())

        return builder.build()
    }
}
