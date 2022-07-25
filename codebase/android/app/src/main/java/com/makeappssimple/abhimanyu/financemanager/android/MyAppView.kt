package com.makeappssimple.abhimanyu.financemanager.android

import androidx.compose.runtime.Composable
import com.google.accompanist.insets.ProvideWindowInsets
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyAppTheme

@Composable
internal fun MyAppView(
    activityViewModel: MainActivityViewModel,
) {
    ProvideWindowInsets {
        MyAppTheme {
            MyNavGraph(
                activityViewModel = activityViewModel,
            )
        }
    }
}
