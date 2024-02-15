package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.Filter
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.SortOption
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
sealed class TransactionsScreenUIEvent : ScreenUIEvent {
    data object ClearSelectedTransactions : TransactionsScreenUIEvent()
    data object NavigateToAddTransactionScreen : TransactionsScreenUIEvent()
    data object NavigateUp : TransactionsScreenUIEvent()
    data object SelectAllTransactions : TransactionsScreenUIEvent()

    data class AddToSelectedTransactions(
        val transactionId: Int,
    ) : TransactionsScreenUIEvent()

    data class NavigateToViewTransactionScreen(
        val transactionId: Int,
    ) : TransactionsScreenUIEvent()

    data class RemoveFromSelectedTransactions(
        val transactionId: Int,
    ) : TransactionsScreenUIEvent()

    data class ToggleTransactionSelection(
        val transactionId: Int,
    ) : TransactionsScreenUIEvent()

    data class UpdateSearchText(
        val updatedSearchText: String,
    ) : TransactionsScreenUIEvent()

    data class UpdateSelectedFilter(
        val updatedSelectedFilter: Filter,
    ) : TransactionsScreenUIEvent()

    data class UpdateSelectedSortOption(
        val updatedSelectedSortOption: SortOption,
    ) : TransactionsScreenUIEvent()

    data class UpdateTransactionForValuesInTransactions(
        val updatedTransactionForValues: Int,
    ) : TransactionsScreenUIEvent()
}
