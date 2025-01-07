package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.transactionfor

import androidx.compose.runtime.Immutable

@Immutable
public sealed class TransactionForValuesDeleteConfirmationBottomSheetEvent {
    public data object OnNegativeButtonClick :
        TransactionForValuesDeleteConfirmationBottomSheetEvent()

    public data object OnPositiveButtonClick :
        TransactionForValuesDeleteConfirmationBottomSheetEvent()
}
