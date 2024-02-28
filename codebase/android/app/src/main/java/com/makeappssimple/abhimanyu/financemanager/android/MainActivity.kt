package com.makeappssimple.abhimanyu.financemanager.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
 * 15. Groovy to Kotlin
 * 16. Proguard/R8
 * 17. Minify resources
 * 18. Feedback
 * 19. Public Issue Tracker
 *
 * Open Bugs
 * 1. Navigation - Enter Amount to Home and clicking on FAB opens Add Account screen
 *
 * Libs
 * 1. Emoji Picker
 * 2. Compose UI Testing
 * 3. Preference Explorer
 * 4. DB Explorer
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
            // TODO(Abhi): Lint Test
            MaterialTheme {

            }

            CompositionLocalProvider(LocalMyLogger provides myLogger) {
                MyApp()
            }
        }
    }
}

// TODO(Abhi): Lint Test
private class LintTest {
    // We have a custom lint check bundled with :library
    // that this module depends on. The lint check looks
    // for mentions of "lint", which should trigger an
    // error
    val s = "lint"
    fun lint() {}
}
