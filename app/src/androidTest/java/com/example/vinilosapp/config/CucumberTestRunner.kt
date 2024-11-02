package com.example.vinilosapp.config

import io.cucumber.android.runner.CucumberAndroidJUnitRunner
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/androidTest/resources/features"], // Path to feature files
    glue = ["com.example.vinilosapp.stepDefinitions"], // Package containing step definitions
    plugin = ["pretty", "summary"],
)
class CucumberTestRunner : CucumberAndroidJUnitRunner()
