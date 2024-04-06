package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
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
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.icons.MyIcons
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.ExpandedListItemShape
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyExpandableItemIconButton
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.MyExpandableItemUIWrapper
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.default_tag.MyDefaultTag

@Immutable
data class AccountsListItemContentDataAndEvents(
    val data: AccountsListItemContentData,
    val events: AccountsListItemContentEvents,
)

@Immutable
data class AccountsListItemContentData(
    override val type: AccountsListItemType = AccountsListItemType.CONTENT,
    val isDefault: Boolean = false,
    val isDeleteEnabled: Boolean = false,
    val isExpanded: Boolean = false,
    val isHeading: Boolean = false,
    val isLowBalance: Boolean = false,
    val isSelected: Boolean = false,
    val icon: ImageVector? = null,
    val accountId: Int? = null,
    val balance: String? = null,
    val name: String,
) : AccountsListItemData

@Immutable
data class AccountsListItemContentEvents(
    val onClick: () -> Unit,
    val onLongClick: () -> Unit = {},
    val onEditClick: () -> Unit = {},
    val onDeleteClick: () -> Unit = {},
)

@Composable
fun AccountsListItemContent(
    modifier: Modifier = Modifier,
    data: AccountsListItemContentData,
    events: AccountsListItemContentEvents,
) {
    MyExpandableItemUIWrapper(
        isExpanded = data.isExpanded,
        modifier = modifier,
    ) {
        AccountsListItemCollapsed(
            data = data,
            events = events,
        )
        if (data.isExpanded) {
            HorizontalDivider(
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
                    iconImageVector = MyIcons.Edit,
                    labelText = stringResource(
                        id = R.string.list_item_accounts_edit,
                    ),
                    enabled = true,
                    onClick = events.onEditClick,
                    modifier = Modifier
                        .weight(
                            weight = 1F,
                        ),
                )
                MyExpandableItemIconButton(
                    iconImageVector = MyIcons.Delete,
                    labelText = stringResource(
                        id = R.string.list_item_accounts_delete,
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

@Composable
internal fun AccountsListItemCollapsed(
    data: AccountsListItemContentData,
    events: AccountsListItemContentEvents,
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
        data.icon?.let {
            Icon(
                imageVector = data.icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(
                        end = 8.dp,
                    ),
            )
        }
        MyText(
            modifier = Modifier
                .padding(
                    end = 16.dp,
                ),
            text = data.name,
            style = MaterialTheme.typography.headlineLarge
                .copy(
                    color = if (data.isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onBackground
                    },
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
        data.balance?.let {
            MyText(
                text = data.balance,
                style = MaterialTheme.typography.headlineLarge
                    .copy(
                        color = if (data.isSelected) {
                            MaterialTheme.colorScheme.primary
                        } else if (data.isLowBalance) {
                            MaterialTheme.colorScheme.error
                        } else {
                            MaterialTheme.colorScheme.onBackground
                        },
                    ),
            )
        }
    }
}
