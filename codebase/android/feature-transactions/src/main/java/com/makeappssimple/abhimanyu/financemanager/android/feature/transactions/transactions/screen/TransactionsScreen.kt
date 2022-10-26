package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.logError
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.Filter
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.SortOption
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.TransactionsScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.TransactionsScreenViewModelImpl

@Composable
fun TransactionsScreen(
    screenViewModel: TransactionsScreenViewModel = hiltViewModel<TransactionsScreenViewModelImpl>(),
) {
    logError(
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
    val transactionDetailsListItemViewData: Map<String, List<TransactionData>> by screenViewModel.transactionDetailsListItemViewData.collectAsStateWithLifecycle(
        initialValue = emptyMap(),
    )
    val isLoading: Boolean by screenViewModel.isLoading.collectAsStateWithLifecycle()
    val selectedFilter: Filter by screenViewModel.selectedFilter.collectAsStateWithLifecycle()
    val selectedSortOption: SortOption by screenViewModel.selectedSortOption.collectAsStateWithLifecycle()
    val searchText: String by screenViewModel.searchText.collectAsStateWithLifecycle()

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
    }

    TransactionsScreenView(
        data = TransactionsScreenViewData(
            isLoading = isLoading,
            expenseCategories = expenseCategories,
            incomeCategories = incomeCategories,
            investmentCategories = investmentCategories,
            selectedFilter = selectedFilter,
            sources = sources,
            transactionDetailsListItemViewData = transactionDetailsListItemViewData,
            navigationManager = screenViewModel.navigationManager,
            searchText = searchText,
            selectedSortOption = selectedSortOption,
            deleteTransaction = { transactionId ->
                screenViewModel.deleteTransaction(
                    id = transactionId,
                )
            },
            updateSearchText = { updatedSearchText ->
                screenViewModel.updateSearchText(
                    updatedSearchText = updatedSearchText,
                )
            },
            updateSelectedFilter = { updatedSelectedFilter ->
                screenViewModel.updateSelectedFilter(
                    updatedSelectedFilter = updatedSelectedFilter,
                )
            },
            updateSelectedSortOption = { updatedSelectedSortOption ->
                screenViewModel.updateSelectedSortOption(
                    updatedSelectedSortOption = updatedSelectedSortOption,
                )
            },
        ),
        state = rememberCommonScreenViewState(),
    )
}
