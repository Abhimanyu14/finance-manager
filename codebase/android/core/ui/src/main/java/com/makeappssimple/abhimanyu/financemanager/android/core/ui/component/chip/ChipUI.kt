package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions.conditionalClickable

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.shimmer.shimmer

@Immutable
public data class ChipUIData(
    val isLoading: Boolean = false,
    val isSelected: Boolean = false,
    val borderColor: Color? = null,
    val textColor: Color? = null,
    val text: String = "",
    val icon: ImageVector? = null,
)

@Immutable
public sealed class ChipUIEvent {
    public data object OnClick : ChipUIEvent()
}

@Composable
public fun ChipUI(
    modifier: Modifier = Modifier,
    data: ChipUIData,
    handleEvent: (event: ChipUIEvent) -> Unit = {},
) {
    val shape = CircleShape

    if (data.isLoading) {
        ChipLoadingUI(
            modifier = modifier,
        )
    } else {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .padding(
                    horizontal = 4.dp,
                    vertical = 4.dp,
                )
                .clip(
                    shape = shape,
                )
                .border(
                    width = 1.dp,
                    color = if (data.borderColor != null) {
                        data.borderColor
                    } else if (data.isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.outline
                    },
                    shape = shape,
                )
                .conditionalClickable(
                    onClick = {
                        handleEvent(ChipUIEvent.OnClick)
                    },
                )
                .background(
                    if (data.isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        Color.Transparent
                    }
                ),
        ) {
            data.icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = if (data.isSelected) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.primary
                    },
                    modifier = Modifier
                        .scale(
                            scale = 0.75F,
                        )
                        .padding(
                            start = 12.dp,
                            end = 2.dp,
                            top = 2.dp,
                            bottom = 2.dp,
                        ),
                )
            }
            MyText(
                modifier = Modifier
                    .padding(
                        top = 6.dp,
                        bottom = 6.dp,
                        end = 16.dp,
                        start = if (data.icon.isNotNull()) {
                            0.dp
                        } else {
                            16.dp
                        },
                    ),
                text = data.text,
                style = MaterialTheme.typography.labelMedium
                    .copy(
                        color = if (data.textColor != null) {
                            data.textColor
                        } else if (data.isSelected) {
                            MaterialTheme.colorScheme.onPrimary
                        } else {
                            MaterialTheme.colorScheme.onBackground
                        },
                    ),
            )
        }
    }
}

@Composable
public fun ChipLoadingUI(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(
                horizontal = 4.dp,
                vertical = 4.dp,
            )
            .size(
                height = 30.dp,
                width = 80.dp,
            )
            .clip(
                shape = CircleShape,
            )
            .shimmer(),
    )
}
