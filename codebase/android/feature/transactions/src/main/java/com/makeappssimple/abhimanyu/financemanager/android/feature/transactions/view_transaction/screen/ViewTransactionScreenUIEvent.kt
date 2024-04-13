package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
public sealed class ViewTransactionScreenUIEvent : ScreenUIEvent {
    public data object NavigateUp : ViewTransactionScreenUIEvent()

    public data class DeleteTransaction(
        val transactionId: Int,
    ) : ViewTransactionScreenUIEvent()

    public data class NavigateToAddTransactionScreen(
        val transactionId: Int,
    ) : ViewTransactionScreenUIEvent()

    public data class NavigateToEditTransactionScreen(
        val transactionId: Int,
    ) : ViewTransactionScreenUIEvent()

    public data class NavigateToViewTransactionScreen(
        val transactionId: Int,
    ) : ViewTransactionScreenUIEvent()
}
