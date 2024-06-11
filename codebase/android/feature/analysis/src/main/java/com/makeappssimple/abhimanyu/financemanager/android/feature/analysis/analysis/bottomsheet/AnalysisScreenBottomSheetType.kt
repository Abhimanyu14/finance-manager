package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.bottomsheet

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenBottomSheetType

public sealed class AnalysisScreenBottomSheetType : ScreenBottomSheetType {
    public data object Filters : AnalysisScreenBottomSheetType()
    public data object None : AnalysisScreenBottomSheetType()
}
