package com.makeappssimple.abhimanyu.financemanager.android.core.navigation

public sealed class Screen(
    public val route: String,
) {
    public data object Accounts : Screen(
        route = "accounts",
    )

    public data object AddAccount : Screen(
        route = "add_account",
    )

    public data object AddCategory : Screen(
        route = "add_category",
    )

    public data object AddTransaction : Screen(
        route = "add_transaction",
    )

    public data object AddTransactionFor : Screen(
        route = "add_transaction_for",
    )

    public data object Analysis : Screen(
        route = "analysis",
    )

    public data object Categories : Screen(
        route = "categories",
    )

    public data object EditAccount : Screen(
        route = "edit_account",
    )

    public data object EditCategory : Screen(
        route = "edit_category",
    )

    public data object EditTransaction : Screen(
        route = "edit_transaction",
    )

    public data object EditTransactionFor : Screen(
        route = "edit_transaction_for",
    )

    public data object Home : Screen(
        route = "home",
    )

    public data object OpenSourceLicenses : Screen(
        route = "open_source_licenses",
    )

    public data object Settings : Screen(
        route = "settings",
    )

    public data object TransactionForValues : Screen(
        route = "transaction_for_values",
    )

    public data object Transactions : Screen(
        route = "transactions",
    )

    public data object ViewTransaction : Screen(
        route = "view_transaction",
    )
}
