package com.makeappssimple.abhimanyu.financemanager.android.core.navigation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class NavigationManagerImpl(
    private val coroutineScope: CoroutineScope,
) : NavigationManager {
    private val _command: MutableSharedFlow<NavigationCommand> = MutableSharedFlow()
    override val command: SharedFlow<NavigationCommand> = _command

    override fun navigateToAccounts() {
        navigate(
            navigationCommand = MyNavigationDirections.Accounts,
        )
    }

    override fun navigateToAddAccount() {
        navigate(
            navigationCommand = MyNavigationDirections.AddAccount,
        )
    }

    override fun navigateToAddCategory(
        transactionType: String,
    ) {
        navigate(
            navigationCommand = MyNavigationDirections.AddCategory(
                transactionType = transactionType,
            ),
        )
    }

    override fun navigateToAddTransaction(
        transactionId: Int?,
    ) {
        navigate(
            navigationCommand = MyNavigationDirections.AddTransaction(
                transactionId = transactionId
            ),
        )
    }

    override fun navigateToAddTransactionFor() {
        navigate(
            navigationCommand = MyNavigationDirections.AddTransactionFor,
        )
    }

    override fun navigateToAnalysis() {
        navigate(
            navigationCommand = MyNavigationDirections.Analysis,
        )
    }

    override fun navigateToCategories() {
        navigate(
            navigationCommand = MyNavigationDirections.Categories,
        )
    }

    override fun navigateToEditAccount(
        accountId: Int,
    ) {
        navigate(
            navigationCommand = MyNavigationDirections.EditAccount(
                accountId = accountId,
            ),
        )
    }

    override fun navigateToEditCategory(
        categoryId: Int,
    ) {
        navigate(
            navigationCommand = MyNavigationDirections.EditCategory(
                categoryId = categoryId,
            ),
        )
    }

    override fun navigateToEditTransaction(
        transactionId: Int,
    ) {
        navigate(
            navigationCommand = MyNavigationDirections.EditTransaction(
                transactionId = transactionId,
            ),
        )
    }

    override fun navigateToEditTransactionFor(
        transactionForId: Int,
    ) {
        navigate(
            navigationCommand = MyNavigationDirections.EditTransactionFor(
                transactionForId = transactionForId,
            ),
        )
    }

    override fun navigateToHome() {
        navigate(
            navigationCommand = MyNavigationDirections.Home,
        )
    }

    override fun navigateUp() {
        navigate(
            navigationCommand = MyNavigationDirections.NavigateUp,
        )
    }

    override fun navigateToOpenSourceLicenses() {
        navigate(
            navigationCommand = MyNavigationDirections.OpenSourceLicenses,
        )
    }

    override fun navigateToSettings() {
        navigate(
            navigationCommand = MyNavigationDirections.Settings,
        )
    }

    override fun navigateToTransactionForValues() {
        navigate(
            navigationCommand = MyNavigationDirections.TransactionForValues,
        )
    }

    override fun navigateToTransactions() {
        navigate(
            navigationCommand = MyNavigationDirections.Transactions,
        )
    }

    override fun navigateToViewTransaction(
        transactionId: Int,
    ) {
        navigate(
            navigationCommand = MyNavigationDirections.ViewTransaction(
                transactionId = transactionId,
            ),
        )
    }

    private fun navigate(
        navigationCommand: NavigationCommand,
    ) {
        coroutineScope.launch {
            _command.emit(navigationCommand)
        }
    }
}
