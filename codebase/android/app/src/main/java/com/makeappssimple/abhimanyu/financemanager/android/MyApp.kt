package com.makeappssimple.abhimanyu.financemanager.android

import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyAppTheme
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.navigation.MyNavGraph

@Composable
internal fun MyApp(
    myLogger: MyLogger,
) {
    MyAppTheme {
        CompositionLocalProvider(
            LocalMyLogger provides myLogger,

            // To remove overscroll effect globally
            LocalOverscrollConfiguration provides null,
        ) {
            MyNavGraph()
        }
    }
}
