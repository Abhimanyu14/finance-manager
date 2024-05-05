package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.icons.MyIcons
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.default_tag.MyDefaultTag
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.minimumListItemHeight

@Immutable
public data class AccountsListItemContentDataAndEvents(
    val data: AccountsListItemContentData,
    val events: AccountsListItemContentEvents,
)

@Immutable
public data class AccountsListItemContentData(
    override val type: AccountsListItemType = AccountsListItemType.CONTENT,
    val isDefault: Boolean = false,
    val isDeleteEnabled: Boolean = false,
    val isHeading: Boolean = false,
    val isLowBalance: Boolean = false,
    val isMoreOptionsIconButtonVisible: Boolean = false,
    val isSelected: Boolean = false,
    val icon: ImageVector? = null,
    val accountId: Int? = null,
    val balance: String? = null,
    val name: String,
) : AccountsListItemData

@Immutable
public data class AccountsListItemContentEvents(
    val onClick: () -> Unit,
)

@Composable
public fun AccountsListItemContent(
    modifier: Modifier = Modifier,
    data: AccountsListItemContentData,
    events: AccountsListItemContentEvents,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(
                minHeight = minimumListItemHeight,
            )
            .conditionalClickable(
                onClick = events.onClick,
            )
            .padding(
                start = 16.dp,
                end = 16.dp,
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
        if (data.isMoreOptionsIconButtonVisible) {
            Icon(
                imageVector = MyIcons.MoreVert,
                contentDescription = stringResource(
                    id = R.string.account_list_item_more_options_content_description,
                ),
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                    ),
            )
        }
    }
}
