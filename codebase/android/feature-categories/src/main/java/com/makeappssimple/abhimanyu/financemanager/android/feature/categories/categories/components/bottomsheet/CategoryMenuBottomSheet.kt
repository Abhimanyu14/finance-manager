package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.components.bottomsheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText

internal data class CategoryMenuBottomSheetItemData(
    val text: String,
    val onClick: () -> Unit,
)

@Composable
internal fun CategoryMenuBottomSheet(
    modifier: Modifier = Modifier,
    items: List<CategoryMenuBottomSheetItemData>,
) {
    LazyColumn(
        modifier = modifier
            .padding(
                top = 16.dp,
            )
            .defaultMinSize(
                minHeight = 24.dp,
            ),
    ) {
        items(
            items = items,
            key = { listItem ->
                listItem.hashCode()
            },
        ) { listItem ->
            CategoryMenuBottomSheetItem(
                data = listItem,
            )
        }
    }
}

@Composable
private fun CategoryMenuBottomSheetItem(
    data: CategoryMenuBottomSheetItemData,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClickLabel = data.text,
                role = Role.Button,
                onClick = data.onClick,
            )
            .padding(
                all = 16.dp,
            ),
    ) {
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