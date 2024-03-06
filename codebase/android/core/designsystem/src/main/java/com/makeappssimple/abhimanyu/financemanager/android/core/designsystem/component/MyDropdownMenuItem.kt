package com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable

@Immutable
data class MyDropdownMenuItemDataAndEvents(
    val data: MyDropdownMenuItemData,
    val events: MyDropdownMenuItemEvents,
)

@Immutable
data class MyDropdownMenuItemData(
    @StringRes val textStringResourceId: Int,
)

@Immutable
data class MyDropdownMenuItemEvents(
    val onClick: () -> Unit,
)

@Composable
fun MyDropdownMenuItem(
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
