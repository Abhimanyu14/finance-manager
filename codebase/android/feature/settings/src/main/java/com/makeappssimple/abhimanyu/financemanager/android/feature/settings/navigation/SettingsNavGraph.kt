package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.DeeplinkUrl
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Screen
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.screen.OpenSourceLicensesScreen
import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen.SettingsScreen

public fun NavGraphBuilder.settingsNavGraph() {
    composable(
        route = Screen.OpenSourceLicenses.route,
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "${DeeplinkUrl.BROWSER_BASE_URL}/${Screen.OpenSourceLicenses.route}"
            },
            navDeepLink {
                uriPattern = "${DeeplinkUrl.BASE_URL}/${Screen.OpenSourceLicenses.route}"
            },
        ),
    ) {
        OpenSourceLicensesScreen()
    }

    composable(
        route = Screen.Settings.route,
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "${DeeplinkUrl.BROWSER_BASE_URL}/${Screen.Settings.route}"
            },
            navDeepLink {
                uriPattern = "${DeeplinkUrl.BASE_URL}/${Screen.Settings.route}"
            },
        ),
    ) {
        SettingsScreen()
    }
}
