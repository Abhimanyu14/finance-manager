package com.makeappssimple.abhimanyu.financemanager.android.core.navigation

import kotlinx.coroutines.flow.SharedFlow

interface NavigationManager {
    val command: SharedFlow<NavigationCommand>

    fun navigateToAccountsScreen()

    fun navigateToAddAccountScreen()

    fun navigateToAddCategoryScreen(
        transactionType: String,
    )

    fun navigateToAddTransactionScreen(
        transactionId: Int? = null,
    )

    fun navigateToAddTransactionForScreen()

    fun navigateToAnalysisScreen()

    fun navigateToCategoriesScreen()

    fun navigateToEditAccountScreen(
        accountId: Int,
    )

    fun navigateToEditCategoryScreen(
        categoryId: Int,
    )

    fun navigateToEditTransactionScreen(
        transactionId: Int,
    )

    fun navigateToEditTransactionForScreen(
        transactionForId: Int,
    )

    fun navigateToHomeScreen()

    fun navigateToOpenSourceLicensesScreen()

    fun navigateToSettingsScreen()

    fun navigateToTransactionForValuesScreen()

    fun navigateToTransactionsScreen()

    fun navigateToViewTransactionScreen(
        transactionId: Int,
    )

    fun navigateUp()
}
