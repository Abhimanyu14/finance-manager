package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.open_source_licenses.screen

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEvent

@Immutable
sealed class OpenSourceLicensesScreenUIEvent : ScreenUIEvent {
    data object NavigateUp : OpenSourceLicensesScreenUIEvent()
}
