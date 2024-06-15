package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.bottomsheet

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenBottomSheetType

@Immutable
public sealed class CategoriesScreenBottomSheetType : ScreenBottomSheetType {
    public data object DeleteConfirmation : CategoriesScreenBottomSheetType()
    public data object None : CategoriesScreenBottomSheetType()
    public data object SetAsDefaultConfirmation : CategoriesScreenBottomSheetType()

    public data class Menu(
        val isDeleteVisible: Boolean,
        val isEditVisible: Boolean,
        val isSetAsDefaultVisible: Boolean,
        val categoryId: Int,
    ) : CategoriesScreenBottomSheetType()
}
