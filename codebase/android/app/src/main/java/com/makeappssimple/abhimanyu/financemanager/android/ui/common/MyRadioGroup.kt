package com.makeappssimple.abhimanyu.financemanager.android.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Primary

data class MyRadioGroupItem(
    val text: String,
    val defaultTextColor: Color = DarkGray,
    val selectedTextColor: Color = White,
    val defaultBackgroundColor: Color = LightGray,
    val selectedBackgroundColor: Color = Primary,
)

@Composable
fun MyRadioGroup(
    items: List<MyRadioGroupItem>,
    selectedItemIndex: Int,
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
private fun MyRadioGroupItemView(
    item: MyRadioGroupItem,
    isSelected: Boolean,
    onSelectionChange: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(
                horizontal = 4.dp,
            )
    ) {
        Text(
            text = item.text,
            style = TextStyle(
                color = if (isSelected) {
                    item.selectedTextColor
                } else {
                    item.defaultTextColor
                },
                fontSize = 14.sp,
                fontWeight = FontWeight.Thin,
            ),
            modifier = Modifier
                .clip(
                    shape = RoundedCornerShape(
                        size = 16.dp,
                    ),
                )
                .border(
                    width = 1.dp,
                    color = if (isSelected) {
                        Transparent
                    } else {
                        item.defaultBackgroundColor
                    },
                    shape = RoundedCornerShape(
                        size = 16.dp,
                    ),
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
                )
                .padding(
                    vertical = 6.dp,
                    horizontal = 16.dp,
                ),
        )
    }
}

