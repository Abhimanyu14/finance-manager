package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.feature.analysis.Filter
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.bottomsheet.AnalysisScreenBottomSheetType

@Stable
internal class AnalysisScreenUIStateEvents(
    val navigateUp: () -> Unit = {},
    val resetScreenBottomSheetType: () -> Unit = {},
    val setScreenBottomSheetType: (AnalysisScreenBottomSheetType) -> Unit = {},
    val setSelectedFilter: (updatedSelectedFilter: Filter) -> Unit = {},
    val setSelectedTransactionTypeIndex: (updatedSelectedTransactionTypeIndex: Int) -> Unit = {},
) : ScreenUIStateEvents
