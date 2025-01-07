package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transaction

import androidx.compose.runtime.Immutable

@Immutable
public sealed class TransactionListItemEvent {
    public data object OnClick : TransactionListItemEvent()
    public data object OnDeleteButtonClick : TransactionListItemEvent()
    public data object OnEditButtonClick : TransactionListItemEvent()
    public data object OnLongClick : TransactionListItemEvent()
    public data object OnRefundButtonClick : TransactionListItemEvent()
}
