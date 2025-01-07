package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.listitem.settings

import androidx.compose.runtime.Immutable

@Immutable
public data class SettingsListItemAppVersionData(
    override val type: SettingsListItemType = SettingsListItemType.APP_VERSION,
    val appVersionText: String,
) : SettingsListItemData
