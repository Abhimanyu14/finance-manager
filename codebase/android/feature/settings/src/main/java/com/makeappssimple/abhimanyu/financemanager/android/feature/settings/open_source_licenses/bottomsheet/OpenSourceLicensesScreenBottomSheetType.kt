package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.bottomsheet

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenBottomSheetType

public sealed class OpenSourceLicensesScreenBottomSheetType : ScreenBottomSheetType {
    public data object None : OpenSourceLicensesScreenBottomSheetType()
}
