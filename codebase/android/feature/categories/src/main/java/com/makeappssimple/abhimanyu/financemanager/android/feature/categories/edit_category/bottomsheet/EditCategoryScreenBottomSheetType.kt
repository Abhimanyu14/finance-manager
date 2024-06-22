package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.bottomsheet

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenBottomSheetType

internal sealed class EditCategoryScreenBottomSheetType : ScreenBottomSheetType {
    data object None : EditCategoryScreenBottomSheetType()
    data object SelectEmoji : EditCategoryScreenBottomSheetType()
}
