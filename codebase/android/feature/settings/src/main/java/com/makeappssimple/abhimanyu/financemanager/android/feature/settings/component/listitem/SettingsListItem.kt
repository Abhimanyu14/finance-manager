package com.makeappssimple.abhimanyu.financemanager.android.feature.settings.component.listitem

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions.conditionalClickable

@Immutable
data class SettingsListItemData(
    val isLoading: Boolean,
    val imageVector: ImageVector,
    @StringRes val textStringResourceId: Int,
)

@Immutable
data class SettingsListItemEvents(
    val onClick: () -> Unit,
)

@Composable
internal fun SettingsListItem(
    modifier: Modifier = Modifier,
    data: SettingsListItemData,
    events: SettingsListItemEvents,
) {
    ListItem(
        leadingContent = {
            Icon(
                imageVector = data.imageVector,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
            )
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
        modifier = modifier
            .conditionalClickable(
                onClick = if (data.isLoading) {
                    null
                } else {
                    events.onClick
                },
            ),
    )
}
