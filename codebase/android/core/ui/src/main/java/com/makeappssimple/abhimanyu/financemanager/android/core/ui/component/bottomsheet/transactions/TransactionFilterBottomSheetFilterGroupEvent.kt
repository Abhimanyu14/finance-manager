package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactions

import androidx.compose.runtime.Immutable

@Immutable
internal sealed class TransactionFilterBottomSheetFilterGroupEvent {
    data object OnClearButtonClick : TransactionFilterBottomSheetFilterGroupEvent()
}
