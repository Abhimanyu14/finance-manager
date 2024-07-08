package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.EmojiConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
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
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
public class TransactionsScreenViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val getAllTransactionDataFlowUseCase: GetAllTransactionDataFlowUseCase,
    private val getAllTransactionForValuesFlowUseCase: GetAllTransactionForValuesFlowUseCase,
    private val dateTimeUtil: DateTimeUtil,
    private val navigator: Navigator,
    private val updateTransactionsUseCase: UpdateTransactionsUseCase,
) : ScreenViewModel, ViewModel() {
    // region initial data
    private val allTransactionData: MutableStateFlow<ImmutableList<TransactionData>> =
        MutableStateFlow(
            value = persistentListOf(),
        )
    private val expenseCategories: MutableStateFlow<ImmutableList<Category>?> = MutableStateFlow(
        value = persistentListOf(),
    )
    private val incomeCategories: MutableStateFlow<ImmutableList<Category>?> = MutableStateFlow(
        value = persistentListOf(),
    )
    private val investmentCategories: MutableStateFlow<ImmutableList<Category>?> = MutableStateFlow(
        value = persistentListOf(),
    )
    private val accounts: MutableStateFlow<ImmutableList<Account>?> = MutableStateFlow(
        value = persistentListOf(),
    )
    private var oldestTransactionLocalDate: MutableStateFlow<LocalDate?> = MutableStateFlow(
        value = null,
    )
    private val transactionForValues: MutableStateFlow<ImmutableList<TransactionFor>> =
        MutableStateFlow(
            value = persistentListOf(),
        )

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
        fetchData()
        observeData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            completeLoading()
        }
    }

    private fun observeData() {
        observeForUiStateAndStateEventsChanges()
        observeForTransactionDetailsListItemViewData()
        observeForAllTransactionData()
        observeForAllTransactionForValues()
    }
    // endregion

    // region observeForUiStateAndStateEventsChanges
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
                expenseCategories,
                incomeCategories,
                investmentCategories,
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
                        expenseCategories,
                        incomeCategories,
                        investmentCategories,
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
                            expenseCategories = expenseCategories.orEmpty(),
                            incomeCategories = incomeCategories.orEmpty(),
                            investmentCategories = investmentCategories.orEmpty(),
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
    // endregion

    // region observeForTransactionDetailsListItemViewData
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
                allTransactionData,
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
                                completeLoading()
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
                                completeLoading()
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
                            completeLoading()
                        }
                }
                transactionDetailsListItemViewData.update {
                    updatedAllTransactionData
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
                    val updatedAccounts = withContext(
                        context = dispatcherProvider.default,
                    ) {
                        updatedAllTransactionData.flatMap { transactionData ->
                            listOfNotNull(
                                transactionData.accountFrom,
                                transactionData.accountTo,
                            )
                        }.distinct()
                    }
                    accounts.update {
                        updatedAccounts
                    }

                    val updatedOldestTransactionLocalDate = withContext(
                        context = dispatcherProvider.default,
                    ) {
                        dateTimeUtil.getLocalDate(
                            timestamp = updatedAllTransactionData.minOfOrNull { transactionData ->
                                transactionData.transaction.transactionTimestamp
                            }.orZero(),
                        )
                    }
                    oldestTransactionLocalDate.update {
                        updatedOldestTransactionLocalDate
                    }

                    allTransactionData.update {
                        updatedAllTransactionData
                    }
                    val updatedCategoriesMap = withContext(
                        context = dispatcherProvider.default,
                    ) {
                        updatedAllTransactionData
                            .mapNotNull { transactionData ->
                                transactionData.category
                            }.groupBy { category ->
                                category.transactionType
                            }.mapValues { (_, categories) ->
                                categories.distinct()
                            }
                    }
                    expenseCategories.update {
                        updatedCategoriesMap[TransactionType.EXPENSE].orEmpty()
                    }
                    incomeCategories.update {
                        updatedCategoriesMap[TransactionType.INCOME].orEmpty()
                    }
                    investmentCategories.update {
                        updatedCategoriesMap[TransactionType.INVESTMENT].orEmpty()
                    }
                }
        }
    }
    // endregion

    // region observeForAllTransactionForValues
    private fun observeForAllTransactionForValues() {
        viewModelScope.launch {
            getAllTransactionForValuesFlowUseCase()
                .flowOn(
                    context = dispatcherProvider.io,
                )
                .collectLatest { updatedAllTransactionForValues ->
                    transactionForValues.update {
                        updatedAllTransactionForValues
                    }
                }
        }
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
