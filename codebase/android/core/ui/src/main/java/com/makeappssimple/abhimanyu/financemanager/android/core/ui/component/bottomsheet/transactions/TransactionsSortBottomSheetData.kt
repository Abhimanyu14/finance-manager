package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactions

import androidx.compose.runtime.Immutable

@Immutable
public data class TransactionsSortBottomSheetData(
    val data: TransactionsSortBottomSheetItemData,
    val handleEvent: (event: TransactionsSortBottomSheetItemEvent) -> Unit = {},
)
