package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
public sealed class TransactionForValuesScreenUIEvent : ScreenUIEvent {
    public data object OnFloatingActionButtonClick : TransactionForValuesScreenUIEvent()
    public data object OnNavigationBackButtonClick : TransactionForValuesScreenUIEvent()
    public data object OnTopAppBarNavigationButtonClick : TransactionForValuesScreenUIEvent()

    public sealed class OnTransactionForValuesDeleteConfirmationBottomSheet {
        public data object NegativeButtonClick : TransactionForValuesScreenUIEvent()
        public data object PositiveButtonClick : TransactionForValuesScreenUIEvent()
    }

    public sealed class OnTransactionForValuesMenuBottomSheet {
        public data class DeleteButtonClick(
            val transactionForId: Int,
        ) : TransactionForValuesScreenUIEvent()

        public data class EditButtonClick(
            val transactionForId: Int,
        ) : TransactionForValuesScreenUIEvent()
    }

    public sealed class OnTransactionForListItem {
        public data class Click(
            val isDeleteVisible: Boolean,
            val transactionForId: Int?,
        ) : TransactionForValuesScreenUIEvent()

        public data class MoreOptionsIconButtonClick(
            val isDeleteVisible: Boolean,
            val transactionForId: Int?,
        ) : TransactionForValuesScreenUIEvent()
    }
}
