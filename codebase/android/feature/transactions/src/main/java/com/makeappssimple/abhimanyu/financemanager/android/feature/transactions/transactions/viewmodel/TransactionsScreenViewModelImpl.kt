package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.atEndOfDay
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.getFormattedDate
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.getLocalDate
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.GetAllCategoriesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetAllSourcesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetAllTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase.DeleteTransactionAndRevertOtherDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

@HiltViewModel
internal class TransactionsScreenViewModelImpl @Inject constructor(
    getAllTransactionDataFlowUseCase: GetAllTransactionDataFlowUseCase,
    getAllCategoriesFlowUseCase: GetAllCategoriesFlowUseCase,
    getAllSourcesFlowUseCase: GetAllSourcesFlowUseCase,
    override val logger: Logger,
    override val navigationManager: NavigationManager,
    private val deleteTransactionAndRevertOtherDataUseCase: DeleteTransactionAndRevertOtherDataUseCase,
    private val dispatcherProvider: DispatcherProvider,
) : TransactionsScreenViewModel, ViewModel() {
    private val categories: Flow<List<Category>> = getAllCategoriesFlowUseCase()
    private val allTransactionData: Flow<List<TransactionData>> =
        getAllTransactionDataFlowUseCase()

    // region Search
    private val _searchText = MutableStateFlow(
        value = "",
    )
    override val searchText: StateFlow<String> = _searchText
    // endregion

    // region Filter
    private val _isLoading = MutableStateFlow(
        value = false,
    )
    override val isLoading: StateFlow<Boolean> = _isLoading
    private val _selectedFilter: MutableStateFlow<Filter> = MutableStateFlow(
        value = Filter(),
    )
    override val selectedFilter: StateFlow<Filter> = _selectedFilter
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
    override val sources: Flow<List<Source>> = getAllSourcesFlowUseCase()
    override val transactionTypes: List<TransactionType> = TransactionType.values().toList()

    private var _oldestTransactionTimestamp: MutableStateFlow<Long> = MutableStateFlow(
        value = 0L,
    )
    override val oldestTransactionTimestamp: StateFlow<Long> = _oldestTransactionTimestamp

    override val sortOptions: List<SortOption> = SortOption.values().toList()

    override val transactionDetailsListItemViewData: Flow<Map<String, List<TransactionData>>> =
        combine(
            allTransactionData,
            searchText,
            selectedFilter,
            categories,
            sources,
            selectedSortOption,
        ) { flows ->
            _isLoading.value = true

            val allTransactionDataValue: List<TransactionData> =
                flows[0] as? List<TransactionData> ?: emptyList()
            val searchTextValue: String = flows[1] as String
            val selectedFilterValue: Filter = flows[2] as? Filter ?: Filter()
            val categoriesValue: List<Category> = flows[3] as? List<Category> ?: emptyList()
            val sourcesValue: List<Source> = flows[4] as? List<Source> ?: emptyList()
            val selectedSortOptionValue: SortOption =
                flows[5] as? SortOption ?: SortOption.LATEST_FIRST

            val expenseCategoriesValue = categoriesValue.filter { category ->
                category.transactionType == TransactionType.EXPENSE
            }
            val incomeCategoriesValue = categoriesValue.filter { category ->
                category.transactionType == TransactionType.INCOME
            }
            val investmentCategoriesValue = categoriesValue.filter { category ->
                category.transactionType == TransactionType.INVESTMENT
            }

            if (selectedFilterValue.fromDate.isNull()) {
                _oldestTransactionTimestamp.update {
                    allTransactionDataValue.minOfOrNull { transactionData ->
                        transactionData.transaction.transactionTimestamp
                    } ?: 0L
                }
                updateSelectedFilter(
                    updatedSelectedFilter = selectedFilterValue.copy(
                        fromDate = getLocalDate(
                            timestamp = oldestTransactionTimestamp.value,
                        ),
                    ),
                )
            }

            allTransactionDataValue
                .filter { transactionDetail ->
                    isAvailableAfterSearch(
                        searchTextValue = searchTextValue,
                        transactionData = transactionDetail,
                    ) && isAvailableAfterDateFilter(
                        fromDate = selectedFilterValue.fromDate,
                        toDate = selectedFilterValue.toDate,
                        transactionData = transactionDetail,
                    ) && isAvailableAfterTransactionTypeFilter(
                        selectedTransactionTypesIndicesValue = selectedFilterValue.selectedTransactionTypeIndices,
                        transactionData = transactionDetail
                    ) && isAvailableAfterSourceFilter(
                        selectedSourceIndicesValue = selectedFilterValue.selectedSourceIndices,
                        sourcesValue = sourcesValue,
                        transactionData = transactionDetail,
                    ) && isAvailableAfterCategoryFilter(
                        selectedExpenseCategoryIndicesValue = selectedFilterValue.selectedExpenseCategoryIndices,
                        selectedIncomeCategoryIndicesValue = selectedFilterValue.selectedIncomeCategoryIndices,
                        selectedInvestmentCategoryIndicesValue = selectedFilterValue.selectedInvestmentCategoryIndices,
                        expenseCategoriesValue = expenseCategoriesValue,
                        transactionData = transactionDetail,
                        incomeCategoriesValue = incomeCategoriesValue,
                        investmentCategoriesValue = investmentCategoriesValue,
                    )
                }
                .also {
                    if (it.isEmpty()) {
                        _isLoading.value = false
                    }
                }
                .sortedWith(compareBy {
                    when (selectedSortOptionValue) {
                        SortOption.AMOUNT_ASC -> {
                            it.transaction.amount.value
                        }

                        SortOption.AMOUNT_DESC -> {
                            -1 * it.transaction.amount.value
                        }

                        SortOption.LATEST_FIRST -> {
                            -1 * it.transaction.transactionTimestamp
                        }

                        SortOption.OLDEST_FIRST -> {
                            it.transaction.transactionTimestamp
                        }
                    }
                })
                .also {
                    if (it.isEmpty()) {
                        _isLoading.value = false
                    }
                }
                .groupBy {
                    if (selectedSortOptionValue == SortOption.LATEST_FIRST || selectedSortOptionValue == SortOption.OLDEST_FIRST) {
                        getFormattedDate(
                            timestamp = it.transaction.transactionTimestamp,
                        )
                    } else {
                        ""
                    }
                }
                .also {
                    _isLoading.value = false
                }
        }.flowOn(
            context = dispatcherProvider.io,
        )

    // region Search
    override fun updateSearchText(
        updatedSearchText: String,
    ) {
        _searchText.value = updatedSearchText
    }
    // endregion

    // region Filter
    override fun updateSelectedFilter(
        updatedSelectedFilter: Filter,
    ) {
        _selectedFilter.update {
            updatedSelectedFilter
        }
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
        transactionData: TransactionData,
    ): Boolean {
        return searchTextValue.isBlank() ||
                transactionData.transaction.title.contains(
                    other = searchTextValue,
                    ignoreCase = true,
                ) ||
                transactionData.transaction.amount.value.toString().contains(
                    other = searchTextValue,
                    ignoreCase = true,
                )
    }

    private fun isAvailableAfterDateFilter(
        fromDate: LocalDate?,
        toDate: LocalDate?,
        transactionData: TransactionData,
    ): Boolean {
        if (fromDate.isNull() || toDate.isNull()) {
            return true
        }
        val fromDateStartOfDayTimestamp = fromDate
            .atStartOfDay()
            .toEpochMilli()
        val toDateStartOfDayTimestamp = toDate
            .atEndOfDay()
            .toEpochMilli()
        return transactionData.transaction.transactionTimestamp in (fromDateStartOfDayTimestamp) until toDateStartOfDayTimestamp
    }

    private fun isAvailableAfterTransactionTypeFilter(
        selectedTransactionTypesIndicesValue: List<Int>,
        transactionData: TransactionData,
    ): Boolean {
        return selectedTransactionTypesIndicesValue.isEmpty() ||
                selectedTransactionTypesIndicesValue.contains(
                    element = transactionTypes.indexOf(
                        element = transactionData.transaction.transactionType,
                    ),
                )
    }

    private fun isAvailableAfterSourceFilter(
        selectedSourceIndicesValue: List<Int>,
        sourcesValue: List<Source>,
        transactionData: TransactionData,
    ): Boolean {
        return selectedSourceIndicesValue.isEmpty() ||
                selectedSourceIndicesValue.contains(
                    element = sourcesValue.indexOf(
                        element = transactionData.sourceFrom,
                    ),
                ) ||
                selectedSourceIndicesValue.contains(
                    element = sourcesValue.indexOf(
                        element = transactionData.sourceTo,
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
        transactionData: TransactionData,
    ): Boolean {
        return (selectedExpenseCategoryIndicesValue.isEmpty() &&
                selectedIncomeCategoryIndicesValue.isEmpty() &&
                selectedInvestmentCategoryIndicesValue.isEmpty()) ||
                selectedExpenseCategoryIndicesValue.contains(
                    element = expenseCategoriesValue.indexOf(
                        element = transactionData.category,
                    ),
                ) ||
                selectedIncomeCategoryIndicesValue.contains(
                    element = incomeCategoriesValue.indexOf(
                        element = transactionData.category,
                    ),
                ) ||
                selectedInvestmentCategoryIndicesValue.contains(
                    element = investmentCategoriesValue.indexOf(
                        element = transactionData.category,
                    ),
                )
    }
}
