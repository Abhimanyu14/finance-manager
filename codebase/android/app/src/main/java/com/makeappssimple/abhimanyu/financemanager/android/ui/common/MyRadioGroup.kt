package com.makeappssimple.abhimanyu.financemanager.android.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowRow
import com.makeappssimple.abhimanyu.financemanager.android.ui.components.MyText
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Primary
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.utils.getIcon

data class MyRadioGroupItem(
    val text: String,
    val iconKey: String? = null,
    val defaultTextColor: Color = DarkGray,
    val selectedTextColor: Color = White,
    val defaultBackgroundColor: Color = LightGray,
    val selectedBackgroundColor: Color = Primary,
)

@Composable
fun MySelectionGroup(
    items: List<MyRadioGroupItem>,
    selectedItemsIndices: List<Int>,
    modifier: Modifier = Modifier,
    onSelectionChange: (index: Int) -> Unit,
) {
    FlowRow(
        modifier = modifier,
    ) {
        items.forEachIndexed { index, item ->
            MyRadioGroupItemView(
                item = item,
                isSelected = selectedItemsIndices.contains(index),
                onSelectionChange = {
                    onSelectionChange(index)
                },
            )
        }
    }
}

@Composable
fun MyRadioGroup(
    items: List<MyRadioGroupItem>,
    selectedItemIndex: Int?,
    modifier: Modifier = Modifier,
    onSelectionChange: (index: Int) -> Unit,
) {
    FlowRow(
        modifier = modifier,
    ) {
        items.forEachIndexed { index, item ->
            MyRadioGroupItemView(
                item = item,
                isSelected = index == selectedItemIndex,
                onSelectionChange = {
                    onSelectionChange(index)
                },
            )
        }
    }
}

@Composable
fun MyScrollableRadioGroup(
    items: List<MyRadioGroupItem>,
    selectedItemIndex: Int?,
    modifier: Modifier = Modifier,
    onSelectionChange: (index: Int) -> Unit,
) {
    LazyRow(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier,
    ) {
        itemsIndexed(items) { index, item ->
            MyRadioGroupItemView(
                item = item,
                isSelected = index == selectedItemIndex,
                onSelectionChange = {
                    onSelectionChange(index)
                },
            )
        }
    }
}

@Composable
private fun MyRadioGroupItemView(
    item: MyRadioGroupItem,
    isSelected: Boolean,
    onSelectionChange: () -> Unit,
) {
    val shape = RoundedCornerShape(
        percent = 50,
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
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
                    Transparent
                } else {
                    item.defaultBackgroundColor
                },
                shape = shape,
            )
            .clickable {
                onSelectionChange()
            }
            .background(
                if (isSelected) {
                    item.selectedBackgroundColor
                } else {
                    Transparent
                }
            ),
    ) {
        item.iconKey?.let {
            Icon(
                imageVector = getIcon(
                    name = it,
                ),
                contentDescription = null,
                tint = if (isSelected) {
                    item.selectedTextColor
                } else {
                    Primary
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
            text = item.text,
            style = TextStyle(
                color = if (isSelected) {
                    item.selectedTextColor
                } else {
                    item.defaultTextColor
                },
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
            ),
            modifier = Modifier
                .padding(
                    top = 6.dp,
                    bottom = 6.dp,
                    end = 16.dp,
                    start = if (item.iconKey.isNotNull()) {
                        0.dp
                    } else {
                        16.dp
                    },
                ),
        )
    }
}

