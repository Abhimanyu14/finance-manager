package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.event

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenUIEvent

@Immutable
internal sealed class ViewTransactionScreenUIEvent : ScreenUIEvent {
    data object OnNavigationBackButtonClick : ViewTransactionScreenUIEvent()
    data object OnTopAppBarNavigationButtonClick : ViewTransactionScreenUIEvent()

    sealed class OnTransactionDeleteConfirmationBottomSheet {
        data object NegativeButtonClick : ViewTransactionScreenUIEvent()
        data object PositiveButtonClick : ViewTransactionScreenUIEvent()
    }

    sealed class OnTransactionListItem {
        data class RefundButtonClick(
            val transactionId: Int,
        ) : ViewTransactionScreenUIEvent()

        data class DeleteButtonClick(
            val transactionId: Int,
        ) : ViewTransactionScreenUIEvent()

        data class EditButtonClick(
            val transactionId: Int,
        ) : ViewTransactionScreenUIEvent()

        data class Click(
            val transactionId: Int,
        ) : ViewTransactionScreenUIEvent()
    }
}
