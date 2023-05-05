package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.Filter
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.SortOption
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.TransactionsScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.TransactionsScreenViewModelImpl

@Composable
fun TransactionsScreen(
    screenViewModel: TransactionsScreenViewModel = hiltViewModel<TransactionsScreenViewModelImpl>(),
) {
    screenViewModel.logger.logError(
        message = "Inside TransactionsScreen",
    )
    val expenseCategories: List<Category> by screenViewModel.expenseCategories.collectAsStateWithLifecycle(
        initialValue = emptyList(),
    )
    val incomeCategories: List<Category> by screenViewModel.incomeCategories.collectAsStateWithLifecycle(
        initialValue = emptyList(),
    )
    val investmentCategories: List<Category> by screenViewModel.investmentCategories.collectAsStateWithLifecycle(
        initialValue = emptyList(),
    )
    val sources: List<Source> by screenViewModel.sources.collectAsStateWithLifecycle(
        initialValue = emptyList(),
    )
    val oldestTransactionTimestamp: Long by screenViewModel.oldestTransactionTimestamp.collectAsStateWithLifecycle(
        initialValue = 0L,
    )
    val transactionDetailsListItemViewData: Map<String, List<TransactionData>> by screenViewModel.transactionDetailsListItemViewData.collectAsStateWithLifecycle(
        initialValue = emptyMap(),
    )
    val isLoading: Boolean by screenViewModel.isLoading.collectAsStateWithLifecycle()
    val selectedFilter: Filter by screenViewModel.selectedFilter.collectAsStateWithLifecycle()
    val selectedSortOption: SortOption by screenViewModel.selectedSortOption.collectAsStateWithLifecycle()
    val searchText: String by screenViewModel.searchText.collectAsStateWithLifecycle()

    TransactionsScreenView(
        data = TransactionsScreenViewData(
            isLoading = isLoading,
            dateTimeUtil = screenViewModel.dateTimeUtil,
            selectedFilter = selectedFilter,
            expenseCategories = expenseCategories,
            incomeCategories = incomeCategories,
            investmentCategories = investmentCategories,
            sortOptions = screenViewModel.sortOptions,
            sources = sources,
            transactionTypes = screenViewModel.transactionTypes,
            oldestTransactionTimestamp = oldestTransactionTimestamp,
            transactionDetailsListItemViewData = transactionDetailsListItemViewData,
            searchText = searchText,
            selectedSortOption = selectedSortOption,
            deleteTransaction = screenViewModel::deleteTransaction,
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
        state = rememberCommonScreenViewState(),
    )
}
