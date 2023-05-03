package com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.bottom_sheet.select_source

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
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions.conditionalClickable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.bottom_sheet.MyBottomSheetTitle

@Immutable
internal data class SelectSourceBottomSheetItemData(
    val isSelected: Boolean,
    val icon: ImageVector,
    val text: String,
    val onClick: () -> Unit,
)

@Composable
internal fun SelectSourceBottomSheet(
    modifier: Modifier = Modifier,
    items: List<SelectSourceBottomSheetItemData>,
) {
    LazyColumn(
        modifier = modifier
            .defaultMinSize(
                minHeight = 24.dp,
            ),
    ) {
        stickyHeader {
            MyBottomSheetTitle(
                textStringResourceId = R.string.bottom_sheet_select_source_title,
            )
        }
        items(
            items = items,
            key = { listItem ->
                listItem.hashCode()
            },
        ) { listItem ->
            SelectSourceBottomSheetItem(
                data = listItem,
            )
        }
    }
}

@Composable
private fun SelectSourceBottomSheetItem(
    data: SelectSourceBottomSheetItemData,
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
                horizontal = 16.dp,
                vertical = 8.dp,
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
            modifier = Modifier,
            text = data.text,
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
