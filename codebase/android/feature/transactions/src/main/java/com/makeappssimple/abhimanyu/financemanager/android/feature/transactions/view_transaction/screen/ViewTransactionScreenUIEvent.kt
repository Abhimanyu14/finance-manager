package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
public sealed class ViewTransactionScreenUIEvent : ScreenUIEvent {
    public data object OnBottomSheetDismissed : ViewTransactionScreenUIEvent()
    public data object OnNavigationBackButtonClick : ViewTransactionScreenUIEvent()
    public data object OnTopAppBarNavigationButtonClick : ViewTransactionScreenUIEvent()

    public sealed class OnTransactionDeleteConfirmationBottomSheet {
        public data object NegativeButtonClick : ViewTransactionScreenUIEvent()
        public data object PositiveButtonClick : ViewTransactionScreenUIEvent()
    }

    public sealed class OnTransactionListItem {
        public data class RefundButtonClick(
            val transactionId: Int,
        ) : ViewTransactionScreenUIEvent()

        public data class DeleteButtonClick(
            val transactionId: Int,
        ) : ViewTransactionScreenUIEvent()

        public data class EditButtonClick(
            val transactionId: Int,
        ) : ViewTransactionScreenUIEvent()

        public data class Click(
            val transactionId: Int,
        ) : ViewTransactionScreenUIEvent()
    }
}
