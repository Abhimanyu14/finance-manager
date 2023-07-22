package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.DeeplinkUrl
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavArgs
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Screen
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.accounts.screen.AccountsScreen
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.screen.AddAccountScreen
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.screen.EditAccountScreen

fun NavGraphBuilder.accountsNavGraph() {
    composable(
        route = Screen.AddAccount.route,
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "${DeeplinkUrl.BROWSER_BASE_URL}/${Screen.AddAccount.route}"
            },
            navDeepLink {
                uriPattern = "${DeeplinkUrl.BASE_URL}/${Screen.AddAccount.route}"
            },
        ),
    ) {
        AddAccountScreen()
    }

    composable(
        route = "${Screen.EditAccount.route}/{${NavArgs.ACCOUNT_ID}}",
        arguments = listOf(
            navArgument(NavArgs.ACCOUNT_ID) {
                type = NavType.IntType
            },
        ),
    ) {
        EditAccountScreen()
    }

    composable(
        route = Screen.Accounts.route,
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "${DeeplinkUrl.BROWSER_BASE_URL}/${Screen.Accounts.route}"
            },
            navDeepLink {
                uriPattern = "${DeeplinkUrl.BASE_URL}/${Screen.Accounts.route}"
            },
        ),
    ) {
        AccountsScreen()
    }
}
