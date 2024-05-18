package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.EmojiConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.atEndOfDay
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combine
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultListStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetAllTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.UpdateTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetAllTransactionForValuesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.Filter
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.SortOption
import com.makeappssimple.abhimanyu.financemanager.android.core.model.toSignedString
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.getAmountTextColor
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen.TransactionsScreenUIData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
public class TransactionsScreenViewModel @Inject constructor(
    getAllTransactionDataFlowUseCase: GetAllTransactionDataFlowUseCase,
    getAllTransactionForValuesFlowUseCase: GetAllTransactionForValuesFlowUseCase,
    private val dateTimeUtil: DateTimeUtil,
    private val dispatcherProvider: DispatcherProvider,
    private val navigator: Navigator,
    private val updateTransactionsUseCase: UpdateTransactionsUseCase,
) : ScreenViewModel, ViewModel() {
    private val allTransactionData: StateFlow<ImmutableList<TransactionData>> =
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

    private val expenseCategories: StateFlow<List<Category>?> = categoriesMap.map {
        it[TransactionType.EXPENSE].orEmpty()
    }.defaultObjectStateIn(
        scope = viewModelScope,
    )
    private val incomeCategories: StateFlow<List<Category>?> = categoriesMap.map {
        it[TransactionType.INCOME].orEmpty()
    }.defaultObjectStateIn(
        scope = viewModelScope,
    )
    private val investmentCategories: StateFlow<List<Category>?> = categoriesMap.map {
        it[TransactionType.INVESTMENT].orEmpty()
    }.defaultObjectStateIn(
        scope = viewModelScope,
    )
    private val accounts: StateFlow<List<Account>?> = allTransactionData.map {
        it.flatMap { transactionData ->
            listOfNotNull(
                transactionData.accountFrom,
                transactionData.accountTo,
            )
        }.distinct()
    }.defaultObjectStateIn(
        scope = viewModelScope,
    )
    private val transactionForValues: StateFlow<ImmutableList<TransactionFor>> =
        getAllTransactionForValuesFlowUseCase().defaultListStateIn(
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

    private val transactionTypes: ImmutableList<TransactionType> =
        TransactionType.entries.toImmutableList()

    private var oldestTransactionLocalDate: StateFlow<LocalDate?> = allTransactionData.map {
        dateTimeUtil.getLocalDate(
            timestamp = it.minOfOrNull { transactionData ->
                transactionData.transaction.transactionTimestamp
            }.orZero(),
        )
    }.defaultObjectStateIn(
        scope = viewModelScope,
    )

    private val sortOptions: ImmutableList<SortOption> = SortOption.entries.toImmutableList()

    private val transactionDetailsListItemViewData: StateFlow<Map<String, ImmutableList<TransactionListItemData>>?> =
        combine(
            allTransactionData,
            searchText,
            selectedFilter,
            selectedSortOption,
            expenseCategories,
            incomeCategories,
            investmentCategories,
            accounts,
            transactionForValues,
            oldestTransactionLocalDate,
        ) {
                allTransactionDataValue,
                searchTextValue,
                selectedFilterValue,
                selectedSortOptionValue,
                expenseCategoriesValue,
                incomeCategoriesValue,
                investmentCategoriesValue,
                accountsValue,
                transactionForValuesValue,
                oldestTransactionLocalDateValue,
            ->
            isLoading.value = true

            updateSelectedFilter(
                updatedSelectedFilter = selectedFilterValue.copy(
                    fromDate = oldestTransactionLocalDateValue,
                ),
            )

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
                    ) && isAvailableAfterAccountFilter(
                        selectedAccountsIndicesValue = selectedFilterValue.selectedAccountsIndices,
                        accountsValue = accountsValue.orEmpty(),
                        transactionData = transactionData,
                    ) && isAvailableAfterCategoryFilter(
                        selectedExpenseCategoryIndicesValue = selectedFilterValue.selectedExpenseCategoryIndices,
                        selectedIncomeCategoryIndicesValue = selectedFilterValue.selectedIncomeCategoryIndices,
                        selectedInvestmentCategoryIndicesValue = selectedFilterValue.selectedInvestmentCategoryIndices,
                        expenseCategoriesValue = expenseCategoriesValue.orEmpty(),
                        transactionData = transactionData,
                        incomeCategoriesValue = incomeCategoriesValue.orEmpty(),
                        investmentCategoriesValue = investmentCategoriesValue.orEmpty(),
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
                                    isPositive = listItem.accountTo.isNotNull(),
                                    isNegative = listItem.accountFrom.isNotNull(),
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
                        val accountFromName = listItem.accountFrom?.name
                        val accountToName = listItem.accountTo?.name
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
                            accountFromName = accountFromName,
                            accountToName = accountToName,
                            title = title,
                            transactionForText = transactionForText,
                        )
                    }
                    transactionListItemDataList.toImmutableList()
                }
                .also {
                    isLoading.value = false
                }
        }.defaultObjectStateIn(
            scope = viewModelScope,
        )

    public val screenUIData: StateFlow<MyResult<TransactionsScreenUIData>?> = combine(
        isLoading,
        selectedFilter,
        oldestTransactionLocalDate,
        transactionDetailsListItemViewData,
        searchText,
        selectedSortOption,
        transactionForValues,
        selectedTransactions,
        expenseCategories,
        incomeCategories,
        investmentCategories,
        accounts,
    ) {
            isLoading,
            selectedFilter,
            oldestTransactionLocalDate,
            transactionDetailsListItemViewData,
            searchText,
            selectedSortOption,
            transactionForValuesValue,
            selectedTransactionsValue,
            expenseCategoriesValue,
            incomeCategoriesValue,
            investmentCategoriesValue,
            accountsValue,
        ->
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
                    accounts = accountsValue?.toImmutableList().orEmpty(),
                    expenseCategories = expenseCategoriesValue?.toImmutableList().orEmpty(),
                    incomeCategories = incomeCategoriesValue?.toImmutableList().orEmpty(),
                    investmentCategories = investmentCategoriesValue?.toImmutableList().orEmpty(),
                    selectedTransactions = selectedTransactionsValue.toImmutableList().orEmpty(),
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
    public fun updateSearchText(
        updatedSearchText: String,
    ) {
        searchText.value = updatedSearchText
    }
    // endregion

    // region Filter
    public fun updateSelectedFilter(
        updatedSelectedFilter: Filter,
    ) {
        selectedFilter.update {
            updatedSelectedFilter
        }
    }
    // endregion

    // region Sort
    public fun updateSelectedSortOption(
        updatedSelectedSortOption: SortOption,
    ) {
        selectedSortOption.value = updatedSelectedSortOption
    }
    // endregion

    public fun addToSelectedTransactions(
        transactionId: Int,
    ) {
        selectedTransactions.update {
            it + transactionId
        }
    }

    public fun clearSelectedTransactions() {
        selectedTransactions.update {
            emptyList()
        }
    }

    public fun navigateToAddTransactionScreen() {
        navigator.navigateToAddTransactionScreen()
    }

    public fun navigateToViewTransactionScreen(
        transactionId: Int,
    ) {
        navigator.navigateToViewTransactionScreen(
            transactionId = transactionId,
        )
    }

    public fun navigateUp() {
        navigator.navigateUp()
    }

    public fun removeFromSelectedTransactions(
        transactionId: Int,
    ) {
        selectedTransactions.update {
            it - transactionId
        }
    }

    public fun selectAllTransactions() {
        selectedTransactions.update {
            transactionDetailsListItemViewData.value?.values?.flatMap {
                it.map { transactionListItemData ->
                    transactionListItemData.transactionId
                }
            }.orEmpty()
        }
    }

    public fun updateTransactionForValuesInTransactions(
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
            updateTransactionsUseCase(
                transactions = updatedTransactions.toTypedArray(),
            )
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

    private fun isAvailableAfterAccountFilter(
        selectedAccountsIndicesValue: List<Int>,
        accountsValue: List<Account>,
        transactionData: TransactionData,
    ): Boolean {
        if (selectedAccountsIndicesValue.isEmpty()) {
            return true
        }
        return selectedAccountsIndicesValue.contains(
            element = accountsValue.indexOf(
                element = transactionData.accountFrom,
            ),
        ) || selectedAccountsIndicesValue.contains(
            element = accountsValue.indexOf(
                element = transactionData.accountTo,
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
