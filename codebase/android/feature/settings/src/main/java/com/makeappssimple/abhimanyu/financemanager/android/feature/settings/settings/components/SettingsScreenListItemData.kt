package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.settings.components

import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.listitem.settings.SettingsListItemContentEvent
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.listitem.settings.SettingsListItemData

@Immutable
internal data class SettingsScreenListItemData(
    val data: SettingsListItemData,
    val handleEvent: (event: SettingsListItemContentEvent) -> Unit = {},
)
