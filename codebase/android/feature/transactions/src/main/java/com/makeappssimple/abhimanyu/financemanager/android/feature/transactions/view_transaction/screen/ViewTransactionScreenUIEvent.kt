package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
sealed class ViewTransactionScreenUIEvent : ScreenUIEvent {
    data object NavigateUp : ViewTransactionScreenUIEvent()

    data class DeleteTransaction(
        val transactionId: Int,
    ) : ViewTransactionScreenUIEvent()

    data class NavigateToAddTransactionScreen(
        val transactionId: Int,
    ) : ViewTransactionScreenUIEvent()

    data class NavigateToEditTransactionScreen(
        val transactionId: Int,
    ) : ViewTransactionScreenUIEvent()

    data class NavigateToViewTransactionScreen(
        val transactionId: Int,
    ) : ViewTransactionScreenUIEvent()
}
