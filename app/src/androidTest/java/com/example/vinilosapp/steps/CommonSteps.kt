package com.example.vinilosapp.steps

import android.util.Log
import dagger.hilt.android.testing.HiltAndroidTest
import io.cucumber.java.After
import io.cucumber.java.Before
import javax.inject.Inject

@HiltAndroidTest
class CommonSteps @Inject constructor(
    private val composeRuleHolder: ComposeRuleHolder,
) {

    @Before(order = 1)
    fun setupComposeTestRule() {
        Log.d("CommonSteps", "Setting up ComposeTestRule")
        composeRuleHolder.composeRule.waitForIdle()
    }

    @After(order = 1)
    fun teardownComposeTestRule() {
        Log.d("CommonSteps", "Tearing down ComposeTestRule")
        // Any necessary cleanup logic here
    }
}
