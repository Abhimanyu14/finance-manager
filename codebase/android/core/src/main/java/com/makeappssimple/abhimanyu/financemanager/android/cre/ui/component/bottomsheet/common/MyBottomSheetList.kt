package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.common

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.component.NavigationBarsAndImeSpacer
import com.makeappssimple.abhimanyu.financemanager.android.cre.designsystem.component.VerticalSpacer
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.util.minimumBottomSheetHeight

@Composable
public fun MyBottomSheetList(
    modifier: Modifier = Modifier,
    data: MyBottomSheetListData,
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
                handleEvent = listItem.handleEvent,
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
