package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.TransactionsScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.TransactionsScreenViewModelImpl

@Composable
fun TransactionsScreen(
    screenViewModel: TransactionsScreenViewModel = hiltViewModel<TransactionsScreenViewModelImpl>(),
) {
    screenViewModel.logger.logError(
        message = "Inside TransactionsScreen",
    )

    val screenUIData: TransactionsScreenUIData? by screenViewModel.screenUIData.collectAsStateWithLifecycle()
    val expenseCategories: List<Category>? by screenViewModel.expenseCategories.collectAsStateWithLifecycle()
    val incomeCategories: List<Category>? by screenViewModel.incomeCategories.collectAsStateWithLifecycle()
    val investmentCategories: List<Category>? by screenViewModel.investmentCategories.collectAsStateWithLifecycle()
    val sources: List<Source>? by screenViewModel.sources.collectAsStateWithLifecycle()

    TransactionsScreenUI(
        events = TransactionsScreenUIEvents(
            getExpenseCategories = {
                expenseCategories.orEmpty()
            },
            getIncomeCategories = {
                incomeCategories.orEmpty()
            },
            getInvestmentCategories = {
                investmentCategories.orEmpty()
            },
            getSources = {
                sources.orEmpty()
            },
            navigateToAddTransactionScreen = {
                screenViewModel.navigationManager.navigate(
                    navigationCommand = MyNavigationDirections.AddTransaction()
                )
            },
            navigateToViewTransactionScreen = { transactionId ->
                screenViewModel.navigationManager.navigate(
                    navigationCommand = MyNavigationDirections.ViewTransaction(
                        transactionId = transactionId,
                    )
                )
            },
            navigateUp = {
                screenViewModel.navigationManager.navigate(
                    navigationCommand = MyNavigationDirections.NavigateUp
                )
            },
            updateSearchText = screenViewModel::updateSearchText,
            updateSelectedFilter = screenViewModel::updateSelectedFilter,
            updateSelectedSortOption = screenViewModel::updateSelectedSortOption,
        ),
        uiState = rememberTransactionsScreenUIState(
            data = screenUIData ?: TransactionsScreenUIData(),
        ),
        state = rememberCommonScreenUIState(),
    )
}
