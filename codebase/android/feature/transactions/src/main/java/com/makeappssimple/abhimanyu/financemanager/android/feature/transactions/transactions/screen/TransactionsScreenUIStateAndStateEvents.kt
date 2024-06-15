package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.makeappssimple.abhimanyu.financemanager.android.core.common.constants.EmojiConstants
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtilImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.atEndOfDay
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orMin
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toEpochMilli
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
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.getAmountTextColor
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import java.time.LocalDate

@Stable
internal class TransactionsScreenUIStateAndStateEvents(
    val state: TransactionsScreenUIState,
    val events: TransactionsScreenUIStateEvents,
) : ScreenUIStateAndStateEvents

@Composable
internal fun rememberTransactionsScreenUIStateAndEvents(
    allTransactionData: ImmutableList<TransactionData>,
    expenseCategories: ImmutableList<Category>,
    incomeCategories: ImmutableList<Category>,
    investmentCategories: ImmutableList<Category>,
    accounts: ImmutableList<Account>,
    transactionForValues: ImmutableList<TransactionFor>,
    transactionTypes: ImmutableList<TransactionType>,
    oldestTransactionLocalDate: LocalDate?,
    sortOptions: ImmutableList<SortOption>,
    currentLocalDate: LocalDate,
): TransactionsScreenUIStateAndStateEvents {
    // region is loading
    var isLoading: Boolean by rememberSaveable {
        mutableStateOf(
            value = false,
        )
    }
    val setIsLoading = { updatedIsLoading: Boolean ->
        isLoading = updatedIsLoading
    }
    // endregion

    // region is in selection mode
    var isInSelectionMode: Boolean by rememberSaveable {
        mutableStateOf(
            value = false,
        )
    }
    val setIsInSelectionMode = { updatedIsInSelectionMode: Boolean ->
        isInSelectionMode = updatedIsInSelectionMode
    }
    // endregion

    // region screen bottom sheet type
    var screenBottomSheetType: TransactionsScreenBottomSheetType by remember {
        mutableStateOf(
            value = TransactionsScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType =
        { updatedTransactionsScreenBottomSheetType: TransactionsScreenBottomSheetType ->
            screenBottomSheetType = updatedTransactionsScreenBottomSheetType
        }
    val resetScreenBottomSheetType = {
        setScreenBottomSheetType(TransactionsScreenBottomSheetType.None)
    }
    // endregion

    // region search text
    var searchText: String by rememberSaveable {
        mutableStateOf(
            value = "",
        )
    }
    val setSearchText = { updatedSearchText: String ->
        searchText = updatedSearchText
    }
    // endregion

    // region selected filter
    var selectedFilter: Filter by remember {
        mutableStateOf(
            value = Filter(),
        )
    }
    val setSelectedFilter = { updatedSelectedFilter: Filter ->
        selectedFilter = updatedSelectedFilter
    }
    // endregion

    // region selected sort option
    var selectedSortOption: SortOption by remember {
        mutableStateOf(
            value = SortOption.LATEST_FIRST,
        )
    }
    val setSelectedSortOption = { updatedSelectedSortOption: SortOption ->
        selectedSortOption = updatedSelectedSortOption
    }
    // endregion

    val dateTimeUtil = remember {
        DateTimeUtilImpl()
    }

    val transactionDetailsListItemViewData = allTransactionData
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
                isLoading = false
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
                isLoading = false
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
            isLoading = false
        }

    // region selected transactions
    val selectedTransactions: SnapshotStateList<Int> = remember {
        mutableStateListOf()
    }
    val addToSelectedTransactions = { transactionId: Int ->
        selectedTransactions.add(transactionId)
        Unit
    }
    val removeFromSelectedTransactions = { transactionId: Int ->
        selectedTransactions.remove(transactionId)
        Unit
    }
    val clearSelectedTransactions = {
        selectedTransactions.clear()
    }
    val selectAllTransactions = {
        selectedTransactions.clear()
        selectedTransactions.addAll(
            transactionDetailsListItemViewData.values.flatMap {
                it.map { transactionListItemData ->
                    transactionListItemData.transactionId
                }
            }
        )
        Unit
    }
    // endregion

    return remember(
        isLoading,
        setIsLoading,
        isInSelectionMode,
        setIsInSelectionMode,
        screenBottomSheetType,
        setScreenBottomSheetType,
        searchText,
        setSearchText,
        selectedFilter,
        setSelectedFilter,
        selectedSortOption,
        setSelectedSortOption,
        transactionDetailsListItemViewData,
        allTransactionData,
        expenseCategories,
        incomeCategories,
        investmentCategories,
        accounts,
        transactionForValues,
        transactionTypes,
        oldestTransactionLocalDate,
        sortOptions,
        currentLocalDate,
    ) {
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
                selectedTransactions = selectedTransactions.toImmutableList(),
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
                resetScreenBottomSheetType = resetScreenBottomSheetType,
                setIsInSelectionMode = setIsInSelectionMode,
                setScreenBottomSheetType = setScreenBottomSheetType,
                setSearchText = setSearchText,
                setSelectedFilter = setSelectedFilter,
                setSelectedSortOption = setSelectedSortOption,
                addToSelectedTransactions = addToSelectedTransactions,
                removeFromSelectedTransactions = removeFromSelectedTransactions,
                clearSelectedTransactions = clearSelectedTransactions,
                selectAllTransactions = selectAllTransactions,
            ),
        )
    }
}

public fun isAvailableAfterSearch(
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

public fun isAvailableAfterDateFilter(
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

public fun isAvailableAfterTransactionForFilter(
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

public fun isAvailableAfterTransactionTypeFilter(
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

public fun isAvailableAfterAccountFilter(
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

public fun isAvailableAfterCategoryFilter(
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
