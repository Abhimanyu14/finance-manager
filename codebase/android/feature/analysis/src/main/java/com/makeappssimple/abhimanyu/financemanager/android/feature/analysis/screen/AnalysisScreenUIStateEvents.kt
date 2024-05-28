package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.screen

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents

@Stable
internal class AnalysisScreenUIStateEvents(
    val resetScreenBottomSheetType: () -> Unit,
    val setScreenBottomSheetType: (AnalysisScreenBottomSheetType) -> Unit,
    // val setSelectedFilter: (updatedSelectedFilter: Filter) -> Unit,
) : ScreenUIStateEvents
