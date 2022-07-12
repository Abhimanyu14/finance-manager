package com.makeappssimple.abhimanyu.financemanager.android.navigation

const val DEEPLINK_BASE_URL = "https://www.makeappssimple.financemanager.com"

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

    object BalanceDetails : Screen(
        route = "balance_details",
    )

    object Categories : Screen(
        route = "categories",
    )

    object CategoryDetails : Screen(
        route = "category_details",
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

    object Home : Screen(
        route = "home",
    )

    object Settings : Screen(
        route = "settings",
    )

    object Sources : Screen(
        route = "sources",
    )

    object Transactions : Screen(
        route = "transactions",
    )
}
