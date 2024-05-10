package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.analysis.Filter
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
public sealed class AnalysisScreenUIEvent : ScreenUIEvent {
    public data object OnBottomSheetDismissed : AnalysisScreenUIEvent()
    public data object OnFilterActionButtonClick : AnalysisScreenUIEvent()
    public data object OnNavigationBackButtonClick : AnalysisScreenUIEvent()
    public data object OnTopAppBarNavigationButtonClick : AnalysisScreenUIEvent()

    public data class OnTransactionTypeChange(
        val updatedSelectedTransactionTypeIndex: Int,
    ) : AnalysisScreenUIEvent()

    public sealed class OnAnalysisFilterBottomSheet {
        public data class PositiveButtonClick(
            val updatedSelectedFilter: Filter,
        ) : AnalysisScreenUIEvent()
    }
}
