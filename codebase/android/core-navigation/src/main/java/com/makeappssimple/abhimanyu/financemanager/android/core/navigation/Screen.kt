package com.makeappssimple.abhimanyu.financemanager.android.core.navigation

const val DEEPLINK_BROWSER_BASE_URL = "https://www.makeappssimple.financemanager.com"
const val DEEPLINK_BASE_URL = "makeappssimple://financemanager"

sealed class Screen(
    val route: String,
) {
    // App specific
    object AddCategory : Screen(
        route = "add_category",
    )

    object AddSource : Screen(
        route = "add_source",
    )

    object AddTransaction : Screen(
        route = "add_transaction",
    )

    object AddTransactionFor : Screen(
        route = "add_transaction_for",
    )

    object Categories : Screen(
        route = "categories",
    )

    object EditCategory : Screen(
        route = "edit_category",
    )

    object EditSource : Screen(
        route = "edit_source",
    )

    object EditTransaction : Screen(
        route = "edit_transaction",
    )

    object EditTransactionFor : Screen(
        route = "edit_transaction_for",
    )

    object Home : Screen(
        route = "home",
    )

    object Settings : Screen(
        route = "settings",
    )

    object Sources : Screen(
        route = "sources",
    )

    object TransactionForValues : Screen(
        route = "transaction_for_values",
    )

    object Transactions : Screen(
        route = "transactions",
    )

    object ViewTransaction : Screen(
        route = "view_transaction",
    )
}
