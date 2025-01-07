package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.event

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenUIEvent

@Immutable
internal sealed class TransactionForValuesScreenUIEvent : ScreenUIEvent {
    data object OnFloatingActionButtonClick : TransactionForValuesScreenUIEvent()
    data object OnNavigationBackButtonClick : TransactionForValuesScreenUIEvent()
    data object OnTopAppBarNavigationButtonClick : TransactionForValuesScreenUIEvent()

    sealed class OnTransactionForValuesDeleteConfirmationBottomSheet {
        data object NegativeButtonClick : TransactionForValuesScreenUIEvent()
        data object PositiveButtonClick : TransactionForValuesScreenUIEvent()
    }

    sealed class OnTransactionForValuesMenuBottomSheet {
        data class DeleteButtonClick(
            val transactionForId: Int,
        ) : TransactionForValuesScreenUIEvent()

        data class EditButtonClick(
            val transactionForId: Int,
        ) : TransactionForValuesScreenUIEvent()
    }

    sealed class OnTransactionForListItem {
        data class Click(
            val isDeleteVisible: Boolean,
            val transactionForId: Int,
        ) : TransactionForValuesScreenUIEvent()

        data class MoreOptionsIconButtonClick(
            val isDeleteVisible: Boolean,
            val transactionForId: Int,
        ) : TransactionForValuesScreenUIEvent()
    }
}
