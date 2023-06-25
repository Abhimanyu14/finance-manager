package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUI
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIEvents

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
