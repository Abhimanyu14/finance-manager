package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
public sealed class TransactionForValuesScreenUIEvent : ScreenUIEvent {
    public data object NavigateToAddTransactionForScreen : TransactionForValuesScreenUIEvent()
    public data object OnTopAppBarNavigationButtonClick : TransactionForValuesScreenUIEvent()

    public data class DeleteTransactionFor(
        val transactionForId: Int,
    ) : TransactionForValuesScreenUIEvent()

    public data class NavigateToEditTransactionForScreen(
        val transactionForId: Int,
    ) : TransactionForValuesScreenUIEvent()
}
