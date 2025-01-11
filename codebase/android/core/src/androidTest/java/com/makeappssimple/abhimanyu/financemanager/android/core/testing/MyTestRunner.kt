package com.makeappssimple.abhimanyu.financemanager.android.core.testing

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

// A custom runner to set up the instrumented application class for tests.
public class MyTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        classLoader: ClassLoader?,
        name: String?,
        context: Context?,
    ): Application {
        return super.newApplication(
            classLoader,
            HiltTestApplication::class.java.name,
            context,
        )
    }
}
