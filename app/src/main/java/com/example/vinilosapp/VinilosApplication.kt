package com.example.vinilosapp

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import com.airbnb.mvrx.Mavericks
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class VinilosApplication : Application(), SingletonImageLoader.Factory {
    @Inject
    lateinit var imageLoader: ImageLoader

    override fun newImageLoader(context: PlatformContext): ImageLoader {
        return imageLoader
    }

    override fun onCreate() {
        super.onCreate()
        Mavericks.initialize(this)
    }
}
