package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.EmojiConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.atEndOfDay
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.distinct
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orMin
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultListStateIn
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
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.areFiltersSelected
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.orDefault
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.model.toSignedString
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.getAmountTextColor
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.bottomsheet.TransactionsScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.state.TransactionsScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.state.TransactionsScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.state.TransactionsScreenUIStateEvents
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
    private val navigator: Navigator,
    private val updateTransactionsUseCase: UpdateTransactionsUseCase,
) : ScreenViewModel, ViewModel() {
    private val allTransactionData: StateFlow<ImmutableList<TransactionData>> =
        getAllTransactionDataFlowUseCase().defaultListStateIn(
            scope = viewModelScope,
        )
    private val categoriesMap: Flow<Map<TransactionType, ImmutableList<Category>>> =
        allTransactionData.map {
            it.mapNotNull { transactionData ->
                transactionData.category
            }.groupBy { category ->
                category.transactionType
            }.mapValues { (_, categories) ->
                categories.distinct()
            }.toMap()
        }

    private val selectedTransactionIndices: MutableStateFlow<MutableList<Int>> = MutableStateFlow(
        value = mutableListOf(),
    )
    private val transactionDetailsListItemViewData: MutableStateFlow<Map<String, ImmutableList<TransactionListItemData>>> =
        MutableStateFlow(
            value = mutableMapOf(),
        )
    private val expenseCategories: StateFlow<ImmutableList<Category>?> = categoriesMap.map {
        it[TransactionType.EXPENSE].orEmpty()
    }.defaultListStateIn(
        scope = viewModelScope,
    )
    private val incomeCategories: StateFlow<ImmutableList<Category>?> = categoriesMap.map {
        it[TransactionType.INCOME].orEmpty()
    }.defaultListStateIn(
        scope = viewModelScope,
    )
    private val investmentCategories: StateFlow<ImmutableList<Category>?> = categoriesMap.map {
        it[TransactionType.INVESTMENT].orEmpty()
    }.defaultListStateIn(
        scope = viewModelScope,
    )
    private val accounts: StateFlow<ImmutableList<Account>?> = allTransactionData.map {
        it.flatMap { transactionData ->
            listOfNotNull(
                transactionData.accountFrom,
                transactionData.accountTo,
            )
        }.distinct()
    }.defaultListStateIn(
        scope = viewModelScope,
    )
    private val transactionForValues: StateFlow<ImmutableList<TransactionFor>> =
        getAllTransactionForValuesFlowUseCase().defaultListStateIn(
            scope = viewModelScope,
        )
    private val transactionTypes: ImmutableList<TransactionType> =
        TransactionType.entries.toImmutableList()
    private var oldestTransactionLocalDate: Flow<LocalDate?> = allTransactionData.map {
        dateTimeUtil.getLocalDate(
            timestamp = it.minOfOrNull { transactionData ->
                transactionData.transaction.transactionTimestamp
            }.orZero(),
        )
    }
    private val sortOptions: ImmutableList<SortOption> = SortOption.entries.toImmutableList()
    private val currentLocalDate: LocalDate = dateTimeUtil.getCurrentLocalDate()

    // region UI data
    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    private val isInSelectionMode: MutableStateFlow<Boolean> = MutableStateFlow(
        value = false,
    )
    private val searchText: MutableStateFlow<String> = MutableStateFlow(
        value = "",
    )
    private val selectedFilter: MutableStateFlow<Filter> = MutableStateFlow(
        value = Filter(),
    )
    private val selectedSortOption: MutableStateFlow<SortOption> = MutableStateFlow(
        value = SortOption.LATEST_FIRST,
    )
    private val screenBottomSheetType: MutableStateFlow<TransactionsScreenBottomSheetType> =
        MutableStateFlow(
            value = TransactionsScreenBottomSheetType.None,
        )
    // endregion

    internal val uiStateAndStateEvents: MutableStateFlow<TransactionsScreenUIStateAndStateEvents> =
        MutableStateFlow(
            value = TransactionsScreenUIStateAndStateEvents(),
        )

    internal fun initViewModel() {
        fetchData()
        observeData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            isLoading.update {
                false
            }
        }
    }

    private fun observeData() {
        observeForTransactionDetailsListItemViewData()
        observeForUiStateAndStateEventsChanges()
    }

    private fun observeForTransactionDetailsListItemViewData() {
        viewModelScope.launch {
            combineAndCollectLatest(
                transactionForValues,
                searchText,
                selectedFilter,
                accounts,
                expenseCategories,
                incomeCategories,
                investmentCategories,
                selectedSortOption,
            ) {
                    (
                        transactionForValues,
                        searchText,
                        selectedFilter,
                        accounts,
                        expenseCategories,
                        incomeCategories,
                        investmentCategories,
                        selectedSortOption,
                    ),
                ->
                transactionDetailsListItemViewData.update {
                    allTransactionData.value
                        .filter { transactionData ->
                            isAvailableAfterSearch(
                                searchTextValue = searchText,
                                transactionData = transactionData,
                            ) && isAvailableAfterDateFilter(
                                fromDate = selectedFilter.fromDate,
                                toDate = selectedFilter.toDate,
                                transactionData = transactionData,
                            ) && isAvailableAfterTransactionForFilter(
                                selectedTransactionForValuesIndices = selectedFilter.selectedTransactionForValuesIndices,
                                transactionData = transactionData,
                                transactionForValuesValue = transactionForValues,
                            ) && isAvailableAfterTransactionTypeFilter(
                                transactionTypes = transactionTypes,
                                selectedTransactionTypesIndicesValue = selectedFilter.selectedTransactionTypeIndices,
                                transactionData = transactionData,
                            ) && isAvailableAfterAccountFilter(
                                selectedAccountsIndicesValue = selectedFilter.selectedAccountsIndices,
                                accountsValue = accounts.orEmpty(),
                                transactionData = transactionData,
                            ) && isAvailableAfterCategoryFilter(
                                selectedExpenseCategoryIndicesValue = selectedFilter.selectedExpenseCategoryIndices,
                                selectedIncomeCategoryIndicesValue = selectedFilter.selectedIncomeCategoryIndices,
                                selectedInvestmentCategoryIndicesValue = selectedFilter.selectedInvestmentCategoryIndices,
                                expenseCategoriesValue = expenseCategories.orEmpty(),
                                transactionData = transactionData,
                                incomeCategoriesValue = incomeCategories.orEmpty(),
                                investmentCategoriesValue = investmentCategories.orEmpty(),
                            )
                        }
                        .also {
                            if (it.isEmpty()) {
                                isLoading.update {
                                    false
                                }
                            }
                        }
                        .sortedWith(compareBy {
                            when (selectedSortOption) {
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
                                isLoading.update {
                                    false
                                }
                            }
                        }
                        .groupBy {
                            if (selectedSortOption == SortOption.LATEST_FIRST ||
                                selectedSortOption == SortOption.OLDEST_FIRST
                            ) {
                                dateTimeUtil.getFormattedDate(
                                    timestamp = it.transaction.transactionTimestamp,
                                )
                            } else {
                                ""
                            }
                        }
                        .mapValues {
                            it.value.map { listItem ->
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
                        }
                        .also {
                            isLoading.update {
                                false
                            }
                        }
                }
            }
        }
    }

    private fun observeForUiStateAndStateEventsChanges() {
        viewModelScope.launch {
            combineAndCollectLatest(
                isLoading,
                screenBottomSheetType,
                transactionForValues,
                isInSelectionMode,
                searchText,
                selectedFilter,
                accounts,
                oldestTransactionLocalDate,
                selectedSortOption,
                selectedTransactionIndices,
                transactionDetailsListItemViewData,
            ) {
                    (
                        isLoading,
                        screenBottomSheetType,
                        transactionForValues,
                        isInSelectionMode,
                        searchText,
                        selectedFilter,
                        accounts,
                        oldestTransactionLocalDate,
                        selectedSortOption,
                        selectedTransactionIndices,
                        transactionDetailsListItemViewData,
                    ),
                ->

                uiStateAndStateEvents.update {
                    TransactionsScreenUIStateAndStateEvents(
                        state = TransactionsScreenUIState(
                            isBottomSheetVisible = screenBottomSheetType != TransactionsScreenBottomSheetType.None,
                            isInSelectionMode = isInSelectionMode,
                            isLoading = isLoading,
                            isSearchSortAndFilterVisible = isInSelectionMode.not() && (
                                    transactionDetailsListItemViewData.isNotEmpty() ||
                                            searchText.isNotEmpty() ||
                                            selectedFilter.orEmpty().areFiltersSelected()
                                    ),
                            selectedFilter = selectedFilter.orEmpty(),
                            selectedTransactions = selectedTransactionIndices.toImmutableList(),
                            sortOptions = sortOptions.orEmpty(),
                            transactionForValues = transactionForValues.orEmpty(),
                            accounts = accounts.orEmpty(),
                            expenseCategories = expenseCategories.value.orEmpty(),
                            incomeCategories = incomeCategories.value.orEmpty(),
                            investmentCategories = investmentCategories.value.orEmpty(),
                            transactionTypes = transactionTypes.orEmpty(),
                            currentLocalDate = currentLocalDate.orMin(),
                            oldestTransactionLocalDate = oldestTransactionLocalDate.orMin(),
                            transactionDetailsListItemViewData = transactionDetailsListItemViewData,
                            selectedSortOption = selectedSortOption.orDefault(),
                            searchText = searchText,
                            screenBottomSheetType = screenBottomSheetType,
                        ),
                        events = TransactionsScreenUIStateEvents(
                            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
                            setIsInSelectionMode = ::setIsInSelectionMode,
                            setScreenBottomSheetType = ::setScreenBottomSheetType,
                            navigateUp = ::navigateUp,
                            setSearchText = ::setSearchText,
                            setSelectedFilter = ::setSelectedFilter,
                            setSelectedSortOption = ::setSelectedSortOption,
                            addToSelectedTransactions = ::addToSelectedTransactions,
                            removeFromSelectedTransactions = ::removeFromSelectedTransactions,
                            clearSelectedTransactions = ::clearSelectedTransactions,
                            selectAllTransactions = ::selectAllTransactions,
                            navigateToAddTransactionScreen = ::navigateToAddTransactionScreen,
                            navigateToViewTransactionScreen = ::navigateToViewTransactionScreen,
                            updateTransactionForValuesInTransactions = ::updateTransactionForValuesInTransactions,
                        ),
                    )
                }
            }
        }
    }

    private fun navigateToAddTransactionScreen() {
        navigator.navigateToAddTransactionScreen()
    }

    private fun navigateToViewTransactionScreen(
        transactionId: Int,
    ) {
        navigator.navigateToViewTransactionScreen(
            transactionId = transactionId,
        )
    }

    private fun updateTransactionForValuesInTransactions(
        selectedTransactions: ImmutableList<Int>,
        transactionForId: Int,
    ) {
        viewModelScope.launch {
            val updatedTransactions = allTransactionData.value.map { transactionData ->
                transactionData.transaction
            }.filter {
                it.transactionType == TransactionType.EXPENSE &&
                        selectedTransactions.contains(it.id)
            }.map {
                it.copy(
                    transactionForId = transactionForId,
                )
            }
            updateTransactionsUseCase(
                transactions = updatedTransactions.toTypedArray(),
            )
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
        selectedTransactionForValuesIndices: ImmutableList<Int>,
        transactionData: TransactionData,
        transactionForValuesValue: ImmutableList<TransactionFor>,
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
        transactionTypes: ImmutableList<TransactionType>,
        selectedTransactionTypesIndicesValue: ImmutableList<Int>,
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
        selectedAccountsIndicesValue: ImmutableList<Int>,
        accountsValue: ImmutableList<Account>,
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
        expenseCategoriesValue: ImmutableList<Category>,
        incomeCategoriesValue: ImmutableList<Category>,
        investmentCategoriesValue: ImmutableList<Category>,
        selectedExpenseCategoryIndicesValue: ImmutableList<Int>,
        selectedIncomeCategoryIndicesValue: ImmutableList<Int>,
        selectedInvestmentCategoryIndicesValue: ImmutableList<Int>,
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

    // region state events
    private fun navigateUp() {
        navigator.navigateUp()
    }

    private fun addToSelectedTransactions(
        transactionId: Int,
    ) {
        selectedTransactionIndices.update {
            it.apply {
                add(transactionId)
            }
        }
    }

    private fun removeFromSelectedTransactions(
        transactionId: Int,
    ) {
        selectedTransactionIndices.update {
            it.apply {
                remove(transactionId)
            }
        }
    }

    private fun clearSelectedTransactions() {
        selectedTransactionIndices.update {
            mutableListOf()
        }
    }

    private fun selectAllTransactions() {
        selectedTransactionIndices.update {
            transactionDetailsListItemViewData.value.values.flatMap {
                it.map { transactionListItemData ->
                    transactionListItemData.transactionId
                }
            }.toMutableList()
        }
    }

    private fun resetScreenBottomSheetType() {
        setScreenBottomSheetType(
            updatedTransactionsScreenBottomSheetType = TransactionsScreenBottomSheetType.None,
        )
    }

    private fun setScreenBottomSheetType(
        updatedTransactionsScreenBottomSheetType: TransactionsScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedTransactionsScreenBottomSheetType
        }
    }

    private fun setIsInSelectionMode(
        updatedIsInSelectionMode: Boolean,
    ) {
        isInSelectionMode.update {
            updatedIsInSelectionMode
        }
    }

    private fun setSearchText(
        updatedSearchText: String,
    ) {
        searchText.update {
            updatedSearchText
        }
    }

    private fun setSelectedFilter(
        updatedSelectedFilter: Filter,
    ) {
        selectedFilter.update {
            updatedSelectedFilter
        }
    }

    private fun setSelectedSortOption(
        updatedSelectedSortOption: SortOption,
    ) {
        selectedSortOption.update {
            updatedSelectedSortOption
        }
    }
    // endregion
}
