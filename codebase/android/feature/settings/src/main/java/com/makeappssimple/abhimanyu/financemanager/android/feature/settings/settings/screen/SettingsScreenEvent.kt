package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.screen

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenEvent

public sealed class SettingsScreenEvent : ScreenEvent {
    public data object RestoreDataFailed : SettingsScreenEvent()
}
