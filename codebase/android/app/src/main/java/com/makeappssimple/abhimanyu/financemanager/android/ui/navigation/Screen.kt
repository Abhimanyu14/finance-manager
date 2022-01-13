package com.makeappssimple.abhimanyu.financemanager.android.ui.navigation

sealed class Screen(
    val route: String,
) {
    // App specific
    object Home : Screen(
        route = "home",
    )

    object AddTransaction : Screen(
        route = "add_transaction",
    )
}
