package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.event

import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.state.OpenSourceLicensesScreenUIStateAndStateEvents

public class OpenSourceLicensesScreenUIEventHandler internal constructor(
    private val uiStateAndStateEvents: OpenSourceLicensesScreenUIStateAndStateEvents,
) {
    public fun handleUIEvent(
        uiEvent: OpenSourceLicensesScreenUIEvent,
    ) {
        when (uiEvent) {
            is OpenSourceLicensesScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
            }

            is OpenSourceLicensesScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateAndStateEvents.events.navigateUp()
            }
        }
    }
}
