package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.analysis.Filter
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
public sealed class AnalysisScreenUIEvent : ScreenUIEvent {
    public data object NavigateUp : AnalysisScreenUIEvent()

    public data class UpdateSelectedFilter(
        val updatedSelectedFilter: Filter,
    ) : AnalysisScreenUIEvent()

    public data class UpdateSelectedTransactionTypeIndex(
        val updatedSelectedTransactionTypeIndex: Int,
    ) : AnalysisScreenUIEvent()
}
