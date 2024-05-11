package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions.conditionalClickable

@Immutable
public data class MyExpandableItemIconButtonData(
    val isClickable: Boolean,
    val isEnabled: Boolean,
    val iconImageVector: ImageVector,
    val labelText: String,
)

@Immutable
public sealed class MyExpandableItemIconButtonEvent {
    public data object OnClick : MyExpandableItemIconButtonEvent()
}

@Composable
public fun MyExpandableItemIconButton(
    modifier: Modifier = Modifier,
    data: MyExpandableItemIconButtonData,
    handleEvent: (event: MyExpandableItemIconButtonEvent) -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(
                shape = CircleShape,
            )
            .conditionalClickable(
                onClick = if (data.isEnabled && data.isClickable) {
                    {
                        handleEvent(MyExpandableItemIconButtonEvent.OnClick)
                    }
                } else {
                    null
                }
            ),
    ) {
        Icon(
            imageVector = data.iconImageVector,
            contentDescription = null,
            tint = if (data.isEnabled) {
                MaterialTheme.colorScheme.onSurfaceVariant
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            },
        )
        MyText(
            text = data.labelText,
            style = MaterialTheme.typography.labelMedium
                .copy(
                    color = if (data.isEnabled) {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant
                    },
                ),
        )
    }
}
