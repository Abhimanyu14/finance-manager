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

/**
 * TODO(Abhi)
 *
 * 1. Immutable collection
 * 2. Analytics
 * 3. Android ViewModel to ViewModel
 * 4. KMP
 * 5. CMP
 * 6. Throttle all clicks
 * 7. Process death
 * 8. Landscape and Tablet
 * 9. Dark mode
 * 10. Unit testing
 * 11. UI Testing
 * 12. Screenshot testing
 * 13. Linting
 * 14. Architecture structure rules
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var myLogger: MyLogger

    override fun onCreate(
        savedInstanceState: Bundle?,
    ) {
        super.onCreate(savedInstanceState)

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations
        enableEdgeToEdge()

        setContent {
            CompositionLocalProvider(LocalMyLogger provides myLogger) {
                MyApp()
            }
        }
    }
}
