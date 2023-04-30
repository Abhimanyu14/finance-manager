package com.makeappssimple.abhimanyu.financemanager.android.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.DEEPLINK_BASE_URL
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.DEEPLINK_BROWSER_BASE_URL
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Screen
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen.HomeScreen

fun NavGraphBuilder.homeNavGraph() {
    composable(
        route = Screen.Home.route,
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "$DEEPLINK_BROWSER_BASE_URL/${Screen.Home.route}"
            },
            navDeepLink {
                uriPattern = "$DEEPLINK_BASE_URL/${Screen.Home.route}"
            },
        ),
    ) {
        HomeScreen()
    }
}
