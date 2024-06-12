package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndEvents

@Stable
internal class OpenSourceLicensesScreenUIStateAndStateEvents(
    val state: OpenSourceLicensesScreenUIState = OpenSourceLicensesScreenUIState(),
    val events: OpenSourceLicensesScreenUIStateEvents = OpenSourceLicensesScreenUIStateEvents(),
) : ScreenUIStateAndEvents
