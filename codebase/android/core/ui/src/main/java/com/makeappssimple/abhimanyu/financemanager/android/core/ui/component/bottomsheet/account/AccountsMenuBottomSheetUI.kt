package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.account

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions.conditionalClickable

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.minimumBottomSheetHeight

@Immutable
public data class AccountsMenuBottomSheetItemData(
    val imageVector: ImageVector? = null,
    val text: String,
    val onClick: () -> Unit,
)

@Composable
public fun AccountsMenuBottomSheetUI(
    modifier: Modifier = Modifier,
    items: List<AccountsMenuBottomSheetItemData>,
) {
    LazyColumn(
        modifier = modifier
            .padding(
                top = 16.dp,
            )
            .defaultMinSize(
                minHeight = minimumBottomSheetHeight,
            ),
    ) {
        items(
            items = items,
            key = { listItem ->
                listItem.hashCode()
            },
        ) { listItem ->
            AccountsMenuBottomSheetItem(
                data = listItem,
            )
        }
        item {
            NavigationBarsAndImeSpacer()
        }
        item {
            VerticalSpacer(
                height = 16.dp,
            )
        }
    }
}

@Composable
private fun AccountsMenuBottomSheetItem(
    data: AccountsMenuBottomSheetItemData,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .conditionalClickable(
                onClickLabel = data.text,
                role = Role.Button,
                onClick = data.onClick,
            )
            .padding(
                all = 16.dp,
            ),
    ) {
        data.imageVector?.let {
            Icon(
                imageVector = data.imageVector,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(
                        end = 12.dp,
                    ),
            )
        }
        MyText(
            modifier = Modifier,
            text = data.text,
            style = MaterialTheme.typography.headlineMedium
                .copy(
                    color = MaterialTheme.colorScheme.onBackground,
                ),
        )
    }
}
