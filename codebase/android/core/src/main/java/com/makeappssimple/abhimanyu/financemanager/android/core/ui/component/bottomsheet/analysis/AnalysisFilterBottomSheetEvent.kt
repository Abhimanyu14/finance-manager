package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.analysis

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.analysis.Filter

@Immutable
public sealed class AnalysisFilterBottomSheetEvent {
    public data object OnNegativeButtonClick : AnalysisFilterBottomSheetEvent()
    public data class OnPositiveButtonClick(
        val updatedFilter: Filter,
    ) : AnalysisFilterBottomSheetEvent()
}
