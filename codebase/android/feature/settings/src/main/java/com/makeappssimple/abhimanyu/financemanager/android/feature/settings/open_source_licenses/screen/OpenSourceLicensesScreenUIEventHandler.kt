package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.screen

import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.viewmodel.OpenSourceLicensesScreenViewModel

public class OpenSourceLicensesScreenUIEventHandler internal constructor(
    private val viewModel: OpenSourceLicensesScreenViewModel,
    private val uiStateAndEvents: OpenSourceLicensesScreenUIStateAndEvents,
) {
    public fun handleUIEvent(
        uiEvent: OpenSourceLicensesScreenUIEvent,
    ) {
        when (uiEvent) {
            is OpenSourceLicensesScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateAndEvents.events.resetScreenBottomSheetType()
            }

            is OpenSourceLicensesScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                viewModel.navigateUp()
            }
        }
    }
}
