package com.makeappssimple.abhimanyu.financemanager.android.core.navigation.fake

import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationCommand
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

public class FakeNavigatorImpl : Navigator {
    private val _command: MutableSharedFlow<NavigationCommand> = MutableSharedFlow()
    override val command: SharedFlow<NavigationCommand> = _command

    override fun navigateToAccountsScreen() {}

    override fun navigateToAddAccountScreen() {}

    override fun navigateToAddCategoryScreen(
        transactionType: String,
    ) {
    }

    override fun navigateToAddTransactionScreen(
        transactionId: Int?,
    ) {
    }

    override fun navigateToAddTransactionForScreen() {}

    override fun navigateToAnalysisScreen() {}

    override fun navigateToCategoriesScreen() {}

    override fun navigateToEditAccountScreen(
        accountId: Int,
    ) {
    }

    override fun navigateToEditCategoryScreen(
        categoryId: Int,
    ) {
    }

    override fun navigateToEditTransactionScreen(
        transactionId: Int,
    ) {
    }

    override fun navigateToEditTransactionForScreen(
        transactionForId: Int,
    ) {
    }

    override fun navigateToHomeScreen() {}

    override fun navigateToOpenSourceLicensesScreen() {}

    override fun navigateToSettingsScreen() {}

    override fun navigateToTransactionForValuesScreen() {}

    override fun navigateToTransactionsScreen() {}

    override fun navigateToViewTransactionScreen(
        transactionId: Int,
    ) {
    }

    override fun navigateUp(): Job {
        return Job()
    }
}
