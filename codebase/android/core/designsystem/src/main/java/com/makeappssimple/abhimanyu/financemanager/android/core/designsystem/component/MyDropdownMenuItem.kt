package com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable

@Immutable
public data class MyDropdownMenuItemDataAndEvents(
    val data: MyDropdownMenuItemData,
    val events: MyDropdownMenuItemEvents,
)

@Immutable
public data class MyDropdownMenuItemData(
    @StringRes val textStringResourceId: Int,
)

@Immutable
public data class MyDropdownMenuItemEvents(
    val onClick: () -> Unit,
)

@Composable
public fun MyDropdownMenuItem(
    data: MyDropdownMenuItemData,
    events: MyDropdownMenuItemEvents,
) {
    DropdownMenuItem(
        text = {
            MyText(
                textStringResourceId = data.textStringResourceId,
            )
        },
        onClick = events.onClick,
    )
}
