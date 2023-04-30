package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.DEEPLINK_BASE_URL
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.DEEPLINK_BROWSER_BASE_URL
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavArgs
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Screen
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.screen.AddTransactionForScreen
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.screen.EditTransactionForScreen
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen.TransactionForValuesScreen

fun NavGraphBuilder.transactionForNavGraph() {
    composable(
        route = Screen.AddTransactionFor.route,
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "$DEEPLINK_BROWSER_BASE_URL/${Screen.AddTransactionFor.route}"
            },
            navDeepLink {
                uriPattern = "$DEEPLINK_BASE_URL/${Screen.AddTransactionFor.route}"
            },
        ),
    ) {
        AddTransactionForScreen()
    }

    composable(
        route = "${Screen.EditTransactionFor.route}/{${NavArgs.TRANSACTION_FOR_ID}}",
        arguments = listOf(
            navArgument(NavArgs.TRANSACTION_FOR_ID) {
                type = NavType.IntType
            },
        ),
    ) {
        EditTransactionForScreen()
    }

    composable(
        route = Screen.TransactionForValues.route,
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "$DEEPLINK_BROWSER_BASE_URL/${Screen.TransactionForValues.route}"
            },
            navDeepLink {
                uriPattern = "$DEEPLINK_BASE_URL/${Screen.TransactionForValues.route}"
            },
        ),
    ) {
        TransactionForValuesScreen()
    }
}
