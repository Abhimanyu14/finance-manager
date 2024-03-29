package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUI
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.fadingedge.fadingEdge

@Immutable
data class MyHorizontalScrollingRadioGroupData(
    val horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween,
    val isLoading: Boolean = false,
    val loadingItemSize: Int = 3,
    val items: List<ChipUIData>,
    val selectedItemIndex: Int?,
)

@Immutable
data class MyHorizontalScrollingRadioGroupEvents(
    val onSelectionChange: (index: Int) -> Unit,
)

@Composable
fun MyHorizontalScrollingRadioGroup(
    modifier: Modifier = Modifier,
    data: MyHorizontalScrollingRadioGroupData,
    events: MyHorizontalScrollingRadioGroupEvents,
) {
    LazyRow(
        horizontalArrangement = data.horizontalArrangement,
        modifier = modifier
            .fadingEdge(),
    ) {
        if (data.isLoading) {
            items(
                count = data.loadingItemSize,
                key = { listItem ->
                    listItem.hashCode()
                },
            ) {
                ChipUI(
                    data = ChipUIData(
                        isLoading = true,
                    ),
                    isSelected = false,
                )
            }
        } else {
            itemsIndexed(
                items = data.items,
                key = { _, listItem ->
                    listItem.hashCode()
                },
            ) { index, chipUIData ->
                ChipUI(
                    data = chipUIData,
                    events = ChipUIEvents(
                        onClick = {
                            events.onSelectionChange(index)
                        }
                    ),
                    isSelected = index == data.selectedItemIndex,
                )
            }
        }
    }
}
