package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.bottomsheet

import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenBottomSheetType

internal sealed class SettingsScreenBottomSheetType : ScreenBottomSheetType {
    data object None : SettingsScreenBottomSheetType()
}
