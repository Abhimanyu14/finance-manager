package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.minimumBottomSheetHeight

@Immutable
public data class MyBottomSheetListData(
    val items: List<MyBottomSheetListItemDataAndEvents>,
)

/*
@Immutable
public data class MyBottomSheetListEvents(
    val onClick: () -> Unit,
)
*/

@Composable
public fun MyBottomSheetListData(
    modifier: Modifier = Modifier,
    data: MyBottomSheetListData,
    // events: MyBottomSheetListEvents,
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
            items = data.items,
            key = { listItem ->
                listItem.hashCode()
            },
        ) { listItem ->
            MyBottomSheetListItem(
                data = listItem.data,
                events = listItem.events,
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
