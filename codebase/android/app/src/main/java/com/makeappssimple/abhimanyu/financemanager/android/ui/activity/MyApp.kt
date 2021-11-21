package com.makeappssimple.abhimanyu.financemanager.android.ui.activity

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MyApp() {
    val navHostController = rememberNavController()

    MyAppView(
        navHostController = navHostController,
    )
}
