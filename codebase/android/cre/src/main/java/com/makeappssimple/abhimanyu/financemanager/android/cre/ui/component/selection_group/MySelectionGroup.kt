package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.selection_group

import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.chip.ChipUI
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.chip.ChipUIEvent

@Composable
public fun MySelectionGroup(
    modifier: Modifier = Modifier,
    data: MySelectionGroupData,
    handleEvent: (event: MySelectionGroupEvent) -> Unit = {},
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
                    data = chipUIData
                        .copy(
                            isMultiSelect = true,
                            isSelected = data.selectedItemsIndices.contains(index),
                        ),
                    handleEvent = { event ->
                        when (event) {
                            is ChipUIEvent.OnClick -> {
                                handleEvent(
                                    MySelectionGroupEvent.OnSelectionChange(
                                        index = index,
                                    )
                                )
                            }
                        }
                    },
                )
            }
        }
    }
}
