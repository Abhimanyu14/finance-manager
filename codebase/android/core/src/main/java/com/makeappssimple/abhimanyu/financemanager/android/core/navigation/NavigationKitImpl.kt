package com.makeappssimple.abhimanyu.financemanager.android.core.navigation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

public class NavigationKitImpl(
    private val coroutineScope: CoroutineScope,
) : NavigationKit {
    private val _command: MutableSharedFlow<NavigationCommand> = MutableSharedFlow()
    override val command: SharedFlow<NavigationCommand> = _command

    override fun navigateToAccountsScreen(): Job {
        return navigate(
            navigationCommand = MyNavigationDirections.Accounts,
        )
    }

    override fun navigateToAddAccountScreen(): Job {
        return navigate(
            navigationCommand = MyNavigationDirections.AddAccount,
        )
    }

    override fun navigateToAddCategoryScreen(
        transactionType: String,
    ): Job {
        return navigate(
            navigationCommand = MyNavigationDirections.AddCategory(
                transactionType = transactionType,
            ),
        )
    }

    override fun navigateToAddTransactionScreen(
        transactionId: Int?,
    ): Job {
        return navigate(
            navigationCommand = MyNavigationDirections.AddTransaction(
                transactionId = transactionId,
            ),
        )
    }

    override fun navigateToAddTransactionForScreen(): Job {
        return navigate(
            navigationCommand = MyNavigationDirections.AddTransactionFor,
        )
    }

    override fun navigateToAnalysisScreen(): Job {
        return navigate(
            navigationCommand = MyNavigationDirections.Analysis,
        )
    }

    override fun navigateToCategoriesScreen(): Job {
        return navigate(
            navigationCommand = MyNavigationDirections.Categories,
        )
    }

    override fun navigateToEditAccountScreen(
        accountId: Int,
    ): Job {
        return navigate(
            navigationCommand = MyNavigationDirections.EditAccount(
                accountId = accountId,
            ),
        )
    }

    override fun navigateToEditCategoryScreen(
        categoryId: Int,
    ): Job {
        return navigate(
            navigationCommand = MyNavigationDirections.EditCategory(
                categoryId = categoryId,
            ),
        )
    }

    override fun navigateToEditTransactionScreen(
        transactionId: Int,
    ): Job {
        return navigate(
            navigationCommand = MyNavigationDirections.EditTransaction(
                transactionId = transactionId,
            ),
        )
    }

    override fun navigateToEditTransactionForScreen(
        transactionForId: Int,
    ): Job {
        return navigate(
            navigationCommand = MyNavigationDirections.EditTransactionFor(
                transactionForId = transactionForId,
            ),
        )
    }

    override fun navigateToHomeScreen(): Job {
        return navigate(
            navigationCommand = MyNavigationDirections.Home,
        )
    }

    override fun navigateToOpenSourceLicensesScreen(): Job {
        return navigate(
            navigationCommand = MyNavigationDirections.OpenSourceLicenses,
        )
    }

    override fun navigateToSettingsScreen(): Job {
        return navigate(
            navigationCommand = MyNavigationDirections.Settings,
        )
    }

    override fun navigateToTransactionForValuesScreen(): Job {
        return navigate(
            navigationCommand = MyNavigationDirections.TransactionForValues,
        )
    }

    override fun navigateToTransactionsScreen(): Job {
        return navigate(
            navigationCommand = MyNavigationDirections.Transactions,
        )
    }

    override fun navigateToViewTransactionScreen(
        transactionId: Int,
    ): Job {
        return navigate(
            navigationCommand = MyNavigationDirections.ViewTransaction(
                transactionId = transactionId,
            ),
        )
    }

    override fun navigateUp(): Job {
        return navigate(
            navigationCommand = MyNavigationDirections.NavigateUp,
        )
    }

    private fun navigate(
        navigationCommand: NavigationCommand,
    ): Job {
        return coroutineScope.launch {
            _command.emit(
                value = navigationCommand,
            )
        }
    }
}
