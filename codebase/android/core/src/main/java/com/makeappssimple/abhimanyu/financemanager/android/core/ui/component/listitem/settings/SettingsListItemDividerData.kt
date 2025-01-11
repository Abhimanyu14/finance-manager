package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.settings

import androidx.compose.runtime.Immutable

@Immutable
public data class SettingsListItemDividerData(
    override val type: SettingsListItemType = SettingsListItemType.DIVIDER,
) : SettingsListItemData
