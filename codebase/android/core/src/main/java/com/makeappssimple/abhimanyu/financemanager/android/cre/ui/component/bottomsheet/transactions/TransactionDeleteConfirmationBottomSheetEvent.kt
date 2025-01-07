package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.transactions

import androidx.compose.runtime.Immutable

@Immutable
public sealed class TransactionDeleteConfirmationBottomSheetEvent {
    public data object OnNegativeButtonClick : TransactionDeleteConfirmationBottomSheetEvent()
    public data object OnPositiveButtonClick : TransactionDeleteConfirmationBottomSheetEvent()
}
