package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.actionbutton

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.chart.composepie.legend.Dot

@Immutable
public data class ActionButtonData(
    val isIndicatorVisible: Boolean = false,
    val imageVector: ImageVector,
    @StringRes val contentDescriptionStringResourceId: Int,
)

@Immutable
public data class ActionButtonEvents(
    val onClick: () -> Unit = {},
)

@Immutable
public sealed class ActionButtonEvent {
    public data object OnClick : ActionButtonEvent()
}

@Composable
public fun ActionButton(
    modifier: Modifier = Modifier,
    data: ActionButtonData,
    handleEvent: (event: ActionButtonEvent) -> Unit = {},
) {
    ElevatedCard(
        onClick = {
            handleEvent(ActionButtonEvent.OnClick)
        },
        modifier = modifier,
    ) {
        Box {
            Icon(
                imageVector = data.imageVector,
                contentDescription = stringResource(
                    id = data.contentDescriptionStringResourceId,
                ),
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                    )
                    .padding(
                        all = 8.dp,
                    ),
            )
            if (data.isIndicatorVisible) {
                Dot(
                    modifier = Modifier
                        .align(
                            alignment = Alignment.TopEnd,
                        )
                        .padding(
                            all = 8.dp,
                        ),
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }
    }
}
