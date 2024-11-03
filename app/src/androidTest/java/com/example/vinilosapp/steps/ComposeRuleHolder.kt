package com.example.vinilosapp.steps

import android.util.Log
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.vinilosapp.MainActivity
import io.cucumber.junit.WithJunitRule
import org.junit.Rule
import javax.inject.Inject
import javax.inject.Singleton

@WithJunitRule
@Singleton
class ComposeRuleHolder @Inject constructor() {

    @get:Rule(order = 1)
    val composeRule: AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity> =
        createAndroidComposeRule(MainActivity::class.java).also {
            Log.d("ComposeRuleHolder", "ComposeTestRule initialized")
        }
}
