package com.makeappssimple.abhimanyu.financemanager.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?,
    ) {
        super.onCreate(savedInstanceState)

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations
        // TODO(Abhi): Change to edgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            MyApp()
        }
    }
}
