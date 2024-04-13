package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.listitem.common

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions.conditionalClickable

@Immutable
public data class MyListItemDataAndEvents(
    val data: MyListItemData,
    val events: MyListItemEvents,
)

@Immutable
public data class MyListItemData(
    @StringRes val textStringResourceId: Int,
)

@Immutable
public data class MyListItemEvents(
    val onClick: () -> Unit,
)

@Composable
public fun MyListItem(
    modifier: Modifier = Modifier,
    data: MyListItemData,
    events: MyListItemEvents,
) {
    MyText(
        modifier = modifier
            .fillMaxWidth()
            .conditionalClickable(
                onClick = events.onClick,
            )
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
        textStringResourceId = data.textStringResourceId,
        style = MaterialTheme.typography.bodyLarge
            .copy(
                color = MaterialTheme.colorScheme.onBackground,
            ),
    )
}
