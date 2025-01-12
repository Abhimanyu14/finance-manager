package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.Filter
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.SortOption
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction.TransactionListItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.bottomsheet.TransactionsScreenBottomSheetType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.MutableStateFlow

internal interface TransactionsScreenUIStateDelegate {
    // region initial data
    val allTransactionData: MutableStateFlow<ImmutableList<TransactionData>>
    val selectedTransactionIndices: MutableStateFlow<ImmutableList<Int>>
    val transactionDetailsListItemViewData: MutableStateFlow<Map<String, ImmutableList<TransactionListItemData>>>
    // endregion

    // region UI state
    val isLoading: MutableStateFlow<Boolean>
    val isInSelectionMode: MutableStateFlow<Boolean>
    val searchText: MutableStateFlow<String>
    val selectedFilter: MutableStateFlow<Filter>
    val selectedSortOption: MutableStateFlow<SortOption>
    val screenBottomSheetType: MutableStateFlow<TransactionsScreenBottomSheetType>
    // endregion

    // region loading
    fun startLoading()

    fun completeLoading()

    fun <T> withLoading(
        block: () -> T,
    ): T

    suspend fun <T> withLoadingSuspend(
        block: suspend () -> T,
    ): T
    // endregion

    // region state events
    fun addToSelectedTransactions(
        transactionId: Int,
    )

    fun clearSelectedTransactions()

    fun navigateUp()

    fun removeFromSelectedTransactions(
        transactionId: Int,
    )

    fun navigateToAddTransactionScreen()

    fun navigateToViewTransactionScreen(
        transactionId: Int,
    )

    fun selectAllTransactions()

    fun resetScreenBottomSheetType()

    fun updateScreenBottomSheetType(
        updatedTransactionsScreenBottomSheetType: TransactionsScreenBottomSheetType,
    )

    fun updateIsInSelectionMode(
        updatedIsInSelectionMode: Boolean,
    )

    fun updateSearchText(
        updatedSearchText: String,
    )

    fun updateSelectedFilter(
        updatedSelectedFilter: Filter,
    )

    fun updateSelectedSortOption(
        updatedSelectedSortOption: SortOption,
    )

    fun updateTransactionForValuesInTransactions(
        transactionForId: Int,
    )
    // endregion
}
