package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.logError
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.components.TransactionsListItemViewData
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
    val sources: List<Source> by screenViewModel.sources.collectAsStateWithLifecycle(
        initialValue = emptyList(),
    )
    val transactionsListItemViewData: Map<String, List<TransactionsListItemViewData>> by screenViewModel.transactionsListItemViewData.collectAsStateWithLifecycle(
        initialValue = emptyMap(),
    )
    val selectedExpenseCategoryIndices: List<Int> by screenViewModel.selectedExpenseCategoryIndices.collectAsStateWithLifecycle()
    val selectedIncomeCategoryIndices: List<Int> by screenViewModel.selectedIncomeCategoryIndices.collectAsStateWithLifecycle()
    val selectedSourceIndices: List<Int> by screenViewModel.selectedSourceIndices.collectAsStateWithLifecycle()
    val selectedTransactionTypesIndices: List<Int> by screenViewModel.selectedTransactionTypesIndices.collectAsStateWithLifecycle()
    val selectedSortOption: SortOption by screenViewModel.selectedSortOption.collectAsStateWithLifecycle()
    val searchText: String by screenViewModel.searchText.collectAsStateWithLifecycle()

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
    }

    TransactionsScreenView(
        data = TransactionsScreenViewData(
            expenseCategories = expenseCategories,
            incomeCategories = incomeCategories,
            selectedExpenseCategoryIndices = selectedExpenseCategoryIndices,
            selectedIncomeCategoryIndices = selectedIncomeCategoryIndices,
            selectedSourceIndices = selectedSourceIndices,
            selectedTransactionTypesIndices = selectedTransactionTypesIndices,
            sources = sources,
            transactionsListItemViewData = transactionsListItemViewData,
            navigationManager = screenViewModel.navigationManager,
            searchText = searchText,
            selectedSortOption = selectedSortOption,
            deleteTransaction = { transactionId ->
                screenViewModel.deleteTransaction(
                    id = transactionId,
                )
            },
            updateSelectedExpenseCategoryIndices = { updatedSelectedExpenseCategoryIndices ->
                screenViewModel.updateSelectedExpenseCategoryIndices(
                    updatedSelectedExpenseCategoryIndices = updatedSelectedExpenseCategoryIndices,
                )
            },
            updateSelectedIncomeCategoryIndices = { updatedSelectedIncomeCategoryIndices ->
                screenViewModel.updateSelectedIncomeCategoryIndices(
                    updatedSelectedIncomeCategoryIndices = updatedSelectedIncomeCategoryIndices,
                )
            },
            updateSelectedSourceIndices = { updatedSelectedSourceIndices ->
                screenViewModel.updateSelectedSourceIndices(
                    updatedSelectedSourceIndices = updatedSelectedSourceIndices,
                )
            },
            updateSelectedTransactionTypesIndices = { updatedSelectedTransactionTypesIndices ->
                screenViewModel.updateSelectedTransactionTypesIndices(
                    updatedSelectedTransactionTypesIndices = updatedSelectedTransactionTypesIndices,
                )
            },
            updateSearchText = { updatedSearchText ->
                screenViewModel.updateSearchText(
                    updatedSearchText = updatedSearchText,
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
