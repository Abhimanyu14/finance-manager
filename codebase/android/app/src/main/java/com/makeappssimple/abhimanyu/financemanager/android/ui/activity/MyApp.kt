package com.makeappssimple.abhimanyu.financemanager.android.ui.activity

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

@ExperimentalMaterial3Api
@Composable
fun MyApp() {
    val navHostController = rememberNavController()

    MyAppView(
        navHostController = navHostController,
    )
}
