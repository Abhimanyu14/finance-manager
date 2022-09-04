package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.GetCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionDetail
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetAllTransactionDetailsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase.DeleteTransactionAndRevertOtherDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.getDateString
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
internal class TransactionsScreenViewModelImpl @Inject constructor(
    getCategoriesUseCase: GetCategoriesUseCase,
    getSourcesUseCase: GetSourcesUseCase,
    getAllTransactionDetailsUseCase: GetAllTransactionDetailsUseCase,
    override val navigationManager: NavigationManager,
    private val dispatcherProvider: DispatcherProvider,
    private val deleteTransactionAndRevertOtherDataUseCase: DeleteTransactionAndRevertOtherDataUseCase,
) : TransactionsScreenViewModel, ViewModel() {
    private val categories: Flow<List<Category>> = getCategoriesUseCase()
    private val allTransactionDetails: Flow<List<TransactionDetail>> =
        getAllTransactionDetailsUseCase()

    // region Search
    private val _searchText = MutableStateFlow(
        value = "",
    )
    override val searchText: StateFlow<String> = _searchText
    // endregion

    // region Filter
    private val _selectedExpenseCategoryIndices = MutableStateFlow(
        value = emptyList<Int>(),
    )
    override val selectedExpenseCategoryIndices: StateFlow<List<Int>> =
        _selectedExpenseCategoryIndices
    private val _selectedIncomeCategoryIndices = MutableStateFlow(
        value = emptyList<Int>(),
    )
    override val selectedIncomeCategoryIndices: StateFlow<List<Int>> =
        _selectedIncomeCategoryIndices
    private val _selectedInvestmentCategoryIndices = MutableStateFlow(
        value = emptyList<Int>(),
    )
    override val selectedInvestmentCategoryIndices: StateFlow<List<Int>> =
        _selectedInvestmentCategoryIndices
    private val _selectedSourceIndices = MutableStateFlow(
        value = emptyList<Int>(),
    )
    override val selectedSourceIndices: StateFlow<List<Int>> = _selectedSourceIndices
    private val _selectedTransactionTypesIndices = MutableStateFlow(
        value = emptyList<Int>(),
    )
    override val selectedTransactionTypesIndices: StateFlow<List<Int>> =
        _selectedTransactionTypesIndices
    // endregion

    // region Sort
    private val _selectedSortOption = MutableStateFlow(
        value = SortOption.LATEST_FIRST,
    )
    override val selectedSortOption: StateFlow<SortOption> = _selectedSortOption
    // endregion

    override val expenseCategories: Flow<List<Category>> = categories.map {
        it.filter { category ->
            category.transactionType == TransactionType.EXPENSE
        }
    }
    override val incomeCategories: Flow<List<Category>> = categories.map {
        it.filter { category ->
            category.transactionType == TransactionType.INCOME
        }
    }
    override val investmentCategories: Flow<List<Category>> = categories.map {
        it.filter { category ->
            category.transactionType == TransactionType.INVESTMENT
        }
    }
    override val sources: Flow<List<Source>> = getSourcesUseCase()

    val transactionTypes: List<TransactionType> = TransactionType.values().toList()
    override val transactionDetailsListItemViewData: Flow<Map<String, List<TransactionDetail>>> =
        combine(
            allTransactionDetails,
            searchText,
            selectedExpenseCategoryIndices,
            selectedIncomeCategoryIndices,
            selectedInvestmentCategoryIndices,
            selectedSourceIndices,
            selectedTransactionTypesIndices,
            expenseCategories,
            incomeCategories,
            investmentCategories,
            sources,
            selectedSortOption,
        ) { flows ->
            val allTransactionDetailsValue: List<TransactionDetail> =
                flows[0] as? List<TransactionDetail> ?: emptyList()
            val searchTextValue: String = flows[1] as String
            val selectedExpenseCategoryIndicesValue: List<Int> =
                flows[2] as? List<Int> ?: emptyList()
            val selectedIncomeCategoryIndicesValue: List<Int> =
                flows[3] as? List<Int> ?: emptyList()
            val selectedInvestmentCategoryIndicesValue: List<Int> =
                flows[4] as? List<Int> ?: emptyList()
            val selectedSourceIndicesValue: List<Int> = flows[5] as? List<Int> ?: emptyList()
            val selectedTransactionTypesIndicesValue: List<Int> =
                flows[6] as? List<Int> ?: emptyList()
            val expenseCategoriesValue: List<Category> = flows[7] as? List<Category> ?: emptyList()
            val incomeCategoriesValue: List<Category> = flows[8] as? List<Category> ?: emptyList()
            val investmentCategoriesValue: List<Category> =
                flows[9] as? List<Category> ?: emptyList()
            val sourcesValue: List<Source> = flows[10] as? List<Source> ?: emptyList()
            val selectedSortOptionValue: SortOption =
                flows[11] as? SortOption ?: SortOption.LATEST_FIRST

            allTransactionDetailsValue
                .filter { transactionDetail ->
                    isAvailableAfterSearch(
                        searchTextValue = searchTextValue,
                        transactionDetail = transactionDetail,
                    ) && isAvailableAfterTransactionTypeFilter(
                        selectedTransactionTypesIndicesValue = selectedTransactionTypesIndicesValue,
                        transactionDetail = transactionDetail
                    ) && isAvailableAfterSourceFilter(
                        selectedSourceIndicesValue = selectedSourceIndicesValue,
                        sourcesValue = sourcesValue,
                        transactionDetail = transactionDetail,
                    ) && isAvailableAfterCategoryFilter(
                        selectedExpenseCategoryIndicesValue = selectedExpenseCategoryIndicesValue,
                        selectedIncomeCategoryIndicesValue = selectedIncomeCategoryIndicesValue,
                        selectedInvestmentCategoryIndicesValue = selectedInvestmentCategoryIndicesValue,
                        expenseCategoriesValue = expenseCategoriesValue,
                        transactionDetail = transactionDetail,
                        incomeCategoriesValue = incomeCategoriesValue,
                        investmentCategoriesValue = investmentCategoriesValue,
                    )
                }
                .sortedWith(compareBy {
                    when (selectedSortOptionValue) {
                        SortOption.AMOUNT_ASC -> {
                            it.amount.value
                        }
                        SortOption.AMOUNT_DESC -> {
                            -1 * it.amount.value
                        }
                        SortOption.LATEST_FIRST -> {
                            -1 * it.transactionTimestamp
                        }
                        SortOption.OLDEST_FIRST -> {
                            it.transactionTimestamp
                        }
                    }
                })
                .groupBy {
                    if (selectedSortOptionValue == SortOption.LATEST_FIRST || selectedSortOptionValue == SortOption.OLDEST_FIRST) {
                        getDateString(
                            it.transactionTimestamp
                        )
                    } else {
                        ""
                    }
                }
        }.flowOn(
            context = dispatcherProvider.io,
        )

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }

    // region Search
    override fun updateSearchText(
        updatedSearchText: String,
    ) {
        _searchText.value = updatedSearchText
    }
    // endregion

    // region Filter
    override fun updateSelectedExpenseCategoryIndices(
        updatedSelectedExpenseCategoryIndices: List<Int>,
    ) {
        _selectedExpenseCategoryIndices.value = updatedSelectedExpenseCategoryIndices
    }

    override fun updateSelectedIncomeCategoryIndices(
        updatedSelectedIncomeCategoryIndices: List<Int>,
    ) {
        _selectedIncomeCategoryIndices.value = updatedSelectedIncomeCategoryIndices
    }

    override fun updateSelectedInvestmentCategoryIndices(
        updatedSelectedInvestmentCategoryIndices: List<Int>,
    ) {
        _selectedInvestmentCategoryIndices.value = updatedSelectedInvestmentCategoryIndices
    }

    override fun updateSelectedSourceIndices(
        updatedSelectedSourceIndices: List<Int>,
    ) {
        _selectedSourceIndices.value = updatedSelectedSourceIndices
    }

    override fun updateSelectedTransactionTypesIndices(
        updatedSelectedTransactionTypesIndices: List<Int>,
    ) {
        _selectedTransactionTypesIndices.value = updatedSelectedTransactionTypesIndices
    }
    // endregion

    // region Sort
    override fun updateSelectedSortOption(
        updatedSelectedSortOption: SortOption,
    ) {
        _selectedSortOption.value = updatedSelectedSortOption
    }
    // endregion

    override fun deleteTransaction(
        id: Int,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            deleteTransactionAndRevertOtherDataUseCase(
                id = id,
            )
        }
    }

    private fun isAvailableAfterSearch(
        searchTextValue: String,
        transactionDetail: TransactionDetail,
    ): Boolean {
        return searchTextValue.isBlank() ||
                transactionDetail.title.contains(
                    other = searchTextValue,
                    ignoreCase = true,
                )
    }

    private fun isAvailableAfterTransactionTypeFilter(
        selectedTransactionTypesIndicesValue: List<Int>,
        transactionDetail: TransactionDetail,
    ): Boolean {
        return selectedTransactionTypesIndicesValue.isEmpty() ||
                selectedTransactionTypesIndicesValue.contains(
                    element = transactionTypes.indexOf(
                        element = transactionDetail.transactionType,
                    ),
                )
    }

    private fun isAvailableAfterSourceFilter(
        selectedSourceIndicesValue: List<Int>,
        sourcesValue: List<Source>,
        transactionDetail: TransactionDetail,
    ): Boolean {
        return selectedSourceIndicesValue.isEmpty() ||
                selectedSourceIndicesValue.contains(
                    element = sourcesValue.indexOf(
                        element = transactionDetail.sourceFrom,
                    ),
                ) ||
                selectedSourceIndicesValue.contains(
                    element = sourcesValue.indexOf(
                        element = transactionDetail.sourceTo,
                    ),
                )
    }

    private fun isAvailableAfterCategoryFilter(
        expenseCategoriesValue: List<Category>,
        incomeCategoriesValue: List<Category>,
        investmentCategoriesValue: List<Category>,
        selectedExpenseCategoryIndicesValue: List<Int>,
        selectedIncomeCategoryIndicesValue: List<Int>,
        selectedInvestmentCategoryIndicesValue: List<Int>,
        transactionDetail: TransactionDetail,
    ): Boolean {
        return (selectedExpenseCategoryIndicesValue.isEmpty() &&
                selectedIncomeCategoryIndicesValue.isEmpty() &&
                selectedInvestmentCategoryIndicesValue.isEmpty()) ||
                selectedExpenseCategoryIndicesValue.contains(
                    element = expenseCategoriesValue.indexOf(
                        element = transactionDetail.category,
                    ),
                ) ||
                selectedIncomeCategoryIndicesValue.contains(
                    element = incomeCategoriesValue.indexOf(
                        element = transactionDetail.category,
                    ),
                ) ||
                selectedInvestmentCategoryIndicesValue.contains(
                    element = investmentCategoriesValue.indexOf(
                        element = transactionDetail.category,
                    ),
                )
    }
}
