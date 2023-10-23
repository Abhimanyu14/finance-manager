package com.makeappssimple.abhimanyu.financemanager.android.core.navigation

sealed class Screen(
    val route: String,
) {
    object Accounts : Screen(
        route = "accounts",
    )

    object AddAccount : Screen(
        route = "add_account",
    )

    object AddCategory : Screen(
        route = "add_category",
    )

    object AddTransaction : Screen(
        route = "add_transaction",
    )

    object AddTransactionFor : Screen(
        route = "add_transaction_for",
    )

    object Analysis : Screen(
        route = "analysis",
    )

    object Categories : Screen(
        route = "categories",
    )

    object EditAccount : Screen(
        route = "edit_account",
    )

    object EditCategory : Screen(
        route = "edit_category",
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

    object OpenSourceLicenses : Screen(
        route = "open_source_licenses",
    )

    object Settings : Screen(
        route = "settings",
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
