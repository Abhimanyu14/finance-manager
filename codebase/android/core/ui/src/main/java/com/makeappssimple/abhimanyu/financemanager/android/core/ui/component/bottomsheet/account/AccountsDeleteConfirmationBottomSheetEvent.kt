package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.account

import androidx.compose.runtime.Immutable

@Immutable
public sealed class AccountsDeleteConfirmationBottomSheetEvent {
    public data object OnNegativeButtonClick : AccountsDeleteConfirmationBottomSheetEvent()
    public data object OnPositiveButtonClick : AccountsDeleteConfirmationBottomSheetEvent()
}
