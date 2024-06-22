package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.event

import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.bottomsheet.AnalysisScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.analysis.state.AnalysisScreenUIStateAndStateEvents

internal class AnalysisScreenUIEventHandler internal constructor(
    private val uiStateAndStateEvents: AnalysisScreenUIStateAndStateEvents,
) {
    fun handleUIEvent(
        uiEvent: AnalysisScreenUIEvent,
    ) {
        when (uiEvent) {
            is AnalysisScreenUIEvent.OnFilterActionButtonClick -> {
                uiStateAndStateEvents.events.setScreenBottomSheetType(AnalysisScreenBottomSheetType.Filters)
            }

            is AnalysisScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
            }

            is AnalysisScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateAndStateEvents.events.navigateUp()
            }

            is AnalysisScreenUIEvent.OnAnalysisFilterBottomSheet.PositiveButtonClick -> {
                uiStateAndStateEvents.events.setSelectedFilter(uiEvent.updatedSelectedFilter)
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
            }

            is AnalysisScreenUIEvent.OnTransactionTypeChange -> {
                uiStateAndStateEvents.events.setSelectedTransactionTypeIndex(uiEvent.updatedSelectedTransactionTypeIndex)
            }
        }
    }
}
