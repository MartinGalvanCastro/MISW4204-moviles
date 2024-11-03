package com.example.vinilosapp.config;

import android.app.Application;
import android.content.Context;

import dagger.hilt.android.testing.HiltTestApplication;
import io.cucumber.android.runner.CucumberAndroidJUnitRunner;
import io.cucumber.junit.CucumberOptions;


@CucumberOptions(
        features = "features"
)
public class VinilosAndroidJUnitRunner extends CucumberAndroidJUnitRunner {

    @Override
    public Application newApplication(ClassLoader cl, String className, Context context) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return super.newApplication(cl, HiltTestApplication.class.getName(), context);
    }
}