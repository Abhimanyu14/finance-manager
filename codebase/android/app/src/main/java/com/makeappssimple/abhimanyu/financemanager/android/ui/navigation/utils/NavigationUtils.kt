package com.makeappssimple.abhimanyu.financemanager.android.ui.navigation.utils

import com.makeappssimple.abhimanyu.financemanager.android.ui.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.ui.navigation.NavigationManager

fun navigateUp(
    navigationManager: NavigationManager,
) {
    navigationManager.navigate(
        direction = MyNavigationDirections.navigateUp()
    )
}

fun clearBackstack(
    navigationManager: NavigationManager,
) {
    navigationManager.navigate(
        direction = MyNavigationDirections.clearBackstack()
    )
}

fun clearTillRoot(
    navigationManager: NavigationManager,
) {
    navigationManager.navigate(
        direction = MyNavigationDirections.clearTillRoot()
    )
}

fun navigateToHomeScreen(
    navigationManager: NavigationManager,
) {
    navigationManager.navigate(
        direction = MyNavigationDirections.home()
    )
}

fun navigateToAddTransactionScreen(
    navigationManager: NavigationManager,
) {
    navigationManager.navigate(
        direction = MyNavigationDirections.addTransaction()
    )
}
