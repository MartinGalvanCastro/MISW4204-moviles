package com.example.vinilosapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.airbnb.mvrx.Mavericks
import com.example.vinilosapp.navigation.AppNavigation
import com.example.vinilosapp.ui.theme.VinilosAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mavericks.initialize(this)
        enableEdgeToEdge()
        setContent {
            VinilosAppTheme {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }
}
