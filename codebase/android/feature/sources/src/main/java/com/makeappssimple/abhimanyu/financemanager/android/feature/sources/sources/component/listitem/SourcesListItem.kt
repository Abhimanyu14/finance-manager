package com.makeappssimple.abhimanyu.financemanager.android.feature.sources.sources.component.listitem

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.ExpandedListItemShape
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyDefaultTag
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyExpandableItemIconButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyExpandableItemViewWrapper
import com.makeappssimple.abhimanyu.financemanager.android.feature.sources.R

@Immutable
data class SourcesListItemData(
    val icon: ImageVector,
    val sourceId: Int,
    val balance: String,
    val name: String,
    val isDefault: Boolean,
    val isDeleteEnabled: Boolean,
    val isExpanded: Boolean,
)

@Immutable
internal data class SourcesListItemEvents(
    val onClick: () -> Unit,
    val onLongClick: () -> Unit,
    val onEditClick: () -> Unit,
    val onDeleteClick: () -> Unit,
)

@Composable
internal fun SourcesListItem(
    modifier: Modifier = Modifier,
    data: SourcesListItemData,
    events: SourcesListItemEvents,
) {
    MyExpandableItemViewWrapper(
        expanded = data.isExpanded,
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    shape = if (data.isExpanded) {
                        ExpandedListItemShape
                    } else {
                        MaterialTheme.shapes.large
                    },
                )
                .conditionalClickable(
                    onClick = events.onClick,
                    onLongClick = events.onLongClick,
                )
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = if (data.isExpanded) {
                        16.dp
                    } else {
                        8.dp
                    },
                    bottom = if (data.isExpanded) {
                        16.dp
                    } else {
                        8.dp
                    },
                ),
        ) {
            Icon(
                imageVector = data.icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(
                        end = 8.dp,
                    ),
            )
            MyText(
                modifier = Modifier
                    .padding(
                        end = 16.dp,
                    ),
                text = data.name,
                style = MaterialTheme.typography.headlineLarge
                    .copy(
                        color = MaterialTheme.colorScheme.onBackground,
                    ),
            )
            if (data.isDefault) {
                MyDefaultTag()
            }
            Spacer(
                modifier = Modifier
                    .weight(
                        weight = 1F,
                    ),
            )
            MyText(
                text = data.balance,
                style = MaterialTheme.typography.headlineLarge
                    .copy(
                        color = MaterialTheme.colorScheme.onBackground,
                    ),
            )
        }
        if (data.isExpanded) {
            Divider(
                color = MaterialTheme.colorScheme.outline,
                thickness = 0.5.dp,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 8.dp,
                        bottom = 8.dp,
                    ),
            ) {
                MyExpandableItemIconButton(
                    iconImageVector = Icons.Rounded.Edit,
                    labelText = stringResource(
                        id = R.string.list_item_sources_edit,
                    ),
                    enabled = true,
                    onClick = events.onEditClick,
                    modifier = Modifier
                        .weight(
                            weight = 1F,
                        ),
                )
                MyExpandableItemIconButton(
                    iconImageVector = Icons.Rounded.Delete,
                    labelText = stringResource(
                        id = R.string.list_item_sources_delete,
                    ),
                    enabled = data.isDeleteEnabled,
                    onClick = events.onDeleteClick,
                    modifier = Modifier
                        .weight(
                            weight = 1F,
                        ),
                )
            }
        }
    }
}
