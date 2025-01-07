package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactions

import androidx.compose.runtime.Immutable

@Immutable
public sealed class TransactionDeleteConfirmationBottomSheetEvent {
    public data object OnNegativeButtonClick : TransactionDeleteConfirmationBottomSheetEvent()
    public data object OnPositiveButtonClick : TransactionDeleteConfirmationBottomSheetEvent()
}
