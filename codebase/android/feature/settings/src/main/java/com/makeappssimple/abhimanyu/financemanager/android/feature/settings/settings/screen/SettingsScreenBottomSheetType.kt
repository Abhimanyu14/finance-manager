package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenBottomSheetType

public sealed class SettingsScreenBottomSheetType : ScreenBottomSheetType {
    public data object None : SettingsScreenBottomSheetType()
}
