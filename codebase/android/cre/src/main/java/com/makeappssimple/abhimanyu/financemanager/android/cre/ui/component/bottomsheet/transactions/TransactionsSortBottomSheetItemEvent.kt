package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.transactions

import androidx.compose.runtime.Immutable

@Immutable
public sealed class TransactionsSortBottomSheetItemEvent {
    public data object OnClick : TransactionsSortBottomSheetItemEvent()
}
