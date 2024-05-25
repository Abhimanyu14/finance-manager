package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.screen

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenBottomSheetType

public sealed class EditCategoryScreenBottomSheetType : ScreenBottomSheetType {
    public data object None : EditCategoryScreenBottomSheetType()
    public data object SelectEmoji : EditCategoryScreenBottomSheetType()
}
