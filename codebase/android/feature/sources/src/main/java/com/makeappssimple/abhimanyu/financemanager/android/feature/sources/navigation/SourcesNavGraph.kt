package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.DeeplinkUrl
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavArgs
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Screen
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.add_source.screen.AddSourceScreen
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.edit_source.screen.EditSourceScreen
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.screen.SourcesScreen

fun NavGraphBuilder.sourcesNavGraph() {
    composable(
        route = Screen.AddSource.route,
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "${DeeplinkUrl.BROWSER_BASE_URL}/${Screen.AddSource.route}"
            },
            navDeepLink {
                uriPattern = "${DeeplinkUrl.BASE_URL}/${Screen.AddSource.route}"
            },
        ),
    ) {
        AddSourceScreen()
    }

    composable(
        route = "${Screen.EditSource.route}/{${NavArgs.SOURCE_ID}}",
        arguments = listOf(
            navArgument(NavArgs.SOURCE_ID) {
                type = NavType.IntType
            },
        ),
    ) {
        EditSourceScreen()
    }

    composable(
        route = Screen.Sources.route,
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "${DeeplinkUrl.BROWSER_BASE_URL}/${Screen.Sources.route}"
            },
            navDeepLink {
                uriPattern = "${DeeplinkUrl.BASE_URL}/${Screen.Sources.route}"
            },
        ),
    ) {
        SourcesScreen()
    }
}
