package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.analysis.Filter
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
sealed class AnalysisScreenUIEvent : ScreenUIEvent {
    data object NavigateUp : AnalysisScreenUIEvent()

    data class UpdateSelectedFilter(
        val updatedSelectedFilter: Filter,
    ) : AnalysisScreenUIEvent()

    data class UpdateSelectedTransactionTypeIndex(
        val updatedSelectedTransactionTypeIndex: Int,
    ) : AnalysisScreenUIEvent()
}
