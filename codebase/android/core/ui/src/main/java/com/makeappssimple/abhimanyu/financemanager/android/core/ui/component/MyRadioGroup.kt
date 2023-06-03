package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.MyText
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.extensions.conditionalClickable

@Immutable
data class ChipUIData(
    val text: String,
    val icon: ImageVector? = null,
)

@Immutable
data class ChipUIEvents(
    val onSelectionChange: () -> Unit,
)

@Composable
fun MySelectionGroup(
    modifier: Modifier = Modifier,
    items: List<ChipUIData>,
    selectedItemsIndices: List<Int>,
    onSelectionChange: (index: Int) -> Unit,
) {
    FlowRow(
        modifier = modifier,
    ) {
        items.mapIndexed { index, data ->
            ChipUI(
                data = data,
                events = ChipUIEvents(
                    onSelectionChange = {
                        onSelectionChange(index)
                    }
                ),
                isSelected = selectedItemsIndices.contains(index),
            )
        }
    }
}

@Composable
fun MyRadioGroup(
    modifier: Modifier = Modifier,
    items: List<ChipUIData>,
    selectedItemIndex: Int?,
    onSelectionChange: (index: Int) -> Unit,
) {
    FlowRow(
        modifier = modifier,
    ) {
        items.mapIndexed { index, data ->
            ChipUI(
                data = data,
                events = ChipUIEvents(
                    onSelectionChange = {
                        onSelectionChange(index)
                    }
                ),
                isSelected = index == selectedItemIndex,
            )
        }
    }
}

@Composable
fun MyHorizontalScrollingRadioGroup(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween,
    items: List<ChipUIData>,
    selectedItemIndex: Int?,
    onSelectionChange: (index: Int) -> Unit,
) {
    LazyRow(
        horizontalArrangement = horizontalArrangement,
        modifier = modifier,
    ) {
        itemsIndexed(
            items = items,
            key = { _, listItem ->
                listItem.hashCode()
            },
        ) { index, data ->
            ChipUI(
                data = data,
                events = ChipUIEvents(
                    onSelectionChange = {
                        onSelectionChange(index)
                    }
                ),
                isSelected = index == selectedItemIndex,
            )
        }
    }
}

@Composable
fun MyHorizontalScrollingSelectionGroup(
    modifier: Modifier = Modifier,
    items: List<ChipUIData>,
    onSelectionChange: (index: Int) -> Unit,
) {
    LazyRow(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier,
    ) {
        itemsIndexed(
            items = items,
            key = { _, listItem ->
                listItem.hashCode()
            },
        ) { index, data ->
            ChipUI(
                data = data,
                events = ChipUIEvents(
                    onSelectionChange = {
                        onSelectionChange(index)
                    }
                ),
                isSelected = false,
            )
        }
    }
}

@Composable
private fun ChipUI(
    modifier: Modifier = Modifier,
    data: ChipUIData,
    events: ChipUIEvents,
    isSelected: Boolean,
) {
    val shape = CircleShape

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
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.outline
                },
                shape = shape,
            )
            .conditionalClickable(
                onClick = events.onSelectionChange,
            )
            .background(
                if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.background
                }
            ),
    ) {
        data.icon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                tint = if (isSelected) {
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
                    color = if (isSelected) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.onBackground
                    },
                ),
        )
    }
}
