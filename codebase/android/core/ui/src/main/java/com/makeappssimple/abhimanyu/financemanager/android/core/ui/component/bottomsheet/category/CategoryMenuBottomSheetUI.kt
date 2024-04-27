package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.category

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions.conditionalClickable

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.minimumBottomSheetHeight

@Immutable
public data class CategoryMenuBottomSheetItemData(
    val text: String,
    val onClick: () -> Unit,
)

@Composable
public fun CategoryMenuBottomSheetUI(
    modifier: Modifier = Modifier,
    items: List<CategoryMenuBottomSheetItemData>,
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
            CategoryMenuBottomSheetItem(
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
private fun CategoryMenuBottomSheetItem(
    data: CategoryMenuBottomSheetItemData,
) {
    Row(
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
