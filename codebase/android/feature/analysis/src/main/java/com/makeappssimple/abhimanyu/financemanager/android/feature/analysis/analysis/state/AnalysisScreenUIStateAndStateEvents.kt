package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndStateEvents

@Stable
internal class AnalysisScreenUIStateAndStateEvents(
    val state: AnalysisScreenUIState = AnalysisScreenUIState(),
    val events: AnalysisScreenUIStateEvents = AnalysisScreenUIStateEvents(),
) : ScreenUIStateAndStateEvents
