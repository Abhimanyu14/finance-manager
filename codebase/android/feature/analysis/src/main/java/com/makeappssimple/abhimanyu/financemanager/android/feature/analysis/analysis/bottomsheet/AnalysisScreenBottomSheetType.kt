package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.bottomsheet

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenBottomSheetType

internal sealed class AnalysisScreenBottomSheetType : ScreenBottomSheetType {
    data object Filters : AnalysisScreenBottomSheetType()
    data object None : AnalysisScreenBottomSheetType()
}
