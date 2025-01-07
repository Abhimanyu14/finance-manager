package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.settings

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector

@Immutable
public data class SettingsListItemContentData(
    override val type: SettingsListItemType = SettingsListItemType.CONTENT,
    val isChecked: Boolean? = null,
    val isEnabled: Boolean = false,
    val hasToggle: Boolean = false,
    val imageVector: ImageVector? = null,
    @StringRes val textStringResourceId: Int,
) : SettingsListItemData
