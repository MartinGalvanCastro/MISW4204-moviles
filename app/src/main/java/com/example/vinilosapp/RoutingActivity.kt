package com.example.vinilosapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class RoutingActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        startSomeNextActivity()
        finish()
    }

    private fun startSomeNextActivity() {
        val intent = Intent(this, MainActivity::class.java) // Route to MainActivity
        startActivity(intent)
    }
}
