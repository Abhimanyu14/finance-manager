package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.event

import com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.state.OpenSourceLicensesScreenUIStateEvents

internal class OpenSourceLicensesScreenUIEventHandler internal constructor(
    private val uiStateEvents: OpenSourceLicensesScreenUIStateEvents,
) {
    fun handleUIEvent(
        uiEvent: OpenSourceLicensesScreenUIEvent,
    ) {
        when (uiEvent) {
            is OpenSourceLicensesScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
            }

            is OpenSourceLicensesScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateEvents.navigateUp()
            }
        }
    }
}
