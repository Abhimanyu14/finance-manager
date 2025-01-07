package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common

import androidx.compose.runtime.Immutable

@Immutable
public sealed class MyConfirmationBottomSheetEvent {
    public data object OnNegativeButtonClick : MyConfirmationBottomSheetEvent()
    public data object OnPositiveButtonClick : MyConfirmationBottomSheetEvent()
}
