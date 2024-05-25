package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.screen

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenBottomSheetType

public sealed class AddCategoryScreenBottomSheetType : ScreenBottomSheetType {
    public data object None : AddCategoryScreenBottomSheetType()
    public data object SelectEmoji : AddCategoryScreenBottomSheetType()
}
