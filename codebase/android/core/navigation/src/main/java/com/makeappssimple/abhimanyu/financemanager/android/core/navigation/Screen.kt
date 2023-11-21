package com.makeappssimple.abhimanyu.financemanager.android.core.navigation

sealed class Screen(
    val route: String,
) {
    data object Accounts : Screen(
        route = "accounts",
    )

    data object AddAccount : Screen(
        route = "add_account",
    )

    data object AddCategory : Screen(
        route = "add_category",
    )

    data object AddTransaction : Screen(
        route = "add_transaction",
    )

    data object AddTransactionFor : Screen(
        route = "add_transaction_for",
    )

    data object Analysis : Screen(
        route = "analysis",
    )

    data object Categories : Screen(
        route = "categories",
    )

    data object EditAccount : Screen(
        route = "edit_account",
    )

    data object EditCategory : Screen(
        route = "edit_category",
    )

    data object EditTransaction : Screen(
        route = "edit_transaction",
    )

    data object EditTransactionFor : Screen(
        route = "edit_transaction_for",
    )

    data object Home : Screen(
        route = "home",
    )

    data object OpenSourceLicenses : Screen(
        route = "open_source_licenses",
    )

    data object Settings : Screen(
        route = "settings",
    )

    data object TransactionForValues : Screen(
        route = "transaction_for_values",
    )

    data object Transactions : Screen(
        route = "transactions",
    )

    data object ViewTransaction : Screen(
        route = "view_transaction",
    )
}
