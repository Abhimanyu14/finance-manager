package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
sealed class TransactionForValuesScreenUIEvent : ScreenUIEvent {
    data object NavigateToAddTransactionForScreen : TransactionForValuesScreenUIEvent()
    data object NavigateUp : TransactionForValuesScreenUIEvent()

    data class DeleteTransactionFor(
        val transactionForId: Int,
    ) : TransactionForValuesScreenUIEvent()

    data class NavigateToEditTransactionForScreen(
        val transactionForId: Int,
    ) : TransactionForValuesScreenUIEvent()
}
