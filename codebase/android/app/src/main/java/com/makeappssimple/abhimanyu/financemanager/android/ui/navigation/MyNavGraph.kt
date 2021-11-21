package com.makeappssimple.abhimanyu.financemanager.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.Screen
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.home.HomeScreen
import com.makeappssimple.abhimanyu.financemanager.android.utils.logError

@Composable
fun MyNavGraph(
    navHostController: NavHostController,
) {
    logError("Inside MyNavGraph")

    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route,
    ) {
        composable(
            route = Screen.Home.route,
        ) {
            HomeScreen()
        }
    }
}
