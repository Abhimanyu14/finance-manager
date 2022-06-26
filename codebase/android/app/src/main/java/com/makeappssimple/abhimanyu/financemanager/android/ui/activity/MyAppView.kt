package com.makeappssimple.abhimanyu.financemanager.android.ui.activity

import androidx.compose.runtime.Composable
import com.google.accompanist.insets.ProvideWindowInsets
import com.makeappssimple.abhimanyu.financemanager.android.navigation.MyNavGraph
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Material2AppTheme
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.MyAppTheme

@Composable
fun MyAppView(
    activityViewModel: MainActivityViewModel,
) {
    ProvideWindowInsets {
        MyAppTheme {
            Material2AppTheme {
                MyNavGraph(
                    activityViewModel = activityViewModel,
                )
            }
        }
    }
}
