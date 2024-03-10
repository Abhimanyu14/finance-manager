package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.accounts

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText

@Immutable
data class AccountsListItemHeaderDataAndEvents(
    val data: AccountsListItemHeaderData,
    val events: AccountsListItemHeaderEvents,
)

@Immutable
data class AccountsListItemHeaderData(
    override val type: AccountsListItemType = AccountsListItemType.HEADER,
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
data class AccountsListItemHeaderEvents(
    val onClick: () -> Unit,
    val onLongClick: () -> Unit = {},
    val onEditClick: () -> Unit = {},
    val onDeleteClick: () -> Unit = {},
)

@Composable
fun AccountsListItemHeader(
    modifier: Modifier = Modifier,
    data: AccountsListItemHeaderData,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clip(
                shape = MaterialTheme.shapes.large,
            )
            .padding(
                start = 24.dp,
                end = 24.dp,
                top = 16.dp,
                bottom = 0.dp,
            ),
    ) {
        MyText(
            modifier = Modifier
                .padding(
                    end = 16.dp,
                ),
            text = data.name,
            style = MaterialTheme.typography.headlineMedium
                .copy(
                    color = MaterialTheme.colorScheme.onBackground,
                ),
        )
        Spacer(
            modifier = Modifier
                .weight(
                    weight = 1F,
                ),
        )
        data.balance?.let {
            MyText(
                text = data.balance,
                style = MaterialTheme.typography.headlineMedium
                    .copy(
                        color = MaterialTheme.colorScheme.onBackground,
                    ),
            )
        }
    }
}
