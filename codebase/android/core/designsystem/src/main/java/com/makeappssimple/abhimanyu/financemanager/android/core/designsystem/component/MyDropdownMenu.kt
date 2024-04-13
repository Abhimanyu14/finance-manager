package com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component

import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable

@Immutable
public data class MyDropdownMenuData(
    val isExpanded: Boolean,
    val menuItemsDataAndEvents: List<MyDropdownMenuItemDataAndEvents>,
)

@Immutable
public data class MyDropdownMenuEvents(
    val onDismissRequest: () -> Unit,
)

@Composable
public fun MyDropdownMenu(
    data: MyDropdownMenuData,
    events: MyDropdownMenuEvents,
) {
    DropdownMenu(
        expanded = data.isExpanded,
        onDismissRequest = events.onDismissRequest,
    ) {
        data.menuItemsDataAndEvents.map { menuItemsDataAndEvent ->
            MyDropdownMenuItem(
                data = menuItemsDataAndEvent.data,
                events = menuItemsDataAndEvent.events,
            )
        }
    }
}
