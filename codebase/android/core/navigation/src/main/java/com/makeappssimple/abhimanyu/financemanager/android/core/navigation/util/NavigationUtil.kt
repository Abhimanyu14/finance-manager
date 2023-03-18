package com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util

import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager

fun navigateUp(
    navigationManager: NavigationManager,
) {
    navigationManager.navigate(
        navigationCommand = MyNavigationDirections.navigateUp()
    )
}

fun clearBackstack(
    navigationManager: NavigationManager,
) {
    navigationManager.navigate(
        navigationCommand = MyNavigationDirections.clearBackstack()
    )
}

fun clearTillRoot(
    navigationManager: NavigationManager,
) {
    navigationManager.navigate(
        navigationCommand = MyNavigationDirections.clearTillRoot()
    )
}


fun navigateToAddCategoryScreen(
    navigationManager: NavigationManager,
    transactionType: String,
) {
    navigationManager.navigate(
        navigationCommand = MyNavigationDirections.addCategory(
            transactionType = transactionType,
        )
    )
}

fun navigateToAddSourceScreen(
    navigationManager: NavigationManager,
) {
    navigationManager.navigate(
        navigationCommand = MyNavigationDirections.addSource()
    )
}

fun navigateToAddTransactionForScreen(
    navigationManager: NavigationManager,
) {
    navigationManager.navigate(
        navigationCommand = MyNavigationDirections.addTransactionFor()
    )
}

fun navigateToAddTransactionScreen(
    navigationManager: NavigationManager,
    transactionId: Int? = null,
) {
    navigationManager.navigate(
        navigationCommand = MyNavigationDirections.addTransaction(
            transactionId = transactionId,
        )
    )
}

fun navigateToCategoriesScreen(
    navigationManager: NavigationManager,
) {
    navigationManager.navigate(
        navigationCommand = MyNavigationDirections.categories()
    )
}

fun navigateToEditCategoryScreen(
    navigationManager: NavigationManager,
    categoryId: Int,
) {
    navigationManager.navigate(
        navigationCommand = MyNavigationDirections.editCategory(
            categoryId = categoryId,
        )
    )
}

fun navigateToEditSourceScreen(
    navigationManager: NavigationManager,
    sourceId: Int,
) {
    navigationManager.navigate(
        navigationCommand = MyNavigationDirections.editSource(
            sourceId = sourceId,
        )
    )
}

fun navigateToEditTransactionScreen(
    navigationManager: NavigationManager,
    transactionId: Int,
) {
    navigationManager.navigate(
        navigationCommand = MyNavigationDirections.editTransaction(
            transactionId = transactionId,
        )
    )
}

fun navigateToEditTransactionForScreen(
    navigationManager: NavigationManager,
    transactionForId: Int,
) {
    navigationManager.navigate(
        navigationCommand = MyNavigationDirections.editTransactionFor(
            transactionForId = transactionForId,
        )
    )
}

fun navigateToSettingsScreen(
    navigationManager: NavigationManager,
) {
    navigationManager.navigate(
        navigationCommand = MyNavigationDirections.settings()
    )
}

fun navigateToSourcesScreen(
    navigationManager: NavigationManager,
) {
    navigationManager.navigate(
        navigationCommand = MyNavigationDirections.sources()
    )
}

fun navigateToTransactionsScreen(
    navigationManager: NavigationManager,
) {
    navigationManager.navigate(
        navigationCommand = MyNavigationDirections.transactions()
    )
}

fun navigateToTransactionForValuesScreen(
    navigationManager: NavigationManager,
) {
    navigationManager.navigate(
        navigationCommand = MyNavigationDirections.transactionForValues()
    )
}

fun navigateToViewTransactionScreen(
    navigationManager: NavigationManager,
    transactionId: Int,
) {
    navigationManager.navigate(
        navigationCommand = MyNavigationDirections.viewTransaction(
            transactionId = transactionId,
        )
    )
}
