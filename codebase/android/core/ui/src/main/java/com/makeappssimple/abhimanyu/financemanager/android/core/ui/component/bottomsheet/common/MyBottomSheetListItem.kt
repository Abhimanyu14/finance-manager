package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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

@Immutable
public data class MyBottomSheetListItemDataAndEventHandler(
    val data: MyBottomSheetListItemData,
    val handleEvent: (event: MyBottomSheetListItemEvent) -> Unit = {},
)

@Immutable
public data class MyBottomSheetListItemData(
    val imageVector: ImageVector? = null,
    val text: String,
)

@Immutable
public sealed class MyBottomSheetListItemEvent {
    public data object OnClick : MyBottomSheetListItemEvent()
}

@Composable
public fun MyBottomSheetListItem(
    modifier: Modifier = Modifier,
    data: MyBottomSheetListItemData,
    handleEvent: (event: MyBottomSheetListItemEvent) -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .conditionalClickable(
                onClickLabel = data.text,
                role = Role.Button,
                onClick = {
                    handleEvent(MyBottomSheetListItemEvent.OnClick)
                },
            )
            .padding(
                all = 16.dp,
            ),
    ) {
        data.imageVector?.let {
            Icon(
                imageVector = data.imageVector,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(
                        end = 12.dp,
                    ),
            )
        }
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
