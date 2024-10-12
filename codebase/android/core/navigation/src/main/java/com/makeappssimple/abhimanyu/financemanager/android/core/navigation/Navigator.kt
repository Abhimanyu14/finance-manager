package com.makeappssimple.abhimanyu.financemanager.android.core.navigation

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharedFlow

public interface Navigator {
    public val command: SharedFlow<NavigationCommand>

    public fun navigateToAccountsScreen()

    public fun navigateToAddAccountScreen()

    public fun navigateToAddCategoryScreen(
        transactionType: String,
    )

    public fun navigateToAddTransactionScreen(
        transactionId: Int? = null,
    )

    public fun navigateToAddTransactionForScreen()

    public fun navigateToAnalysisScreen()

    public fun navigateToCategoriesScreen()

    public fun navigateToEditAccountScreen(
        accountId: Int,
    )

    public fun navigateToEditCategoryScreen(
        categoryId: Int,
    )

    public fun navigateToEditTransactionScreen(
        transactionId: Int,
    )

    public fun navigateToEditTransactionForScreen(
        transactionForId: Int,
    )

    public fun navigateToHomeScreen()

    public fun navigateToOpenSourceLicensesScreen()

    public fun navigateToSettingsScreen()

    public fun navigateToTransactionForValuesScreen()

    public fun navigateToTransactionsScreen()

    public fun navigateToViewTransactionScreen(
        transactionId: Int,
    )

    public fun navigateUp(): Job
}
