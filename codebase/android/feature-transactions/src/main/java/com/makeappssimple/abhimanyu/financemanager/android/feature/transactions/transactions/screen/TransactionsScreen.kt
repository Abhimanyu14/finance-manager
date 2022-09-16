package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.logError
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenViewState
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
    val selectedExpenseCategoryIndices: List<Int> by screenViewModel.selectedExpenseCategoryIndices.collectAsStateWithLifecycle()
    val selectedIncomeCategoryIndices: List<Int> by screenViewModel.selectedIncomeCategoryIndices.collectAsStateWithLifecycle()
    val selectedInvestmentCategoryIndices: List<Int> by screenViewModel.selectedInvestmentCategoryIndices.collectAsStateWithLifecycle()
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
            isLoading = isLoading,
            expenseCategories = expenseCategories,
            incomeCategories = incomeCategories,
            investmentCategories = investmentCategories,
            selectedExpenseCategoryIndices = selectedExpenseCategoryIndices,
            selectedIncomeCategoryIndices = selectedIncomeCategoryIndices,
            selectedInvestmentCategoryIndices = selectedInvestmentCategoryIndices,
            selectedSourceIndices = selectedSourceIndices,
            selectedTransactionTypesIndices = selectedTransactionTypesIndices,
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
            updateSelectedInvestmentCategoryIndices = { updatedSelectedInvestmentCategoryIndices ->
                screenViewModel.updateSelectedInvestmentCategoryIndices(
                    updatedSelectedInvestmentCategoryIndices = updatedSelectedInvestmentCategoryIndices,
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
