package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.cre.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.feature.Filter
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.feature.SortOption
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.listitem.transaction.TransactionListItemData
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

    fun setScreenBottomSheetType(
        updatedTransactionsScreenBottomSheetType: TransactionsScreenBottomSheetType,
    )

    fun setIsInSelectionMode(
        updatedIsInSelectionMode: Boolean,
    )

    fun setSearchText(
        updatedSearchText: String,
    )

    fun setSelectedFilter(
        updatedSelectedFilter: Filter,
    )

    fun setSelectedSortOption(
        updatedSelectedSortOption: SortOption,
    )

    fun updateTransactionForValuesInTransactions(
        transactionForId: Int,
    )
    // endregion
}
