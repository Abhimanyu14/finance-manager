package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.transaction_list_item.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.Filter
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.SortOption
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.TransactionsScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.TransactionsScreenViewModelImpl
import java.time.LocalDate

@Composable
fun TransactionsScreen(
    screenViewModel: TransactionsScreenViewModel = hiltViewModel<TransactionsScreenViewModelImpl>(),
) {
    screenViewModel.logger.logError(
        message = "Inside TransactionsScreen",
    )
    val oldestTransactionLocalDate: LocalDate by screenViewModel.oldestTransactionLocalDate.collectAsStateWithLifecycle(
        initialValue = LocalDate.MIN,
    )
    val transactionDetailsListItemViewData: Map<String, List<TransactionListItemData>> by screenViewModel.transactionDetailsListItemViewData.collectAsStateWithLifecycle(
        initialValue = emptyMap(),
    )
    val isLoading: Boolean by screenViewModel.isLoading.collectAsStateWithLifecycle()
    val expenseCategories: List<Category>? by screenViewModel.expenseCategories.collectAsStateWithLifecycle()
    val incomeCategories: List<Category>? by screenViewModel.incomeCategories.collectAsStateWithLifecycle()
    val investmentCategories: List<Category>? by screenViewModel.investmentCategories.collectAsStateWithLifecycle()
    val selectedFilter: Filter by screenViewModel.selectedFilter.collectAsStateWithLifecycle()
    val selectedSortOption: SortOption by screenViewModel.selectedSortOption.collectAsStateWithLifecycle()
    val sources: List<Source>? by screenViewModel.sources.collectAsStateWithLifecycle()
    val searchText: String by screenViewModel.searchText.collectAsStateWithLifecycle()

    TransactionsScreenView(
        data = TransactionsScreenViewData(
            isLoading = isLoading,
            selectedFilter = selectedFilter,
            sortOptions = screenViewModel.sortOptions,
            transactionTypes = screenViewModel.transactionTypes,
            oldestTransactionLocalDate = oldestTransactionLocalDate,
            currentLocalDate = screenViewModel.currentLocalDate,
            currentTimeMillis = screenViewModel.currentTimeMillis,
            transactionDetailsListItemViewData = transactionDetailsListItemViewData,
            searchText = searchText,
            selectedSortOption = selectedSortOption,
        ),
        events = TransactionsScreenViewEvents(
            getExpenseCategories = {
                expenseCategories ?: emptyList()
            },
            getIncomeCategories = {
                incomeCategories ?: emptyList()
            },
            getInvestmentCategories = {
                investmentCategories ?: emptyList()
            },
            getSources = {
                sources ?: emptyList()
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
        state = rememberCommonScreenViewState(),
    )
}
