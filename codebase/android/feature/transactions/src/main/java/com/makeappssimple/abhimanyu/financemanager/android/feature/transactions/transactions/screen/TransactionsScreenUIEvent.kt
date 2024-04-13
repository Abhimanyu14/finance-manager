package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.Filter
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.SortOption
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
public sealed class TransactionsScreenUIEvent : ScreenUIEvent {
    public data object ClearSelectedTransactions : TransactionsScreenUIEvent()
    public data object NavigateToAddTransactionScreen : TransactionsScreenUIEvent()
    public data object NavigateUp : TransactionsScreenUIEvent()
    public data object SelectAllTransactions : TransactionsScreenUIEvent()

    public data class AddToSelectedTransactions(
        val transactionId: Int,
    ) : TransactionsScreenUIEvent()

    public data class NavigateToViewTransactionScreen(
        val transactionId: Int,
    ) : TransactionsScreenUIEvent()

    public data class RemoveFromSelectedTransactions(
        val transactionId: Int,
    ) : TransactionsScreenUIEvent()

    public data class ToggleTransactionSelection(
        val transactionId: Int,
    ) : TransactionsScreenUIEvent()

    public data class UpdateSearchText(
        val updatedSearchText: String,
    ) : TransactionsScreenUIEvent()

    public data class UpdateSelectedFilter(
        val updatedSelectedFilter: Filter,
    ) : TransactionsScreenUIEvent()

    public data class UpdateSelectedSortOption(
        val updatedSelectedSortOption: SortOption,
    ) : TransactionsScreenUIEvent()

    public data class UpdateTransactionForValuesInTransactions(
        val updatedTransactionForValues: Int,
    ) : TransactionsScreenUIEvent()
}
