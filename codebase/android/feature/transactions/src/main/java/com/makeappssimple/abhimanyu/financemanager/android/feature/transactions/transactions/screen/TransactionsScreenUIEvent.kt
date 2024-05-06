package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.Filter
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.SortOption
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
public sealed class TransactionsScreenUIEvent : ScreenUIEvent {
    public data object ClearSelectedTransactions : TransactionsScreenUIEvent()
    public data object OnFloatingActionButtonClick : TransactionsScreenUIEvent()
    public data object OnTopAppBarNavigationButtonClick : TransactionsScreenUIEvent()

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

    public sealed class OnTransactionListItem {
        public data class Click(
            val isInSelectionMode: Boolean,
            val isSelected: Boolean,
            val transactionId: Int,
        ) : TransactionsScreenUIEvent()

        public data class LongClick(
            val isInSelectionMode: Boolean,
            val isSelected: Boolean,
            val transactionId: Int,
        ) : TransactionsScreenUIEvent()
    }

    public sealed class OnTransactionsMenuBottomSheet {
        public data object SelectAllTransactionsButtonClick : TransactionsScreenUIEvent()
    }
}
