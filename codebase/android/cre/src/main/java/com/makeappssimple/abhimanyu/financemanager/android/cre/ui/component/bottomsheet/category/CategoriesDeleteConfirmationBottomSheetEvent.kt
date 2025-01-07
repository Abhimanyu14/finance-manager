package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.category

import androidx.compose.runtime.Immutable

@Immutable
public sealed class CategoriesDeleteConfirmationBottomSheetEvent {
    public data object OnNegativeButtonClick : CategoriesDeleteConfirmationBottomSheetEvent()
    public data object OnPositiveButtonClick : CategoriesDeleteConfirmationBottomSheetEvent()
}
