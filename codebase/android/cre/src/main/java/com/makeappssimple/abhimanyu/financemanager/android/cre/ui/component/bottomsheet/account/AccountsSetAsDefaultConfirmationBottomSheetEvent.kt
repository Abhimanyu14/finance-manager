package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.account

import androidx.compose.runtime.Immutable

@Immutable
public sealed class AccountsSetAsDefaultConfirmationBottomSheetEvent {
    public data object OnNegativeButtonClick : AccountsSetAsDefaultConfirmationBottomSheetEvent()
    public data object OnPositiveButtonClick : AccountsSetAsDefaultConfirmationBottomSheetEvent()
}
