package com.makeappssimple.abhimanyu.financemanager.android.ui.activity

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.google.accompanist.insets.ProvideWindowInsets
import com.makeappssimple.abhimanyu.financemanager.android.navigation.MyNavGraph
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Material2AppTheme
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.MyAppTheme

@ExperimentalMaterialApi
@ExperimentalMaterial3Api
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
