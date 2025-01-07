package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transactionfor

import androidx.compose.runtime.Immutable

@Immutable
public sealed class TransactionForListItemEvent {
    public data object OnClick : TransactionForListItemEvent()
    public data object OnMoreOptionsIconButtonClick : TransactionForListItemEvent()
}
