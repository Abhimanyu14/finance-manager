package com.makeappssimple.abhimanyu.financemanager.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
public class MainActivity : ComponentActivity() {

    @Inject
    public lateinit var myLogger: MyLogger

    override fun onCreate(
        savedInstanceState: Bundle?,
    ) {
        super.onCreate(savedInstanceState)

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations
        enableEdgeToEdge()

        setContent {
            CompositionLocalProvider(
                value = LocalMyLogger provides myLogger,
            ) {
                MyApp()
            }
        }
    }
}
