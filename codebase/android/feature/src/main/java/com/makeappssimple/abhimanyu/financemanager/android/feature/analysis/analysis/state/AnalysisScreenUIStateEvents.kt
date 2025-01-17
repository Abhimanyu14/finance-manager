package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.analysis.Filter
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.bottomsheet.AnalysisScreenBottomSheetType

@Stable
internal class AnalysisScreenUIStateEvents(
    val navigateUp: () -> Unit = {},
    val resetScreenBottomSheetType: () -> Unit = {},
    val updateScreenBottomSheetType: (AnalysisScreenBottomSheetType) -> Unit = {},
    val updateSelectedFilter: (updatedSelectedFilter: Filter) -> Unit = {},
    val updateSelectedTransactionTypeIndex: (updatedSelectedTransactionTypeIndex: Int) -> Unit = {},
) : ScreenUIStateEvents
