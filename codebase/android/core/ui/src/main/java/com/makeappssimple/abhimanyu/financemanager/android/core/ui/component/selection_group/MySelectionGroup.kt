package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.flowlayout.FlowRow
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUI
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIEvents

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
