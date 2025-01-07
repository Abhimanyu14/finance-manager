package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.icons.MyIcons
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.shimmer.shimmer

private object ChipUIConstants {
    val borderWidth = 1.dp
    val leadingIconPaddingBottom = 2.dp
    val leadingIconPaddingEnd = 2.dp
    val leadingIconPaddingStart = 12.dp
    val leadingIconPaddingTop = 2.dp
    const val leadingIconScale = 0.75F
    val loadingUIHeight = 30.dp
    val loadingUIPadding = 4.dp
    val loadingUIWidth = 80.dp
    val padding = 4.dp
    val shape = CircleShape
    val textPaddingBottom = 6.dp
    val textPaddingEnd = 16.dp
    val textPaddingStart = 16.dp
    val textPaddingStartWithIcon = 0.dp
    val textPaddingTop = 6.dp
}

@Composable
public fun ChipUI(
    modifier: Modifier = Modifier,
    data: ChipUIData,
    handleEvent: (event: ChipUIEvent) -> Unit = {},
) {
    if (data.isLoading) {
        ChipLoadingUI(
            modifier = modifier,
        )
    } else {
        ChipUIContainer(
            modifier = modifier,
            data = data,
            handleEvent = handleEvent,
        ) {
            val icon: ImageVector? = if (data.isMultiSelect && data.isSelected) {
                MyIcons.Done
            } else {
                data.icon
            }
            ChipUIIcon(
                icon = icon,
                isSelected = data.isSelected,
            )
            ChipUIText(
                icon = icon,
                data = data,
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
                all = ChipUIConstants.loadingUIPadding,
            )
            .size(
                height = ChipUIConstants.loadingUIHeight,
                width = ChipUIConstants.loadingUIWidth,
            )
            .clip(
                shape = ChipUIConstants.shape,
            )
            .shimmer(),
    )
}

@Composable
private fun ChipUIContainer(
    modifier: Modifier = Modifier,
    data: ChipUIData,
    handleEvent: (event: ChipUIEvent) -> Unit = {},
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(
                all = ChipUIConstants.padding,
            )
            .clip(
                shape = ChipUIConstants.shape,
            )
            .border(
                width = ChipUIConstants.borderWidth,
                color = if (data.borderColor.isNotNull()) {
                    data.borderColor
                } else if (data.isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.outline
                },
                shape = ChipUIConstants.shape,
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
            )
            .animateContentSize(),
        content = content,
    )
}

@Composable
private fun ChipUIIcon(
    icon: ImageVector?,
    isSelected: Boolean,
) {
    AnimatedVisibility(
        visible = icon.isNotNull(),
        enter = scaleIn(),
        exit = scaleOut(),
    ) {
        icon?.let {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.primary
                },
                modifier = Modifier
                    .scale(
                        scale = ChipUIConstants.leadingIconScale,
                    )
                    .padding(
                        start = ChipUIConstants.leadingIconPaddingStart,
                        end = ChipUIConstants.leadingIconPaddingEnd,
                        top = ChipUIConstants.leadingIconPaddingTop,
                        bottom = ChipUIConstants.leadingIconPaddingBottom,
                    ),
            )
        }
    }
}

@Composable
private fun ChipUIText(
    icon: ImageVector?,
    data: ChipUIData,
) {
    MyText(
        modifier = Modifier
            .padding(
                top = ChipUIConstants.textPaddingTop,
                bottom = ChipUIConstants.textPaddingBottom,
                end = ChipUIConstants.textPaddingEnd,
                start = if (icon.isNotNull()) {
                    ChipUIConstants.textPaddingStartWithIcon
                } else {
                    ChipUIConstants.textPaddingStart
                },
            ),
        text = data.text,
        style = MaterialTheme.typography.labelMedium
            .copy(
                color = if (data.textColor.isNotNull()) {
                    data.textColor
                } else if (data.isSelected) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.onBackground
                },
            ),
    )
}
