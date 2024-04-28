package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.screen

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenBottomSheetType

public sealed class AddOrEditCategoryScreenBottomSheetType : ScreenBottomSheetType {
    public data object None : AddOrEditCategoryScreenBottomSheetType()
    public data object SelectEmoji : AddOrEditCategoryScreenBottomSheetType()
}
