package com.makeappssimple.abhimanyu.financemanager.android.core.navigation

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharedFlow

public interface NavigationKit {
    public val command: SharedFlow<NavigationCommand>

    public fun navigateToAccountsScreen(): Job

    public fun navigateToAddAccountScreen(): Job

    public fun navigateToAddCategoryScreen(
        transactionType: String,
    ): Job

    public fun navigateToAddTransactionScreen(
        transactionId: Int? = null,
    ): Job

    public fun navigateToAddTransactionForScreen(): Job

    public fun navigateToAnalysisScreen(): Job

    public fun navigateToCategoriesScreen(): Job

    public fun navigateToEditAccountScreen(
        accountId: Int,
    ): Job

    public fun navigateToEditCategoryScreen(
        categoryId: Int,
    ): Job

    public fun navigateToEditTransactionScreen(
        transactionId: Int,
    ): Job

    public fun navigateToEditTransactionForScreen(
        transactionForId: Int,
    ): Job

    public fun navigateToHomeScreen(): Job

    public fun navigateToOpenSourceLicensesScreen(): Job

    public fun navigateToSettingsScreen(): Job

    public fun navigateToTransactionForValuesScreen(): Job

    public fun navigateToTransactionsScreen(): Job

    public fun navigateUp(): Job

    public fun navigateToViewTransactionScreen(
        transactionId: Int,
    ): Job
}
