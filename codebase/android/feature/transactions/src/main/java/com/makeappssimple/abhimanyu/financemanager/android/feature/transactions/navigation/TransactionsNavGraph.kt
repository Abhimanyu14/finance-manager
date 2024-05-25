package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.DeeplinkUrl
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavArgs
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Screen
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.screen.AddTransactionScreen
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.screen.EditTransactionScreen
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen.TransactionsScreen
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen.ViewTransactionScreen

public fun NavGraphBuilder.transactionsNavGraph() {
    composable(
        route = "${Screen.AddTransaction.route}/{${NavArgs.TRANSACTION_ID}}",
        arguments = listOf(
            navArgument(NavArgs.TRANSACTION_ID) {
                type = NavType.StringType
                nullable = true
            },
        ),
        deepLinks = listOf(
            navDeepLink {
                uriPattern =
                    "${DeeplinkUrl.BROWSER_BASE_URL}/${Screen.AddTransaction.route}/${NavArgs.TRANSACTION_ID}"
            },
            navDeepLink {
                uriPattern =
                    "${DeeplinkUrl.BASE_URL}/${Screen.AddTransaction.route}/${NavArgs.TRANSACTION_ID}"
            },
        ),
    ) {
        AddTransactionScreen()
    }

    composable(
        route = "${Screen.EditTransaction.route}/{${NavArgs.TRANSACTION_ID}}",
        arguments = listOf(
            navArgument(NavArgs.TRANSACTION_ID) {
                type = NavType.StringType
                nullable = true
            },
        ),
    ) {
        EditTransactionScreen()
    }

    composable(
        route = Screen.Transactions.route,
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "${DeeplinkUrl.BROWSER_BASE_URL}/${Screen.Transactions.route}"
            },
            navDeepLink {
                uriPattern = "${DeeplinkUrl.BASE_URL}/${Screen.Transactions.route}"
            },
        ),
    ) {
        TransactionsScreen()
    }

    // TODO(Abhi): View transaction can not have a nullable transaction id
    composable(
        route = "${Screen.ViewTransaction.route}/{${NavArgs.TRANSACTION_ID}}",
        arguments = listOf(
            navArgument(NavArgs.TRANSACTION_ID) {
                type = NavType.StringType
                nullable = true
            },
        ),
    ) {
        ViewTransactionScreen()
    }
}
