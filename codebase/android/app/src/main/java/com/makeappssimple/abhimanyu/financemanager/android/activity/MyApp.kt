package com.makeappssimple.abhimanyu.financemanager.android.activity

import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyAppTheme
import com.makeappssimple.abhimanyu.financemanager.android.navigation.MyNavGraph

@Composable
internal fun MyApp(
    navHostController: NavHostController = rememberNavController(),
) {
    MyAppTheme {
        CompositionLocalProvider(
            // To remove overscroll effect globally
            LocalOverscrollConfiguration provides null,
        ) {
            MyNavGraph(
                navHostController = navHostController,
            )
        }
    }
}
