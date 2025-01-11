package com.makeappssimple.abhimanyu.financemanager.android.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LogKit
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
public class MainActivity : ComponentActivity() {
    @Inject
    public lateinit var logKit: LogKit

    override fun onCreate(
        savedInstanceState: Bundle?,
    ) {
        super.onCreate(savedInstanceState)

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations
        enableEdgeToEdge()

        setContent {
            MyApp(
                logKit = logKit,
            )
        }
    }
}
