package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.listitem.transactionfor

import androidx.compose.runtime.Immutable

@Immutable
public data class TransactionForListItemData(
    val transactionForId: Int = -1,
    val isMoreOptionsIconButtonVisible: Boolean = false,
    val isDeleteBottomSheetMenuItemVisible: Boolean = false,
    val title: String,
)
