package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.bottomsheet

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenBottomSheetType

internal sealed class OpenSourceLicensesScreenBottomSheetType : ScreenBottomSheetType {
    data object None : OpenSourceLicensesScreenBottomSheetType()
}
