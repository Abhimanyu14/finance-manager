package com.makeappssimple.abhimanyu.financemanager.android.core.navigation.fake

import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationCommand
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class FakeNavigationManagerImpl(
    private val coroutineScope: CoroutineScope,
) : NavigationManager {
    private val _command: MutableSharedFlow<NavigationCommand> = MutableSharedFlow()
    override val command: SharedFlow<NavigationCommand> = _command

//    override fun navigate(
//        navigationCommand: NavigationCommand,
//    ) {
//        coroutineScope.launch {
//            _command.emit(navigationCommand)
//        }
//    }

    override fun navigateToAccountsScreen() {
        TODO("Not yet implemented")
    }

    override fun navigateToAddAccountScreen() {
        TODO("Not yet implemented")
    }

    override fun navigateToAddCategoryScreen(transactionType: String) {
        TODO("Not yet implemented")
    }

    override fun navigateToAddTransactionScreen(transactionId: Int?) {
        TODO("Not yet implemented")
    }

    override fun navigateToAddTransactionForScreen() {
        TODO("Not yet implemented")
    }

    override fun navigateToAnalysisScreen() {
        TODO("Not yet implemented")
    }

    override fun navigateToCategoriesScreen() {
        TODO("Not yet implemented")
    }

    override fun navigateToEditAccountScreen(accountId: Int) {
        TODO("Not yet implemented")
    }

    override fun navigateToEditCategoryScreen(categoryId: Int) {
        TODO("Not yet implemented")
    }

    override fun navigateToEditTransactionScreen(transactionId: Int) {
        TODO("Not yet implemented")
    }

    override fun navigateToEditTransactionForScreen(transactionForId: Int) {
        TODO("Not yet implemented")
    }

    override fun navigateToHomeScreen() {
        TODO("Not yet implemented")
    }

    override fun navigateToOpenSourceLicensesScreen() {
        TODO("Not yet implemented")
    }

    override fun navigateToSettingsScreen() {
        TODO("Not yet implemented")
    }

    override fun navigateToTransactionForValuesScreen() {
        TODO("Not yet implemented")
    }

    override fun navigateToTransactionsScreen() {
        TODO("Not yet implemented")
    }

    override fun navigateToViewTransactionScreen(transactionId: Int) {
        TODO("Not yet implemented")
    }

    override fun navigateUp() {
        TODO("Not yet implemented")
    }
}
