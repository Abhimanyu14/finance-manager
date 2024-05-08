package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.settings

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.icons.MyIcons

@Immutable
public data class SettingsListItemContentData(
    override val type: SettingsListItemType = SettingsListItemType.CONTENT,
    val isChecked: Boolean? = null,
    val isEnabled: Boolean = false,
    val hasToggle: Boolean = false,
    val imageVector: ImageVector? = null,
    @StringRes val textStringResourceId: Int,
) : SettingsListItemData

@Immutable
public sealed class SettingsListItemContentEvent {
    public data object OnClick : SettingsListItemContentEvent()
    public data object OnCheckedChange : SettingsListItemContentEvent()
}

@Composable
public fun SettingsListItemContent(
    modifier: Modifier = Modifier,
    data: SettingsListItemContentData,
    handleEvent: (event: SettingsListItemContentEvent) -> Unit = {},
) {
    ListItem(
        leadingContent = if (data.imageVector == null) {
            null
        } else {
            {
                Icon(
                    imageVector = data.imageVector,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }
        },
        headlineContent = {
            MyText(
                modifier = Modifier
                    .fillMaxWidth(),
                textStringResourceId = data.textStringResourceId,
                style = MaterialTheme.typography.bodyLarge
                    .copy(
                        color = MaterialTheme.colorScheme.onBackground,
                    ),
            )
        },
        trailingContent = if (data.hasToggle) {
            {
                // TODO(Abhi): Create a wrapper for this M3 component
                Switch(
                    checked = data.isChecked.orFalse(),
                    onCheckedChange = {
                        handleEvent(
                            SettingsListItemContentEvent.OnCheckedChange
                        )
                    },
                    thumbContent = if (data.isChecked.orFalse()) {
                        {
                            Icon(
                                imageVector = MyIcons.Check,
                                contentDescription = null,
                                modifier = Modifier.size(SwitchDefaults.IconSize),
                            )
                        }
                    } else {
                        null
                    },
                    colors = SwitchDefaults.colors(
                        uncheckedThumbColor = MaterialTheme.colorScheme.background,
                    ),
                )
            }
        } else {
            null
        },
        modifier = modifier
            .conditionalClickable(
                onClick = if (data.isEnabled) {
                    {
                        handleEvent(SettingsListItemContentEvent.OnClick)
                    }
                } else {
                    null
                },
            ),
    )
}

