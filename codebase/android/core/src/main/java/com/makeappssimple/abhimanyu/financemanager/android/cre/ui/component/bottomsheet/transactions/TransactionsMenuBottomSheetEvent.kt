package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.transactions

import androidx.compose.runtime.Immutable

@Immutable
public sealed class TransactionsMenuBottomSheetEvent {
    public data object OnSelectAllTransactionsClick : TransactionsMenuBottomSheetEvent()
    public data object OnUpdateTransactionForClick : TransactionsMenuBottomSheetEvent()
}
