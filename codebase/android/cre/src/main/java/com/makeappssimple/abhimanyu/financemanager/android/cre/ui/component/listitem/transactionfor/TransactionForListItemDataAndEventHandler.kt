package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.listitem.transactionfor

import androidx.compose.runtime.Immutable

@Immutable
public data class TransactionForListItemDataAndEventHandler(
    val data: TransactionForListItemData,
    val handleEvent: (event: TransactionForListItemEvent) -> Unit = {},
)
