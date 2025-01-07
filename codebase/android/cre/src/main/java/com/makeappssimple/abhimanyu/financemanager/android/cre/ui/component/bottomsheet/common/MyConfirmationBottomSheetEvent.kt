package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.common

import androidx.compose.runtime.Immutable

@Immutable
public sealed class MyConfirmationBottomSheetEvent {
    public data object OnNegativeButtonClick : MyConfirmationBottomSheetEvent()
    public data object OnPositiveButtonClick : MyConfirmationBottomSheetEvent()
}
