package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.transactions

import androidx.compose.runtime.Immutable

@Immutable
public data class TransactionsSortBottomSheetData(
    val data: TransactionsSortBottomSheetItemData,
    val handleEvent: (event: TransactionsSortBottomSheetItemEvent) -> Unit = {},
)
