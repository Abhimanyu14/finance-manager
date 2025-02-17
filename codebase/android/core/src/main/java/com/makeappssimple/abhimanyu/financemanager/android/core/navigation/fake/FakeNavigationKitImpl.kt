package com.makeappssimple.abhimanyu.financemanager.android.core.navigation.fake

import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationCommand
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationKit
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

public class FakeNavigationKitImpl : NavigationKit {
    private val _command: MutableSharedFlow<NavigationCommand> = MutableSharedFlow()
    override val command: SharedFlow<NavigationCommand> = _command

    override fun navigateToAccountsScreen(): Job {
        return Job()
    }

    override fun navigateToAddAccountScreen(): Job {
        return Job()
    }

    override fun navigateToAddCategoryScreen(
        transactionType: String,
    ): Job {
        return Job()
    }

    override fun navigateToAddTransactionScreen(
        transactionId: Int?,
    ): Job {
        return Job()
    }

    override fun navigateToAddTransactionForScreen(): Job {
        return Job()
    }

    override fun navigateToAnalysisScreen(): Job {
        return Job()
    }

    override fun navigateToCategoriesScreen(): Job {
        return Job()
    }

    override fun navigateToEditAccountScreen(
        accountId: Int,
    ): Job {
        return Job()
    }

    override fun navigateToEditCategoryScreen(
        categoryId: Int,
    ): Job {
        return Job()
    }

    override fun navigateToEditTransactionScreen(
        transactionId: Int,
    ): Job {
        return Job()
    }

    override fun navigateToEditTransactionForScreen(
        transactionForId: Int,
    ): Job {
        return Job()
    }

    override fun navigateToHomeScreen(): Job {
        return Job()
    }

    override fun navigateToOpenSourceLicensesScreen(): Job {
        return Job()
    }

    override fun navigateToSettingsScreen(): Job {
        return Job()
    }

    override fun navigateToTransactionForValuesScreen(): Job {
        return Job()
    }

    override fun navigateToTransactionsScreen(): Job {
        return Job()
    }

    override fun navigateToViewTransactionScreen(
        transactionId: Int,
    ): Job {
        return Job()
    }

    override fun navigateUp(): Job {
        return Job()
    }
}
