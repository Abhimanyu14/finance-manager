package com.makeappssimple.abhimanyu.financemanager.android

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowInsetsControllerCompat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?,
    ) {
        super.onCreate(savedInstanceState)

        setDarkStatusBarIcons()
        setContent {
            MyApp()
        }
    }
}

/**
 * Source: https://stackoverflow.com/a/71036723/9636037
 */
fun Activity.setDarkStatusBarIcons() {
    window?.let { window ->
        WindowInsetsControllerCompat(window, window.decorView)
    }?.apply {
        isAppearanceLightStatusBars = true
    }
}
