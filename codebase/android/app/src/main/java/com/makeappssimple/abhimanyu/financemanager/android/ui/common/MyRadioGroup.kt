package com.makeappssimple.abhimanyu.financemanager.android.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.unit.dp
import com.makeappssimple.abhimanyu.financemanager.android.ui.theme.Primary

data class MyRadioGroupItem(
    val text: String,
)

@Composable
fun MyRadioGroup(
    items: List<MyRadioGroupItem>,
    selectedItemIndex: Int,
    onSelectionChange: (index: Int) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxSize(),
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
    Text(
        text = item.text,
        style = MaterialTheme.typography.body1.merge(),
        color = Color.White,
        modifier = Modifier
            .clip(
                shape = RoundedCornerShape(
                    size = 12.dp,
                ),
            )
            .clickable {
                onSelectionChange()
            }
            .background(
                if (isSelected) {
                    Primary
                } else {
                    LightGray
                }
            )
            .padding(
                vertical = 12.dp,
                horizontal = 16.dp,
            ),
    )
}

