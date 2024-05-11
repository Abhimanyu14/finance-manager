package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.settings

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Immutable
public data class SettingsListItemDividerData(
    override val type: SettingsListItemType = SettingsListItemType.DIVIDER,
) : SettingsListItemData

@Composable
public fun SettingsListItemDivider(
    modifier: Modifier = Modifier,
) {
    HorizontalDivider(
        modifier = modifier
            .padding(
                bottom = 24.dp,
                start = 16.dp,
            ),
        color = MaterialTheme.colorScheme.outline,
        thickness = 1.dp,
    )
}
