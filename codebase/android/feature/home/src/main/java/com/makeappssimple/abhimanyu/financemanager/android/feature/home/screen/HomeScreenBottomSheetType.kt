package com.makeappssimple.abhimanyu.financemanager.android.feature.home.screen

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenBottomSheetType

public sealed class HomeScreenBottomSheetType : ScreenBottomSheetType {
    public data object None : HomeScreenBottomSheetType()
}
