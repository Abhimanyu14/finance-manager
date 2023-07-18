package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.EmojiConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.atEndOfDay
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultListStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetAllTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.UpdateTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.transaction_list_item.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.getAmountTextColor
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen.TransactionsScreenUIData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
internal class TransactionsScreenViewModelImpl @Inject constructor(
    getAllTransactionDataFlowUseCase: GetAllTransactionDataFlowUseCase,
    override val myLogger: MyLogger,
    private val dateTimeUtil: DateTimeUtil,
    private val dispatcherProvider: DispatcherProvider,
    private val navigationManager: NavigationManager,
    private val updateTransactionsUseCase: UpdateTransactionsUseCase,
) : TransactionsScreenViewModel, ViewModel() {
    private val allTransactionData: StateFlow<List<TransactionData>> =
        getAllTransactionDataFlowUseCase().defaultListStateIn(
            scope = viewModelScope,
        )
    private val categoriesMap: Flow<Map<TransactionType, List<Category>>> =
        allTransactionData.map {
            it.mapNotNull { transactionData ->
                transactionData.category
            }.groupBy { category ->
                category.transactionType
            }.mapValues { (_, categories) ->
                categories.distinct()
            }.toMap()
        }

    override val expenseCategories: StateFlow<List<Category>?> = categoriesMap.map {
        it[TransactionType.EXPENSE].orEmpty()
    }.defaultObjectStateIn(
        scope = viewModelScope,
    )
    override val incomeCategories: StateFlow<List<Category>?> = categoriesMap.map {
        it[TransactionType.INCOME].orEmpty()
    }.defaultObjectStateIn(
        scope = viewModelScope,
    )
    override val investmentCategories: StateFlow<List<Category>?> = categoriesMap.map {
        it[TransactionType.INVESTMENT].orEmpty()
    }.defaultObjectStateIn(
        scope = viewModelScope,
    )
    override val sources: StateFlow<List<Source>?> = allTransactionData.map {
        it.flatMap { transactionData ->
            listOfNotNull(
                transactionData.sourceFrom,
                transactionData.sourceTo,
            )
        }.distinct()
    }.defaultObjectStateIn(
        scope = viewModelScope,
    )
    override val transactionForValues: StateFlow<List<TransactionFor>?> = allTransactionData.map {
        it.map { transactionData ->
            transactionData.transactionFor
        }.distinct()
    }.defaultObjectStateIn(
        scope = viewModelScope,
    )

    // region Search
    private val searchText = MutableStateFlow(
        value = "",
    )
    // endregion

    // region Filter
    private val isLoading = MutableStateFlow(
        value = false,
    )
    private val selectedFilter: MutableStateFlow<Filter> = MutableStateFlow(
        value = Filter(),
    )
    // endregion

    // region Sort
    private val selectedSortOption = MutableStateFlow(
        value = SortOption.LATEST_FIRST,
    )
    // endregion

    private val selectedTransactions: MutableStateFlow<List<Int>> = MutableStateFlow(
        value = emptyList(),
    )

    private val transactionTypes: List<TransactionType> = TransactionType.values().toList()

    private var oldestTransactionLocalDate: MutableStateFlow<LocalDate> = MutableStateFlow(
        value = LocalDate.MIN,
    )

    private val sortOptions: List<SortOption> = SortOption.values().toList()

    private val transactionDetailsListItemViewData: Flow<Map<String, List<TransactionListItemData>>> =
        combine(
            allTransactionData,
            searchText,
            selectedFilter,
            selectedSortOption,
            expenseCategories,
            incomeCategories,
            investmentCategories,
            sources,
            transactionForValues,
        ) { flows ->
            isLoading.value = true

            val allTransactionDataValue: List<TransactionData> =
                flows[0] as? List<TransactionData> ?: emptyList()
            val searchTextValue: String = flows[1] as String
            val selectedFilterValue: Filter = flows[2] as? Filter ?: Filter()
            val selectedSortOptionValue: SortOption =
                flows[3] as? SortOption ?: SortOption.LATEST_FIRST
            val expenseCategoriesValue: List<Category> = flows[4] as? List<Category> ?: emptyList()
            val incomeCategoriesValue: List<Category> = flows[5] as? List<Category> ?: emptyList()
            val investmentCategoriesValue: List<Category> =
                flows[6] as? List<Category> ?: emptyList()
            val sourcesValue: List<Source> = flows[7] as? List<Source> ?: emptyList()
            val transactionForValuesValue: List<TransactionFor> =
                flows[8] as? List<TransactionFor> ?: emptyList()

            if (selectedFilterValue.fromDate.isNull()) {
                oldestTransactionLocalDate.update {
                    dateTimeUtil.getLocalDate(
                        timestamp = allTransactionDataValue.minOfOrNull { transactionData ->
                            transactionData.transaction.transactionTimestamp
                        }.orZero(),
                    )
                }
                updateSelectedFilter(
                    updatedSelectedFilter = selectedFilterValue.copy(
                        fromDate = oldestTransactionLocalDate.value,
                    ),
                )
            }

            allTransactionDataValue
                .filter { transactionData ->
                    isAvailableAfterSearch(
                        searchTextValue = searchTextValue,
                        transactionData = transactionData,
                    ) && isAvailableAfterDateFilter(
                        fromDate = selectedFilterValue.fromDate,
                        toDate = selectedFilterValue.toDate,
                        transactionData = transactionData,
                    ) && isAvailableAfterTransactionForFilter(
                        selectedTransactionForValuesIndices = selectedFilterValue.selectedTransactionForValuesIndices,
                        transactionData = transactionData,
                        transactionForValuesValue = transactionForValuesValue,
                    ) && isAvailableAfterTransactionTypeFilter(
                        selectedTransactionTypesIndicesValue = selectedFilterValue.selectedTransactionTypeIndices,
                        transactionData = transactionData,
                    ) && isAvailableAfterSourceFilter(
                        selectedSourceIndicesValue = selectedFilterValue.selectedSourceIndices,
                        sourcesValue = sourcesValue,
                        transactionData = transactionData,
                    ) && isAvailableAfterCategoryFilter(
                        selectedExpenseCategoryIndicesValue = selectedFilterValue.selectedExpenseCategoryIndices,
                        selectedIncomeCategoryIndicesValue = selectedFilterValue.selectedIncomeCategoryIndices,
                        selectedInvestmentCategoryIndicesValue = selectedFilterValue.selectedInvestmentCategoryIndices,
                        expenseCategoriesValue = expenseCategoriesValue,
                        transactionData = transactionData,
                        incomeCategoriesValue = incomeCategoriesValue,
                        investmentCategoriesValue = investmentCategoriesValue,
                    )
                }
                .also {
                    if (it.isEmpty()) {
                        isLoading.value = false
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
                        isLoading.value = false
                    }
                }
                .groupBy {
                    if (selectedSortOptionValue == SortOption.LATEST_FIRST ||
                        selectedSortOptionValue == SortOption.OLDEST_FIRST
                    ) {
                        dateTimeUtil.getFormattedDate(
                            timestamp = it.transaction.transactionTimestamp,
                        )
                    } else {
                        ""
                    }
                }
                .mapValues {
                    val transactionListItemDataList = it.value.map { listItem ->
                        val amountColor = listItem.transaction.getAmountTextColor()
                        val amountText =
                            if (listItem.transaction.transactionType == TransactionType.INCOME ||
                                listItem.transaction.transactionType == TransactionType.EXPENSE ||
                                listItem.transaction.transactionType == TransactionType.ADJUSTMENT ||
                                listItem.transaction.transactionType == TransactionType.REFUND
                            ) {
                                listItem.transaction.amount.toSignedString(
                                    isPositive = listItem.sourceTo.isNotNull(),
                                    isNegative = listItem.sourceFrom.isNotNull(),
                                )
                            } else {
                                listItem.transaction.amount.toString()
                            }
                        val dateAndTimeText = dateTimeUtil.getReadableDateAndTime(
                            timestamp = listItem.transaction.transactionTimestamp,
                        )
                        val emoji = when (listItem.transaction.transactionType) {
                            TransactionType.TRANSFER -> {
                                EmojiConstants.LEFT_RIGHT_ARROW
                            }

                            TransactionType.ADJUSTMENT -> {
                                EmojiConstants.EXPRESSIONLESS_FACE
                            }

                            else -> {
                                listItem.category?.emoji
                            }
                        }.orEmpty()
                        val sourceFromName = listItem.sourceFrom?.name
                        val sourceToName = listItem.sourceTo?.name
                        val title = listItem.transaction.title
                        val transactionForText = listItem.transactionFor.titleToDisplay

                        TransactionListItemData(
                            isDeleteButtonEnabled = false,
                            isDeleteButtonVisible = true,
                            isEditButtonVisible = false,
                            isExpanded = false,
                            isRefundButtonVisible = false,
                            transactionId = listItem.transaction.id,
                            amountColor = amountColor,
                            amountText = amountText,
                            dateAndTimeText = dateAndTimeText,
                            emoji = emoji,
                            sourceFromName = sourceFromName,
                            sourceToName = sourceToName,
                            title = title,
                            transactionForText = transactionForText,
                        )
                    }
                    transactionListItemDataList
                }
                .also {
                    isLoading.value = false
                }
        }.flowOn(
            context = dispatcherProvider.io,
        )

    override val screenUIData: StateFlow<MyResult<TransactionsScreenUIData>?> = combine(
        isLoading,
        selectedFilter,
        oldestTransactionLocalDate,
        transactionDetailsListItemViewData,
        searchText,
        selectedSortOption,
        transactionForValues,
        selectedTransactions,
    ) { flows ->
        val isLoading = flows[0] as? Boolean
        val selectedFilter = flows[1] as? Filter
        val oldestTransactionLocalDate = flows[2] as? LocalDate
        val transactionDetailsListItemViewData =
            flows[3] as? Map<String, List<TransactionListItemData>>
        val searchText = flows[4] as? String
        val selectedSortOption = flows[5] as? SortOption
        val transactionForValuesValue: List<TransactionFor> =
            flows[6] as? List<TransactionFor> ?: emptyList()
        val selectedTransactionsValue: List<Int> =
            flows[7] as? List<Int> ?: emptyList()

        if (
            isLoading.isNull() ||
            selectedFilter.isNull() ||
            oldestTransactionLocalDate.isNull() ||
            transactionDetailsListItemViewData.isNull() ||
            searchText.isNull() ||
            selectedSortOption.isNull()
        ) {
            MyResult.Loading
        } else {
            MyResult.Success(
                data = TransactionsScreenUIData(
                    isLoading = isLoading,
                    selectedFilter = selectedFilter,
                    selectedTransactions = selectedTransactionsValue,
                    sortOptions = sortOptions,
                    transactionTypes = transactionTypes,
                    transactionForValues = transactionForValuesValue,
                    oldestTransactionLocalDate = oldestTransactionLocalDate,
                    currentLocalDate = dateTimeUtil.getCurrentLocalDate(),
                    currentTimeMillis = dateTimeUtil.getCurrentTimeMillis(),
                    transactionDetailsListItemViewData = transactionDetailsListItemViewData,
                    searchText = searchText,
                    selectedSortOption = selectedSortOption,
                ),
            )
        }
    }.defaultObjectStateIn(
        scope = viewModelScope,
    )

    // region Search
    override fun updateSearchText(
        updatedSearchText: String,
    ) {
        searchText.value = updatedSearchText
    }
    // endregion

    // region Filter
    override fun updateSelectedFilter(
        updatedSelectedFilter: Filter,
    ) {
        selectedFilter.update {
            updatedSelectedFilter
        }
    }
    // endregion

    // region Sort
    override fun updateSelectedSortOption(
        updatedSelectedSortOption: SortOption,
    ) {
        selectedSortOption.value = updatedSelectedSortOption
    }
    // endregion

    override fun addToSelectedTransactions(
        transactionId: Int,
    ) {
        selectedTransactions.update {
            it + transactionId
        }
    }

    override fun clearSelectedTransactions() {
        selectedTransactions.update {
            emptyList()
        }
    }

    override fun navigateToAddTransactionScreen() {
        navigationManager.navigate(
            MyNavigationDirections.AddTransaction()
        )
    }

    override fun navigateToViewTransactionScreen(
        transactionId: Int,
    ) {
        navigationManager.navigate(
            MyNavigationDirections.ViewTransaction(
                transactionId = transactionId,
            )
        )
    }

    override fun navigateUp() {
        navigationManager.navigate(
            MyNavigationDirections.NavigateUp
        )
    }

    override fun removeFromSelectedTransactions(
        transactionId: Int,
    ) {
        selectedTransactions.update {
            it - transactionId
        }
    }

    override fun toggleTransactionSelection(
        transactionId: Int,
    ) {
        if (selectedTransactions.value.contains(transactionId)) {
            selectedTransactions.update {
                it - transactionId
            }
        } else {
            selectedTransactions.update {
                it + transactionId
            }
        }
    }

    override fun updateTransactionForValuesInTransactions(
        transactionForId: Int,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            val updatedTransactions = allTransactionData.value.map { transactionData ->
                transactionData.transaction
            }.filter {
                it.transactionType == TransactionType.EXPENSE &&
                        selectedTransactions.value.contains(it.id)
            }.map {
                it.copy(
                    transactionForId = transactionForId,
                )
            }
            updateTransactionsUseCase(*updatedTransactions.toTypedArray())
            clearSelectedTransactions()
        }
    }

    private fun isAvailableAfterSearch(
        searchTextValue: String,
        transactionData: TransactionData,
    ): Boolean {
        if (searchTextValue.isBlank()) {
            return true
        }
        return transactionData.transaction.title.contains(
            other = searchTextValue,
            ignoreCase = true,
        ) || transactionData.transaction.amount.value.toString().contains(
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

    private fun isAvailableAfterTransactionForFilter(
        selectedTransactionForValuesIndices: List<Int>,
        transactionData: TransactionData,
        transactionForValuesValue: List<TransactionFor>,
    ): Boolean {
        if (selectedTransactionForValuesIndices.isEmpty()) {
            return true
        }
        return selectedTransactionForValuesIndices.contains(
            element = transactionForValuesValue.indexOf(
                element = transactionData.transactionFor,
            ),
        )
    }

    private fun isAvailableAfterTransactionTypeFilter(
        selectedTransactionTypesIndicesValue: List<Int>,
        transactionData: TransactionData,
    ): Boolean {
        if (selectedTransactionTypesIndicesValue.isEmpty()) {
            return true
        }
        return selectedTransactionTypesIndicesValue.contains(
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
        if (selectedSourceIndicesValue.isEmpty()) {
            return true
        }
        return selectedSourceIndicesValue.contains(
            element = sourcesValue.indexOf(
                element = transactionData.sourceFrom,
            ),
        ) || selectedSourceIndicesValue.contains(
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
        if (selectedExpenseCategoryIndicesValue.isEmpty() &&
            selectedIncomeCategoryIndicesValue.isEmpty() &&
            selectedInvestmentCategoryIndicesValue.isEmpty()
        ) {
            return true
        }
        return selectedExpenseCategoryIndicesValue.contains(
            element = expenseCategoriesValue.indexOf(
                element = transactionData.category,
            ),
        ) || selectedIncomeCategoryIndicesValue.contains(
            element = incomeCategoriesValue.indexOf(
                element = transactionData.category,
            ),
        ) || selectedInvestmentCategoryIndicesValue.contains(
            element = investmentCategoriesValue.indexOf(
                element = transactionData.category,
            ),
        )
    }
}
