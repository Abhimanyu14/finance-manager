package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group

import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUI
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIEvents

@Immutable
data class MySelectionGroupData(
    val isLoading: Boolean = false,
    val loadingItemSize: Int = 3,
    val items: List<ChipUIData>,
    val selectedItemsIndices: List<Int>,
)

@Immutable
data class MySelectionGroupEvents(
    val onSelectionChange: (index: Int) -> Unit = {},
)

@Composable
fun MySelectionGroup(
    modifier: Modifier = Modifier,
    data: MySelectionGroupData,
    events: MySelectionGroupEvents = MySelectionGroupEvents(),
) {
    FlowRow(
        modifier = modifier,
    ) {
        if (data.isLoading) {
            SelectionGroupLoadingUI(
                size = data.loadingItemSize,
            )
        } else {
            data.items.mapIndexed { index, chipUIData ->
                ChipUI(
                    data = chipUIData,
                    events = ChipUIEvents(
                        onSelectionChange = {
                            events.onSelectionChange(index)
                        }
                    ),
                    isSelected = data.selectedItemsIndices.contains(index),
                )
            }
        }
    }
}
