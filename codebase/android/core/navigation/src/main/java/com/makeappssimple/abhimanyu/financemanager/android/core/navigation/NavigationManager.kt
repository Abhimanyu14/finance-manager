package com.makeappssimple.abhimanyu.financemanager.android.core.navigation

import kotlinx.coroutines.flow.SharedFlow

interface NavigationManager {
    val command: SharedFlow<NavigationCommand>

    fun navigateToAccounts()

    fun navigateToAddAccount()

    fun navigateToAddCategory(
        transactionType: String,
    )

    fun navigateToAddTransaction(
        transactionId: Int? = null,
    )

    fun navigateToAddTransactionFor()

    fun navigateToAnalysis()

    fun navigateToCategories()

    fun navigateToEditAccount(
        accountId: Int,
    )

    fun navigateToEditCategory(
        categoryId: Int,
    )

    fun navigateToEditTransaction(
        transactionId: Int,
    )

    fun navigateToEditTransactionFor(
        transactionForId: Int,
    )

    fun navigateToHome()

    fun navigateUp()

    fun navigateToOpenSourceLicenses()

    fun navigateToSettings()

    fun navigateToTransactionForValues()

    fun navigateToTransactions()

    fun navigateToViewTransaction(
        transactionId: Int,
    )
}
