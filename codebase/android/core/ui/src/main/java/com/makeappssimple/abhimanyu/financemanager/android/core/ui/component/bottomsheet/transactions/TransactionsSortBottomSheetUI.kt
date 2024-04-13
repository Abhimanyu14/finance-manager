package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactions

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.icons.MyIcons
import com.makeappssimple.abhimanyu.financemanager.android.core.model.feature.SortOption
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common.MyBottomSheetTitle
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.minimumBottomSheetHeight

@Immutable
public data class TransactionsSortBottomSheetData(
    val data: TransactionsSortBottomSheetItemData,
    val events: TransactionsSortBottomSheetItemEvents,
)

@Immutable
public data class TransactionsSortBottomSheetItemData(
    val sortOption: SortOption,
    val isSelected: Boolean,
)

@Immutable
public data class TransactionsSortBottomSheetItemEvents(
    val onClick: () -> Unit,
)

@Composable
public fun TransactionsSortBottomSheetUI(
    modifier: Modifier = Modifier,
    data: List<TransactionsSortBottomSheetData>,
) {
    LazyColumn(
        modifier = modifier
            .defaultMinSize(
                minHeight = minimumBottomSheetHeight,
            ),
    ) {
        stickyHeader {
            MyBottomSheetTitle(
                textStringResourceId = R.string.bottom_sheet_transactions_sort_title,
            )
        }
        items(
            items = data,
            key = { listItem ->
                listItem.hashCode()
            },
        ) { listItem ->
            TransactionsSortBottomSheetItem(
                data = listItem.data,
                events = listItem.events,
            )
        }
        item {
            NavigationBarsAndImeSpacer()
        }
    }
}

@Composable
private fun TransactionsSortBottomSheetItem(
    data: TransactionsSortBottomSheetItemData,
    events: TransactionsSortBottomSheetItemEvents,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .conditionalClickable(
                onClickLabel = data.sortOption.title,
                role = Role.Button,
                onClick = events.onClick,
            )
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
    ) {
        MyText(
            modifier = Modifier
                .padding(
                    vertical = 6.dp,
                ),
            text = data.sortOption.title,
            style = MaterialTheme.typography.headlineMedium
                .copy(
                    color = if (data.isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onBackground
                    },
                ),
        )
        Spacer(
            modifier = Modifier
                .weight(
                    weight = 1F,
                ),
        )
        if (data.isSelected) {
            Icon(
                imageVector = MyIcons.Done,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
            )
        }
    }
}
