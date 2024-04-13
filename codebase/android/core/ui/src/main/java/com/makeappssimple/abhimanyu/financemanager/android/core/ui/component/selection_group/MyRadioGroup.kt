package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group

import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUI
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIEvents

@Immutable
public data class MyRadioGroupData(
    val isLoading: Boolean = false,
    val loadingItemSize: Int = 3,
    val items: List<ChipUIData>,
    val selectedItemIndex: Int?,
)

@Immutable
public data class MyRadioGroupEvents(
    val onSelectionChange: (index: Int) -> Unit = {},
)

@Composable
public fun MyRadioGroup(
    modifier: Modifier = Modifier,
    data: MyRadioGroupData,
    events: MyRadioGroupEvents = MyRadioGroupEvents(),
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
