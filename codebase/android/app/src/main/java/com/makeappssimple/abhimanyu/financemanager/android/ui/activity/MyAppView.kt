package com.makeappssimple.abhimanyu.financemanager.android.ui.activity

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.makeappssimple.abhimanyu.financemanager.android.ui.navigation.MyNavGraph

@Composable
fun MyAppView(
    navHostController: NavHostController,
) {
    MyNavGraph(
        navHostController = navHostController,
    )
}
