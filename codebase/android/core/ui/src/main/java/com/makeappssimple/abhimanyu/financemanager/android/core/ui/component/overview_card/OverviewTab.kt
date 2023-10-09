package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.MyAppTheme

private val overviewMinimumTabWidth = 64.dp

@Immutable
data class OverviewTabData(
    val items: List<String>,
    val selectedItemIndex: Int,
)

@Immutable
data class OverviewTabEvents(
    val onClick: (index: Int) -> Unit = {},
)

@Composable
fun OverviewTab(
    modifier: Modifier = Modifier,
    data: OverviewTabData,
    events: OverviewTabEvents = OverviewTabEvents(),
) {
    val density = LocalDensity.current
    val tabWidths = remember {
        mutableStateListOf<Dp>()
    }
    val indicatorWidthTargetValue = remember(
        key1 = data.selectedItemIndex,
    ) {
        tabWidths.getOrElse(
            index = data.selectedItemIndex,
        ) {
            overviewMinimumTabWidth
        }
    }
    val indicatorOffsetTargetValue = remember(
        key1 = tabWidths,
        key2 = data.selectedItemIndex,
    ) {
        tabWidths.take(
            n = data.selectedItemIndex,
        ).fold(
            initial = 0.dp,
        ) { accumulator, result ->
            accumulator + result
        }
    }
    val indicatorWidth: Dp by animateDpAsState(
        targetValue = tabWidths.getOrElse(
            index = data.selectedItemIndex,
        ) {
            64.dp
        },
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearEasing,
        ),
        label = "",
    )
    val indicatorOffset: Dp by animateDpAsState(
        targetValue = tabWidths.take(
            n = data.selectedItemIndex,
        ).fold(
            initial = 0.dp,
        ) { accumulator, result ->
            accumulator + result
        },
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearEasing,
        ),
        label = "",
    )

    Box(
        modifier = modifier
            .clip(
                shape = CircleShape,
            )
            .background(
                color = MaterialTheme.colorScheme.background,
            )
            .height(
                intrinsicSize = IntrinsicSize.Min,
            ),
    ) {
        OverviewTabIndicator(
            indicatorWidth = indicatorWidth,
            indicatorOffset = indicatorOffset,
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .clip(
                    shape = CircleShape,
                ),
        ) {
            data.items.mapIndexed { index, text ->
                val isSelected = index == data.selectedItemIndex
                val tabTextColor: Color by animateColorAsState(
                    targetValue = if (isSelected) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.onBackground
                    },
                    animationSpec = tween(
                        easing = LinearEasing,
                    ),
                    label = "",
                )
                MyText(
                    modifier = Modifier
                        .onGloballyPositioned {
                            if (tabWidths.size < data.items.size) {
                                tabWidths.add(index, density.run { it.size.width.toDp() })
                            }
                        }
                        .widthIn(
                            min = 64.dp,
                        )
                        .clip(
                            shape = CircleShape,
                        )
                        .clickable(
                            interactionSource = remember {
                                MutableInteractionSource()
                            },
                            indication = null,
                            onClick = {
                                events.onClick(index)
                            },
                        )
                        .padding(
                            horizontal = 12.dp,
                            vertical = 8.dp,
                        ),
                    text = text,
                    style = MaterialTheme.typography.labelLarge
                        .copy(
                            color = tabTextColor,
                            textAlign = TextAlign.Center,
                        ),
                )
            }
        }
    }
}

@Composable
private fun OverviewTabIndicator(
    indicatorWidth: Dp,
    indicatorOffset: Dp,
) {
    val density = LocalDensity.current
    val color = MaterialTheme.colorScheme.primary
    val indicatorWidthInPx = with(density) {
        indicatorWidth.toPx()
    }
    val indicatorOffsetInPx = with(density) {
        indicatorOffset.toPx()
    }

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .drawBehind {
                drawRoundRect(
                    color = color,
                    topLeft = Offset(
                        x = indicatorOffsetInPx,
                        y = 0F,
                    ),
                    size = size.copy(
                        width = indicatorWidthInPx,
                    ),
                    cornerRadius = CornerRadius(
                        x = size.height / 2,
                        y = size.height / 2,
                    ),
                )
            },
    )
}

@Preview
@Composable
private fun OverviewSelectionPreview() {
    var selectedIndex by remember {
        mutableIntStateOf(0)
    }
    MyAppTheme {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.onBackground,
                )
                .fillMaxSize(),
        ) {
            OverviewTab(
                data = OverviewTabData(
                    items = listOf(
                        "Day",
                        "Week",
                        "Month",
                        "Year",
                    ),
                    selectedItemIndex = selectedIndex,
                ),
                events = OverviewTabEvents(
                    onClick = {
                        selectedIndex = it
                    },
                ),
            )
        }
    }
}
