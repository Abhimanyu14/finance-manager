package com.makeappssimple.abhimanyu.financemanager.android.ui.activity

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.insets.ProvideWindowInsets
import com.makeappssimple.abhimanyu.financemanager.android.ui.navigation.MyNavGraph
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Material2AppTheme
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.MyAppTheme

@ExperimentalMaterial3Api
@Composable
fun MyAppView(
    navHostController: NavHostController,
) {
    ProvideWindowInsets {
        MyAppTheme {
            Material2AppTheme {
                MyNavGraph(
                    navHostController = navHostController,
                )
            }
        }
    }
}
