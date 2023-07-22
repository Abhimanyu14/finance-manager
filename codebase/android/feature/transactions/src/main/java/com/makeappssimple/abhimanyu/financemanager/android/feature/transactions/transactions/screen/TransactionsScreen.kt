package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.TransactionsScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.TransactionsScreenViewModelImpl

@Composable
fun TransactionsScreen(
    screenViewModel: TransactionsScreenViewModel = hiltViewModel<TransactionsScreenViewModelImpl>(),
) {
    screenViewModel.myLogger.logError(
        message = "Inside TransactionsScreen",
    )

    val screenUIData: MyResult<TransactionsScreenUIData>? by screenViewModel.screenUIData.collectAsStateWithLifecycle()
    val expenseCategories: List<Category>? by screenViewModel.expenseCategories.collectAsStateWithLifecycle()
    val incomeCategories: List<Category>? by screenViewModel.incomeCategories.collectAsStateWithLifecycle()
    val investmentCategories: List<Category>? by screenViewModel.investmentCategories.collectAsStateWithLifecycle()
    val accounts: List<Account>? by screenViewModel.accounts.collectAsStateWithLifecycle()
    val transactionForValues: List<TransactionFor>? by screenViewModel.transactionForValues.collectAsStateWithLifecycle()

    TransactionsScreenUI(
        events = TransactionsScreenUIEvents(
            addToSelectedTransactions = screenViewModel::addToSelectedTransactions,
            clearSelectedTransactions = screenViewModel::clearSelectedTransactions,
            getExpenseCategories = {
                expenseCategories.orEmpty()
            },
            getIncomeCategories = {
                incomeCategories.orEmpty()
            },
            getInvestmentCategories = {
                investmentCategories.orEmpty()
            },
            getAccounts = {
                accounts.orEmpty()
            },
            getTransactionForValues = {
                transactionForValues.orEmpty()
            },
            navigateToAddTransactionScreen = screenViewModel::navigateToAddTransactionScreen,
            navigateToViewTransactionScreen = screenViewModel::navigateToViewTransactionScreen,
            navigateUp = screenViewModel::navigateUp,
            removeFromSelectedTransactions = screenViewModel::removeFromSelectedTransactions,
            selectAllTransactions = screenViewModel::selectAllTransactions,
            toggleTransactionSelection = screenViewModel::toggleTransactionSelection,
            updateSearchText = screenViewModel::updateSearchText,
            updateSelectedFilter = screenViewModel::updateSelectedFilter,
            updateSelectedSortOption = screenViewModel::updateSelectedSortOption,
            updateTransactionForValuesInTransactions = screenViewModel::updateTransactionForValuesInTransactions,
        ),
        uiState = rememberTransactionsScreenUIState(
            data = screenUIData,
        ),
        state = rememberCommonScreenUIState(),
    )
}
