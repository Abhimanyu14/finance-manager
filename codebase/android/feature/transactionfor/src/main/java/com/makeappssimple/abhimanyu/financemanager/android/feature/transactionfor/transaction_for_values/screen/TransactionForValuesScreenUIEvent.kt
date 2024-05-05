package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
public sealed class TransactionForValuesScreenUIEvent : ScreenUIEvent {
    public data object OnFloatingActionButtonClick : TransactionForValuesScreenUIEvent()
    public data object OnTopAppBarNavigationButtonClick : TransactionForValuesScreenUIEvent()

    public sealed class OnTransactionForValuesDeleteConfirmationBottomSheet {
        public data class PositiveButtonClick(
            val transactionForId: Int,
        ) : TransactionForValuesScreenUIEvent()
    }

    public sealed class OnTransactionForValuesMenuBottomSheet {
        public data class EditButtonClick(
            val transactionForId: Int,
        ) : TransactionForValuesScreenUIEvent()
    }
}
