package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.event

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEventHandler
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.bottomsheet.AnalysisScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.state.AnalysisScreenUIStateEvents

internal class AnalysisScreenUIEventHandler internal constructor(
    private val uiStateEvents: AnalysisScreenUIStateEvents,
) : ScreenUIEventHandler<AnalysisScreenUIEvent> {
    override fun handleUIEvent(
        uiEvent: AnalysisScreenUIEvent,
    ) {
        when (uiEvent) {
            is AnalysisScreenUIEvent.OnFilterActionButtonClick -> {
                uiStateEvents.setScreenBottomSheetType(AnalysisScreenBottomSheetType.Filters)
            }

            is AnalysisScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
            }

            is AnalysisScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateEvents.navigateUp()
            }

            is AnalysisScreenUIEvent.OnAnalysisFilterBottomSheet.PositiveButtonClick -> {
                uiStateEvents.setSelectedFilter(uiEvent.updatedSelectedFilter)
                uiStateEvents.resetScreenBottomSheetType()
            }

            is AnalysisScreenUIEvent.OnTransactionTypeChange -> {
                uiStateEvents.setSelectedTransactionTypeIndex(uiEvent.updatedSelectedTransactionTypeIndex)
            }
        }
    }
}
