package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.perf.metrics.Trace
import com.google.firebase.perf.performance
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
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
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
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
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.toTransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.bottomsheet.TransactionsScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.state.TransactionsScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.state.TransactionsScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.state.TransactionsScreenUIStateEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
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
public class TransactionsScreenViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val dateTimeUtil: DateTimeUtil,
    private val getAllTransactionDataFlowUseCase: GetAllTransactionDataFlowUseCase,
    private val getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
    private val myLogger: MyLogger,
    private val navigator: Navigator,
    private val updateTransactionsUseCase: UpdateTransactionsUseCase,
) : ScreenViewModel, ViewModel() {
    // region initial data
    private var isInitialDataFetchCompleted = false
    private var performanceScreenInitTrace: Trace? = null
    private var allTransactionData: MutableStateFlow<ImmutableList<TransactionData>> =
        MutableStateFlow(
            value = persistentListOf(),
        )
    private var categoriesMap: Map<TransactionType, MutableSet<Category>> = mapOf()
    private var accounts: MutableSet<Account> = mutableSetOf()
    private var oldestTransactionLocalDate: LocalDate? = null
    private var allTransactionForValues: ImmutableList<TransactionFor> = persistentListOf()

    private val selectedTransactionIndices: MutableStateFlow<MutableList<Int>> = MutableStateFlow(
        value = mutableListOf(),
    )
    private val transactionDetailsListItemViewData: MutableStateFlow<Map<String, ImmutableList<TransactionListItemData>>> =
        MutableStateFlow(
            value = mutableMapOf(),
        )

    private val transactionTypes: ImmutableList<TransactionType> =
        TransactionType.entries.toImmutableList()
    private val sortOptions: ImmutableList<SortOption> = SortOption.entries.toImmutableList()
    private val currentLocalDate: LocalDate = dateTimeUtil.getCurrentLocalDate()
    // endregion

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

    // region uiStateAndStateEvents
    internal val uiStateAndStateEvents: MutableStateFlow<TransactionsScreenUIStateAndStateEvents> =
        MutableStateFlow(
            value = TransactionsScreenUIStateAndStateEvents(),
        )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        startTrackingScreenInit()
        fetchData()
        observeData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            startLoading()
            allTransactionForValues = getAllTransactionForValuesUseCase()
        }
    }

    private fun observeData() {
        observeForUiStateAndStateEventsChanges()
        observeForTransactionDetailsListItemViewData()
        observeForAllTransactionData()
    }
    // endregion

    // region observeForUiStateAndStateEventsChanges
    private fun observeForUiStateAndStateEventsChanges() {
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

                uiStateAndStateEvents.update {
                    TransactionsScreenUIStateAndStateEvents(
                        state = TransactionsScreenUIState(
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
                        ),
                        events = TransactionsScreenUIStateEvents(
                            addToSelectedTransactions = ::addToSelectedTransactions,
                            clearSelectedTransactions = ::clearSelectedTransactions,
                            navigateToAddTransactionScreen = ::navigateToAddTransactionScreen,
                            navigateToViewTransactionScreen = ::navigateToViewTransactionScreen,
                            navigateUp = ::navigateUp,
                            removeFromSelectedTransactions = ::removeFromSelectedTransactions,
                            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
                            selectAllTransactions = ::selectAllTransactions,
                            setIsInSelectionMode = ::setIsInSelectionMode,
                            setScreenBottomSheetType = ::setScreenBottomSheetType,
                            setSearchText = ::setSearchText,
                            setSelectedFilter = ::setSelectedFilter,
                            setSelectedSortOption = ::setSelectedSortOption,
                            updateTransactionForValuesInTransactions = ::updateTransactionForValuesInTransactions,
                        ),
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
                val start = System.currentTimeMillis()
                myLogger.logInfo(
                    message = "observeForTransactionDetailsListItemViewData: Starting ${System.currentTimeMillis()}",
                )

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
                                dateTimeUtil.getFormattedDate(
                                    timestamp = it.transaction.transactionTimestamp,
                                )
                            } else {
                                ""
                            }
                        }
                        .mapValues {
                            it.value.map { listItem ->
                                listItem.toTransactionListItemData(
                                    dateTimeUtil = dateTimeUtil,
                                ).copy(
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
                myLogger.logInfo(
                    message = "observeForTransactionDetailsListItemViewData: ${System.currentTimeMillis() - start}",
                )
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
                    oldestTransactionLocalDate = dateTimeUtil.getLocalDate(
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

    // region loading
    private fun startLoading() {
        isLoading.update {
            true
        }
    }

    private fun completeLoading() {
        isLoading.update {
            false
        }
    }
    // endregion

    // region state events
    private fun addToSelectedTransactions(
        transactionId: Int,
    ) {
        selectedTransactionIndices.update {
            it.apply {
                add(transactionId)
            }
        }
    }

    private fun clearSelectedTransactions() {
        selectedTransactionIndices.update {
            mutableListOf()
        }
    }

    private fun navigateUp() {
        navigator.navigateUp()
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
    // endregion
}
