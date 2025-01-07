package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.bottomsheet

import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenBottomSheetType

internal sealed class AddCategoryScreenBottomSheetType : ScreenBottomSheetType {
    data object None : AddCategoryScreenBottomSheetType()
    data object SelectEmoji : AddCategoryScreenBottomSheetType()
}
