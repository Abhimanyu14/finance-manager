package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.makeappssimple.abhimanyu.financemanager.android.cre.navigation.DeeplinkUrl
import com.makeappssimple.abhimanyu.financemanager.android.cre.navigation.Screen
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.screen.AnalysisScreen

public fun NavGraphBuilder.analysisNavGraph() {
    composable(
        route = Screen.Analysis.route,
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "${DeeplinkUrl.BROWSER_BASE_URL}/${Screen.Analysis.route}"
            },
            navDeepLink {
                uriPattern = "${DeeplinkUrl.BASE_URL}/${Screen.Analysis.route}"
            },
        ),
    ) {
        AnalysisScreen()
    }
}
