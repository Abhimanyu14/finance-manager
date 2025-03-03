package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel

import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.perf.metrics.Trace
import com.google.firebase.perf.performance
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.ApplicationScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeKit
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.atEndOfDay
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orMin
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetAllTransactionDataFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.UpdateTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LogKit
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.SortOption
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.areFiltersSelected
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.orDefault
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.toTransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.bottomsheet.TransactionsScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.state.TransactionsScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.state.TransactionsScreenUIStateEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Long.min
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
internal class TransactionsScreenViewModel @Inject constructor(
    @ApplicationScope coroutineScope: CoroutineScope,
    private val dispatcherProvider: DispatcherProvider,
    private val dateTimeKit: DateTimeKit,
    private val getAllTransactionDataFlowUseCase: GetAllTransactionDataFlowUseCase,
    private val getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
    private val navigationKit: NavigationKit,
    private val updateTransactionsUseCase: UpdateTransactionsUseCase,
    internal val logKit: LogKit,
) : ScreenViewModel(
    viewModelScope = coroutineScope,
), TransactionsScreenUIStateDelegate by TransactionsScreenUIStateDelegateImpl(
    coroutineScope = coroutineScope,
    navigationKit = navigationKit,
    updateTransactionsUseCase = updateTransactionsUseCase,
) {
    // region initial data
    private var isInitialDataFetchCompleted = false
    private var performanceScreenInitTrace: Trace? = null

    private var categoriesMap: Map<TransactionType, MutableSet<Category>> = mapOf()
    private var accounts: MutableSet<Account> = mutableSetOf()
    private var oldestTransactionLocalDate: LocalDate? = null
    private var allTransactionForValues: ImmutableList<TransactionFor> = persistentListOf()

    private val transactionTypes: ImmutableList<TransactionType> =
        TransactionType.entries.toImmutableList()
    private val sortOptions: ImmutableList<SortOption> = SortOption.entries.toImmutableList()
    private val currentLocalDate: LocalDate = dateTimeKit.getCurrentLocalDate()
    // endregion

    // region uiStateAndStateEvents
    internal val uiState: MutableStateFlow<TransactionsScreenUIState> =
        MutableStateFlow(
            value = TransactionsScreenUIState(),
        )
    internal val uiStateEvents: TransactionsScreenUIStateEvents = TransactionsScreenUIStateEvents(
        addToSelectedTransactions = ::addToSelectedTransactions,
        clearSelectedTransactions = ::clearSelectedTransactions,
        navigateToAddTransactionScreen = ::navigateToAddTransactionScreen,
        navigateToViewTransactionScreen = ::navigateToViewTransactionScreen,
        navigateUp = ::navigateUp,
        removeFromSelectedTransactions = ::removeFromSelectedTransactions,
        resetScreenBottomSheetType = ::resetScreenBottomSheetType,
        selectAllTransactions = ::selectAllTransactions,
        updateIsInSelectionMode = ::updateIsInSelectionMode,
        updateScreenBottomSheetType = ::updateScreenBottomSheetType,
        updateSearchText = ::updateSearchText,
        updateSelectedFilter = ::updateSelectedFilter,
        updateSelectedSortOption = ::updateSelectedSortOption,
        updateTransactionForValuesInTransactions = ::updateTransactionForValuesInTransactions,
    )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        startTrackingScreenInit()
        observeData()
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            withLoadingSuspend {
                allTransactionForValues = getAllTransactionForValuesUseCase()
            }
        }
    }

    private fun observeData() {
        observeForUiStateAndStateEvents()
        observeForTransactionDetailsListItemViewData()
        observeForAllTransactionData()
    }
    // endregion

    // region observeForUiStateAndStateEvents
    private fun observeForUiStateAndStateEvents() {
        viewModelScope.launch {
            combineAndCollectLatest(
                isLoading,
                screenBottomSheetType,
                isInSelectionMode,
                searchText,
                selectedFilter,
                selectedSortOption,
                selectedTransactionIndices,
                transactionDetailsListItemViewData,
            ) {
                    (
                        isLoading,
                        screenBottomSheetType,
                        isInSelectionMode,
                        searchText,
                        selectedFilter,
                        selectedSortOption,
                        selectedTransactionIndices,
                        transactionDetailsListItemViewData,
                    ),
                ->
                if (!isInitialDataFetchCompleted && transactionDetailsListItemViewData.isNotEmpty()) {
                    stopTrackingScreenInit()
                    isInitialDataFetchCompleted = true
                }

                uiState.update {
                    TransactionsScreenUIState(
                        isBackHandlerEnabled = searchText.isNotEmpty() ||
                                selectedFilter.orEmpty().areFiltersSelected() ||
                                isInSelectionMode,
                        isBottomSheetVisible = screenBottomSheetType != TransactionsScreenBottomSheetType.None,
                        isInSelectionMode = isInSelectionMode,
                        isLoading = isLoading,
                        isSearchSortAndFilterVisible = isInSelectionMode.not() && (
                                transactionDetailsListItemViewData.isNotEmpty() ||
                                        searchText.isNotEmpty() ||
                                        selectedFilter.orEmpty().areFiltersSelected() ||
                                        isLoading
                                ),
                        selectedFilter = selectedFilter.orEmpty(),
                        selectedTransactions = selectedTransactionIndices.toImmutableList(),
                        sortOptions = sortOptions.orEmpty(),
                        transactionForValues = allTransactionForValues.orEmpty(),
                        accounts = accounts.toImmutableList(),
                        expenseCategories = categoriesMap[TransactionType.EXPENSE].orEmpty()
                            .toImmutableList(),
                        incomeCategories = categoriesMap[TransactionType.INCOME].orEmpty()
                            .toImmutableList(),
                        investmentCategories = categoriesMap[TransactionType.INVESTMENT].orEmpty()
                            .toImmutableList(),
                        transactionTypes = transactionTypes.orEmpty(),
                        currentLocalDate = currentLocalDate.orMin(),
                        oldestTransactionLocalDate = oldestTransactionLocalDate.orMin(),
                        transactionDetailsListItemViewData = transactionDetailsListItemViewData,
                        selectedSortOption = selectedSortOption.orDefault(),
                        searchText = searchText,
                        screenBottomSheetType = screenBottomSheetType,
                    )
                }
            }
        }
    }
    // endregion

    // region observeForTransactionDetailsListItemViewData
    private fun observeForTransactionDetailsListItemViewData() {
        viewModelScope.launch {
            combineAndCollectLatest(
                searchText,
                selectedFilter,
                selectedSortOption,
                allTransactionData,
            ) {
                    (
                        searchText,
                        selectedFilter,
                        selectedSortOption,
                        allTransactionData,
                    ),
                ->
                val updatedAllTransactionData = withContext(
                    context = dispatcherProvider.default,
                ) {
                    allTransactionData
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
                                transactionForValuesValue = allTransactionForValues,
                            ) && isAvailableAfterTransactionTypeFilter(
                                transactionTypes = transactionTypes,
                                selectedTransactionTypesIndicesValue = selectedFilter.selectedTransactionTypeIndices,
                                transactionData = transactionData,
                            ) && isAvailableAfterAccountFilter(
                                selectedAccountsIndicesValue = selectedFilter.selectedAccountsIndices,
                                accountsValue = accounts.toImmutableList(),
                                transactionData = transactionData,
                            ) && isAvailableAfterCategoryFilter(
                                selectedExpenseCategoryIndicesValue = selectedFilter.selectedExpenseCategoryIndices,
                                selectedIncomeCategoryIndicesValue = selectedFilter.selectedIncomeCategoryIndices,
                                selectedInvestmentCategoryIndicesValue = selectedFilter.selectedInvestmentCategoryIndices,
                                expenseCategoriesValue = categoriesMap[TransactionType.EXPENSE].orEmpty()
                                    .toImmutableList(),
                                transactionData = transactionData,
                                incomeCategoriesValue = categoriesMap[TransactionType.INCOME].orEmpty()
                                    .toImmutableList(),
                                investmentCategoriesValue = categoriesMap[TransactionType.INVESTMENT].orEmpty()
                                    .toImmutableList(),
                            )
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
                        .groupBy {
                            if (selectedSortOption == SortOption.LATEST_FIRST ||
                                selectedSortOption == SortOption.OLDEST_FIRST
                            ) {
                                val dateTextBuilder = StringBuilder()
                                dateTextBuilder.append(
                                    dateTimeKit.getFormattedDate(
                                        timestamp = it.transaction.transactionTimestamp,
                                    )
                                )
                                dateTextBuilder.append(" (")
                                dateTextBuilder.append(
                                    dateTimeKit.getFormattedDayOfWeek(
                                        timestamp = it.transaction.transactionTimestamp,
                                    )
                                )
                                dateTextBuilder.append(")")
                                dateTextBuilder.toString()
                            } else {
                                ""
                            }
                        }
                        .mapValues {
                            it.value.map { listItem ->
                                listItem.toTransactionListItemData(
                                    dateTimeKit = dateTimeKit,
                                )
                                    .copy(
                                        isDeleteButtonEnabled = false,
                                        isDeleteButtonVisible = true,
                                        isEditButtonVisible = false,
                                        isExpanded = false,
                                        isRefundButtonVisible = false,
                                    )
                            }
                        }
                }
                transactionDetailsListItemViewData.update {
                    updatedAllTransactionData
                }
                if (allTransactionData.isNotEmpty()) {
                    completeLoading()
                }
            }
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
    // endregion

    // region observeForAllTransactionData
    private fun observeForAllTransactionData() {
        viewModelScope.launch {
            getAllTransactionDataFlowUseCase()
                .flowOn(
                    context = dispatcherProvider.io,
                )
                .collectLatest { updatedAllTransactionData ->
                    val accountsInTransactions = mutableSetOf<Account>()
                    var oldestTransactionLocalDateValue = Long.MAX_VALUE
                    val categoriesInTransactionsMap =
                        mutableMapOf<TransactionType, MutableSet<Category>>()

                    updatedAllTransactionData.forEach { transactionData ->
                        transactionData.accountFrom?.let {
                            accountsInTransactions.add(it)
                        }
                        transactionData.accountTo?.let {
                            accountsInTransactions.add(it)
                        }
                        oldestTransactionLocalDateValue = min(
                            oldestTransactionLocalDateValue,
                            transactionData.transaction.transactionTimestamp
                        )
                        transactionData.category?.let {
                            categoriesInTransactionsMap[it.transactionType]?.add(it)
                        }
                    }

                    accounts = accountsInTransactions
                    oldestTransactionLocalDate = dateTimeKit.getLocalDate(
                        timestamp = oldestTransactionLocalDateValue.orZero(),
                    )
                    categoriesMap = categoriesInTransactionsMap.toMap()
                    allTransactionData.update {
                        updatedAllTransactionData
                    }
                }
        }
    }
    // endregion

    // region performance tracking
    private fun startTrackingScreenInit() {
        performanceScreenInitTrace = Firebase.performance.newTrace("screen_transactions_init")
        performanceScreenInitTrace?.start()
    }

    private fun stopTrackingScreenInit() {
        performanceScreenInitTrace?.stop()
    }
    // endregion
}
