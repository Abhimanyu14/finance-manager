package com.makeappssimple.abhimanyu.financemanager.android.navigation

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

    object Home : Screen(
        route = "home",
    )

    object SourceDetails : Screen(
        route = "source_details",
    )

    object Sources : Screen(
        route = "sources",
    )
}
