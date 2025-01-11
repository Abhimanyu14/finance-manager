package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.event

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.feature.analysis.Filter
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenUIEvent

@Immutable
internal sealed class AnalysisScreenUIEvent : ScreenUIEvent {
    data object OnFilterActionButtonClick : AnalysisScreenUIEvent()
    data object OnNavigationBackButtonClick : AnalysisScreenUIEvent()
    data object OnTopAppBarNavigationButtonClick : AnalysisScreenUIEvent()

    data class OnTransactionTypeChange(
        val updatedSelectedTransactionTypeIndex: Int,
    ) : AnalysisScreenUIEvent()

    sealed class OnAnalysisFilterBottomSheet {
        data class PositiveButtonClick(
            val updatedSelectedFilter: Filter,
        ) : AnalysisScreenUIEvent()
    }
}
