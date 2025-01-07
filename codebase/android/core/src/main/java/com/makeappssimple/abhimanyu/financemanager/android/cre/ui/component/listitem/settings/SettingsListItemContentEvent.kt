package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.listitem.settings

import androidx.compose.runtime.Immutable

@Immutable
public sealed class SettingsListItemContentEvent {
    public data object OnClick : SettingsListItemContentEvent()
    public data object OnCheckedChange : SettingsListItemContentEvent()
}
