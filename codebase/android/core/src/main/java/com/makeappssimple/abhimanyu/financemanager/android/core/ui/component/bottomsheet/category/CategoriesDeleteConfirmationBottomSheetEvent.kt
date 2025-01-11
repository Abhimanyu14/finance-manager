package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.category

import androidx.compose.runtime.Immutable

@Immutable
public sealed class CategoriesDeleteConfirmationBottomSheetEvent {
    public data object OnNegativeButtonClick : CategoriesDeleteConfirmationBottomSheetEvent()
    public data object OnPositiveButtonClick : CategoriesDeleteConfirmationBottomSheetEvent()
}
