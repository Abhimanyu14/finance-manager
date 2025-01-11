package com.makeappssimple.abhimanyu.financemanager.android.feature.home.home.bottomsheet

import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenBottomSheetType

internal sealed class HomeScreenBottomSheetType : ScreenBottomSheetType {
    data object None : HomeScreenBottomSheetType()
}
