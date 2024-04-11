package com.makeappssimple.abhimanyu.financemanager.android.core.navigation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class NavigatorImpl(
    private val coroutineScope: CoroutineScope,
) : Navigator {
    private val _command: MutableSharedFlow<NavigationCommand> = MutableSharedFlow()
    override val command: SharedFlow<NavigationCommand> = _command

    override fun navigateToAccountsScreen() {
        navigate(
            navigationCommand = MyNavigationDirections.Accounts,
        )
    }

    override fun navigateToAddAccountScreen() {
        navigate(
            navigationCommand = MyNavigationDirections.AddAccount,
        )
    }

    override fun navigateToAddCategoryScreen(
        transactionType: String,
    ) {
        navigate(
            navigationCommand = MyNavigationDirections.AddCategory(
                transactionType = transactionType,
            ),
        )
    }

    override fun navigateToAddTransactionScreen(
        transactionId: Int?,
    ) {
        navigate(
            navigationCommand = MyNavigationDirections.AddTransaction(
                transactionId = transactionId
            ),
        )
    }

    override fun navigateToAddTransactionForScreen() {
        navigate(
            navigationCommand = MyNavigationDirections.AddTransactionFor,
        )
    }

    override fun navigateToAnalysisScreen() {
        navigate(
            navigationCommand = MyNavigationDirections.Analysis,
        )
    }

    override fun navigateToCategoriesScreen() {
        navigate(
            navigationCommand = MyNavigationDirections.Categories,
        )
    }

    override fun navigateToEditAccountScreen(
        accountId: Int,
    ) {
        navigate(
            navigationCommand = MyNavigationDirections.EditAccount(
                accountId = accountId,
            ),
        )
    }

    override fun navigateToEditCategoryScreen(
        categoryId: Int,
    ) {
        navigate(
            navigationCommand = MyNavigationDirections.EditCategory(
                categoryId = categoryId,
            ),
        )
    }

    override fun navigateToEditTransactionScreen(
        transactionId: Int,
    ) {
        navigate(
            navigationCommand = MyNavigationDirections.EditTransaction(
                transactionId = transactionId,
            ),
        )
    }

    override fun navigateToEditTransactionForScreen(
        transactionForId: Int,
    ) {
        navigate(
            navigationCommand = MyNavigationDirections.EditTransactionFor(
                transactionForId = transactionForId,
            ),
        )
    }

    override fun navigateToHomeScreen() {
        navigate(
            navigationCommand = MyNavigationDirections.Home,
        )
    }

    override fun navigateToOpenSourceLicensesScreen() {
        navigate(
            navigationCommand = MyNavigationDirections.OpenSourceLicenses,
        )
    }

    override fun navigateToSettingsScreen() {
        navigate(
            navigationCommand = MyNavigationDirections.Settings,
        )
    }

    override fun navigateToTransactionForValuesScreen() {
        navigate(
            navigationCommand = MyNavigationDirections.TransactionForValues,
        )
    }

    override fun navigateToTransactionsScreen() {
        navigate(
            navigationCommand = MyNavigationDirections.Transactions,
        )
    }

    override fun navigateToViewTransactionScreen(
        transactionId: Int,
    ) {
        navigate(
            navigationCommand = MyNavigationDirections.ViewTransaction(
                transactionId = transactionId,
            ),
        )
    }

    override fun navigateUp() {
        navigate(
            navigationCommand = MyNavigationDirections.NavigateUp,
        )
    }

    private fun navigate(
        navigationCommand: NavigationCommand,
    ) {
        coroutineScope.launch {
            _command.emit(
                value = navigationCommand,
            )
        }
    }
}
