package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactions

import androidx.compose.runtime.Immutable

@Immutable
internal data class TransactionsFiltersBottomSheetData(
    val data: TransactionFilterBottomSheetFilterGroupData,
    val handleEvent: (event: TransactionFilterBottomSheetFilterGroupEvent) -> Unit = {},
)
