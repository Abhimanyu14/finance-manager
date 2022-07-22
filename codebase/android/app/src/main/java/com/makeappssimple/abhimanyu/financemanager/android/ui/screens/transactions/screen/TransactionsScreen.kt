package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.transactions.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.utils.logError
import com.makeappssimple.abhimanyu.financemanager.android.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.transactions.components.TransactionsListItemViewData
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.transactions.viewmodel.SortOption
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.transactions.viewmodel.TransactionsScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.transactions.viewmodel.TransactionsScreenViewModelImpl

@Composable
fun TransactionsScreen(
    screenViewModel: TransactionsScreenViewModel = hiltViewModel<TransactionsScreenViewModelImpl>(),
) {
    logError(
        message = "Inside TransactionsScreen",
    )
    val expenseCategories: List<Category> by screenViewModel.expenseCategories.collectAsState(
        initial = emptyList(),
    )
    val incomeCategories: List<Category> by screenViewModel.incomeCategories.collectAsState(
        initial = emptyList(),
    )
    val sources: List<Source> by screenViewModel.sources.collectAsState(
        initial = emptyList(),
    )
    val transactionsListItemViewData: Map<String, List<TransactionsListItemViewData>> by screenViewModel.transactionsListItemViewData.collectAsState(
        initial = emptyMap(),
    )
    val selectedExpenseCategoryIndices: List<Int> by screenViewModel.selectedExpenseCategoryIndices.collectAsState()
    val selectedIncomeCategoryIndices: List<Int> by screenViewModel.selectedIncomeCategoryIndices.collectAsState()
    val selectedSourceIndices: List<Int> by screenViewModel.selectedSourceIndices.collectAsState()
    val selectedTransactionTypesIndices: List<Int> by screenViewModel.selectedTransactionTypesIndices.collectAsState()
    val selectedSortOption: SortOption by screenViewModel.selectedSortOption.collectAsState()
    val searchText: String by screenViewModel.searchText.collectAsState()

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
