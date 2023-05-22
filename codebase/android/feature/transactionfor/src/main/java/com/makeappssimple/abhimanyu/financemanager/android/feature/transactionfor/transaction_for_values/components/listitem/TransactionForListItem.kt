package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.components.listitem

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions.conditionalClickable

@Immutable
data class TransactionForListItemData(
    val isDeleteVisible: Boolean,
    val title: String,
)

@Immutable
data class TransactionForListItemEvents(
    val onClick: () -> Unit,
)

@Composable
fun TransactionForListItem(
    modifier: Modifier = Modifier,
    data: TransactionForListItemData,
    events: TransactionForListItemEvents,
) {
    MyText(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 8.dp,
            )
            .clip(
                shape = MaterialTheme.shapes.large,
            )
            .conditionalClickable(
                onClick = events.onClick,
            )
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp,
            ),
        text = data.title,
        style = MaterialTheme.typography.bodyLarge
            .copy(
                color = MaterialTheme.colorScheme.onBackground,
            ),
    )
}
