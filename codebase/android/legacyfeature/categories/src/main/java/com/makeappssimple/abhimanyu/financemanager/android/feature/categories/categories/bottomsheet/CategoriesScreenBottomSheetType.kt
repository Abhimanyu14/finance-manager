package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.bottomsheet

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenBottomSheetType

@Immutable
internal sealed class CategoriesScreenBottomSheetType : ScreenBottomSheetType {
    data object DeleteConfirmation : CategoriesScreenBottomSheetType()
    data object None : CategoriesScreenBottomSheetType()
    data object SetAsDefaultConfirmation : CategoriesScreenBottomSheetType()

    data class Menu(
        val isDeleteVisible: Boolean,
        val isEditVisible: Boolean,
        val isSetAsDefaultVisible: Boolean,
        val categoryId: Int,
    ) : CategoriesScreenBottomSheetType()
}
