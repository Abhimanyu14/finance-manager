package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.listitem.settings

import androidx.annotation.StringRes

public data class SettingsListItemHeaderData(
    override val type: SettingsListItemType = SettingsListItemType.HEADER,
    @StringRes val textStringResourceId: Int,
) : SettingsListItemData
