package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.select_list_item

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.NavigationBarSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.MyBottomSheetTitle

@Immutable
internal data class SelectListItemBottomSheetUIData(
    val titleTextStringResourceId: Int = 0,
    val items: List<SelectListItemBottomSheetItemData> = emptyList(),
)

@Composable
internal fun SelectListItemBottomSheetUI(
    modifier: Modifier = Modifier,
    data: SelectListItemBottomSheetUIData,
) {
    LazyColumn(
        modifier = modifier
            .defaultMinSize(
                minHeight = 24.dp,
            ),
    ) {
        stickyHeader {
            MyBottomSheetTitle(
                textStringResourceId = data.titleTextStringResourceId,
            )
        }
        items(
            items = data.items,
            key = { listItem ->
                listItem.hashCode()
            },
        ) { listItem ->
            SelectListItemBottomSheetItem(
                data = listItem,
            )
        }
        item {
            NavigationBarSpacer()
        }
    }
}
