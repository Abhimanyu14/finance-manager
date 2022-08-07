package com.makeappssimple.abhimanyu.financemanager.android

import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyAppTheme

@Composable
internal fun MyAppView(
    activityViewModel: MainActivityViewModel,
) {
    MyAppTheme {
        MyNavGraph(
            activityViewModel = activityViewModel,
        )
    }
}
