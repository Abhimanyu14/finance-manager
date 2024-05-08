package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.screen.AnalysisScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.screen.AnalysisScreenUIEvent
import kotlinx.coroutines.flow.StateFlow

public interface AnalysisScreenViewModel : ScreenViewModel {
    public val screenUIData: StateFlow<MyResult<AnalysisScreenUIData>?>

    public fun handleUIEvent(
        uiEvent: AnalysisScreenUIEvent,
    )
}
