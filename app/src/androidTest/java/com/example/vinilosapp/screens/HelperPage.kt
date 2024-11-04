package com.example.vinilosapp.screens

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import javax.inject.Inject

class HelperPage @Inject constructor() {

    fun hideKeyboard() {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressBack()
    }
}
