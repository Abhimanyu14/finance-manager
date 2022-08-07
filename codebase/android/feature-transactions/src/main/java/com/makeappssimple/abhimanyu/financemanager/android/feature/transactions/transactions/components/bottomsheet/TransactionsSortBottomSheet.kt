package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.components.bottomsheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.bottom_sheet.BottomSheetTitle
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel.SortOption

data class TransactionsSortBottomSheetItemData(
    val sortOption: SortOption,
    val isSelected: Boolean,
    val onClick: () -> Unit,
)

data class TransactionsSortBottomSheetData(
    val items: List<TransactionsSortBottomSheetItemData>,
)

@Composable
fun TransactionsSortBottomSheet(
    data: TransactionsSortBottomSheetData,
) {
    LazyColumn(
        modifier = Modifier
            .defaultMinSize(
                minHeight = 24.dp,
            ),
    ) {
        stickyHeader {
            BottomSheetTitle(
                textStringResourceId = R.string.bottom_sheet_transactions_sort_title,
            )
        }
        items(
            items = data.items,
            key = { listItem ->
                listItem.hashCode()
            },
        ) { listItem ->
            TransactionsSortBottomSheetItem(
                data = listItem,
            )
        }
    }
}

@Composable
private fun TransactionsSortBottomSheetItem(
    data: TransactionsSortBottomSheetItemData,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClickLabel = data.sortOption.title,
                role = Role.Button,
                onClick = data.onClick,
            )
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
    ) {
        MyText(
            modifier = Modifier
                .padding(
                    horizontal = 0.dp,
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
                imageVector = Icons.Rounded.Done,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
            )
        }
    }
}
