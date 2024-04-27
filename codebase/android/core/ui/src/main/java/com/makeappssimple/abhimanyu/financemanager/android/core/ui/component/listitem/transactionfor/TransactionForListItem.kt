package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.transactionfor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.button.MyIconButton
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.icons.MyIcons
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R

@Immutable
public data class TransactionForListItemDataAndEvents(
    val data: TransactionForListItemData,
    val events: TransactionForListItemEvents,
)

@Immutable
public data class TransactionForListItemData(
    val title: String,
)

@Immutable
public data class TransactionForListItemEvents(
    val onClick: () -> Unit,
    val onMoreOptionsIconButtonClick: (() -> Unit)? = null,
)

@Composable
public fun TransactionForListItem(
    modifier: Modifier = Modifier,
    data: TransactionForListItemData,
    events: TransactionForListItemEvents,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(
                minHeight = 48.dp,
            )
            .conditionalClickable(
                onClick = events.onClick,
            )
            .padding(
                start = 16.dp,
                end = 8.dp,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        MyText(
            text = data.title,
            style = MaterialTheme.typography.bodyLarge
                .copy(
                    color = MaterialTheme.colorScheme.onBackground,
                ),
        )
        events.onMoreOptionsIconButtonClick?.let {
            MyIconButton(
                tint = MaterialTheme.colorScheme.onBackground,
                imageVector = MyIcons.MoreVert,
                contentDescriptionStringResourceId = R.string.transaction_for_list_item_more_options_content_description,
                onClick = events.onMoreOptionsIconButtonClick,
            )
        }
    }
}
