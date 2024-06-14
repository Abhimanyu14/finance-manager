package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.state

import androidx.compose.runtime.Stable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndEvents

@Stable
internal class SettingsScreenUIStateAndStateEvents(
    val state: SettingsScreenUIState = SettingsScreenUIState(),
    val events: SettingsScreenUIStateEvents = SettingsScreenUIStateEvents(),
) : ScreenUIStateAndEvents
