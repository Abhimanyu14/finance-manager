package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.DEEPLINK_BASE_URL
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.DEEPLINK_BROWSER_BASE_URL
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Screen
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.screen.SettingsScreen

fun NavGraphBuilder.settingsNavGraph() {
    composable(
        route = Screen.Settings.route,
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "$DEEPLINK_BROWSER_BASE_URL/${Screen.Settings.route}"
            },
            navDeepLink {
                uriPattern = "$DEEPLINK_BASE_URL/${Screen.Settings.route}"
            },
        ),
    ) {
        SettingsScreen()
    }
}
