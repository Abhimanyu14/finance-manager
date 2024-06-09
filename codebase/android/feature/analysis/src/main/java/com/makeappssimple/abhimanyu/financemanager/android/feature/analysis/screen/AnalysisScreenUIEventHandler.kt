package com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.screen

import com.makeappssimple.abhimanyu.financemanager.android.feature.analysis.viewmodel.AnalysisScreenViewModel

public class AnalysisScreenUIEventHandler internal constructor(
    private val viewModel: AnalysisScreenViewModel,
    private val uiStateAndEvents: AnalysisScreenUIStateAndEvents,
) {
    public fun handleUIEvent(
        uiEvent: AnalysisScreenUIEvent,
    ) {
        when (uiEvent) {
            is AnalysisScreenUIEvent.OnFilterActionButtonClick -> {
                uiStateAndEvents.events.setScreenBottomSheetType(AnalysisScreenBottomSheetType.Filters)
            }

            is AnalysisScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateAndEvents.events.resetScreenBottomSheetType()
            }

            is AnalysisScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                viewModel.navigateUp()
            }

            is AnalysisScreenUIEvent.OnAnalysisFilterBottomSheet.PositiveButtonClick -> {
                viewModel.updateSelectedFilter(
                    updatedSelectedFilter = uiEvent.updatedSelectedFilter,
                )
                uiStateAndEvents.events.resetScreenBottomSheetType()
            }

            is AnalysisScreenUIEvent.OnTransactionTypeChange -> {
                viewModel.updateSelectedTransactionTypeIndex(
                    updatedSelectedTransactionTypeIndex = uiEvent.updatedSelectedTransactionTypeIndex,
                )
            }
        }
    }
}
