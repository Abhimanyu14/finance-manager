package com.makeappssimple.abhimanyu.financemanager.android.navigation.utils

import com.makeappssimple.abhimanyu.financemanager.android.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager

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


fun navigateToAddCategoryScreen(
    navigationManager: NavigationManager,
) {
    navigationManager.navigate(
        direction = MyNavigationDirections.addCategory()
    )
}

fun navigateToAddSourceScreen(
    navigationManager: NavigationManager,
) {
    navigationManager.navigate(
        direction = MyNavigationDirections.addSource()
    )
}

fun navigateToAddTransactionScreen(
    navigationManager: NavigationManager,
) {
    navigationManager.navigate(
        direction = MyNavigationDirections.addTransaction()
    )
}

fun navigateToBalanceDetailsScreen(
    navigationManager: NavigationManager,
) {
    navigationManager.navigate(
        direction = MyNavigationDirections.balanceDetails()
    )
}

fun navigateToCategoriesScreen(
    navigationManager: NavigationManager,
) {
    navigationManager.navigate(
        direction = MyNavigationDirections.categories()
    )
}

fun navigateToCategoryDetailsScreen(
    navigationManager: NavigationManager,
) {
    navigationManager.navigate(
        direction = MyNavigationDirections.categoryDetails()
    )
}

fun navigateToHomeScreen(
    navigationManager: NavigationManager,
) {
    navigationManager.navigate(
        direction = MyNavigationDirections.home()
    )
}

fun navigateToSettingsScreen(
    navigationManager: NavigationManager,
) {
    navigationManager.navigate(
        direction = MyNavigationDirections.settings()
    )
}

fun navigateToSourceDetailsScreen(
    navigationManager: NavigationManager,
) {
    navigationManager.navigate(
        direction = MyNavigationDirections.sourceDetails()
    )
}

fun navigateToSourcesScreen(
    navigationManager: NavigationManager,
) {
    navigationManager.navigate(
        direction = MyNavigationDirections.sources()
    )
}
