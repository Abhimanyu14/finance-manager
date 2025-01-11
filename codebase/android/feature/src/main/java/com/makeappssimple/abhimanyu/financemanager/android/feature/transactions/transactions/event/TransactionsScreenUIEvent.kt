package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.event

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.Filter
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.SortOption
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
internal sealed class TransactionsScreenUIEvent : ScreenUIEvent {
    data object OnBottomSheetDismissed : TransactionsScreenUIEvent()
    data object OnSelectionModeTopAppBarNavigationButtonClick : TransactionsScreenUIEvent()
    data object OnSelectionModeTopAppBarMoreOptionsButtonClick : TransactionsScreenUIEvent()
    data object OnFloatingActionButtonClick : TransactionsScreenUIEvent()
    data object OnTopAppBarNavigationButtonClick : TransactionsScreenUIEvent()
    data object OnNavigationBackButtonClick : TransactionsScreenUIEvent()
    data object OnSortActionButtonClick : TransactionsScreenUIEvent()
    data object OnFilterActionButtonClick : TransactionsScreenUIEvent()

    data class OnSearchTextUpdated(
        val updatedSearchText: String,
    ) : TransactionsScreenUIEvent()

    data class OnSelectedFilterUpdated(
        val updatedSelectedFilter: Filter,
    ) : TransactionsScreenUIEvent()

    data class OnSelectedSortOptionUpdated(
        val updatedSelectedSortOption: SortOption,
    ) : TransactionsScreenUIEvent()

    sealed class OnSelectTransactionForBottomSheet {
        data class ItemClick(
            val updatedTransactionForValues: Int,
        ) : TransactionsScreenUIEvent()
    }

    sealed class OnTransactionListItem {
        data class Click(
            val isInSelectionMode: Boolean,
            val isSelected: Boolean,
            val transactionId: Int,
        ) : TransactionsScreenUIEvent()

        data class LongClick(
            val isInSelectionMode: Boolean,
            val isSelected: Boolean,
            val transactionId: Int,
        ) : TransactionsScreenUIEvent()
    }

    sealed class OnTransactionsMenuBottomSheet {
        data object SelectAllTransactionsButtonClick : TransactionsScreenUIEvent()
        data object UpdateTransactionForButtonClick : TransactionsScreenUIEvent()
    }
}
