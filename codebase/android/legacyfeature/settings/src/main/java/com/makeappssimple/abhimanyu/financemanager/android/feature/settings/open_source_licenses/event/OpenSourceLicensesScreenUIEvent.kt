package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.event

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
internal sealed class OpenSourceLicensesScreenUIEvent : ScreenUIEvent {
    data object OnNavigationBackButtonClick : OpenSourceLicensesScreenUIEvent()
    data object OnTopAppBarNavigationButtonClick : OpenSourceLicensesScreenUIEvent()
}
