package com.makeappssimple.abhimanyu.financemanager.android.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.DeeplinkUrl
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Screen
import com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen.HomeScreen

public fun NavGraphBuilder.homeNavGraph() {
    composable(
        route = Screen.Home.route,
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "${DeeplinkUrl.BROWSER_BASE_URL}/${Screen.Home.route}"
            },
            navDeepLink {
                uriPattern = "${DeeplinkUrl.BASE_URL}/${Screen.Home.route}"
            },
        ),
    ) {
        HomeScreen()
    }
}
