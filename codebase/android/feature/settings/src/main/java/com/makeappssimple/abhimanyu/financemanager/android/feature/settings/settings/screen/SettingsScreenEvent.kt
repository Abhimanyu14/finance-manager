package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenEvent

internal sealed class SettingsScreenEvent : ScreenEvent {
    data object RestoreDataFailed : SettingsScreenEvent()
}
