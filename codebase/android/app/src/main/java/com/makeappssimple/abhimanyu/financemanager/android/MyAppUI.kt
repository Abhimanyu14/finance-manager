package com.makeappssimple.abhimanyu.financemanager.android

import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyAppTheme

@Composable
internal fun MyAppUI(
    activityViewModel: MainActivityViewModel,
) {
    MyAppTheme {
        // To remove overscroll effect globally
        CompositionLocalProvider(
            value = LocalOverscrollConfiguration provides null,
        ) {
            MyNavGraph(
                activityViewModel = activityViewModel,
            )
        }
    }
}
