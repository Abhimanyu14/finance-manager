package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group

import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUI
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIEvent

@Composable
public fun MyRadioGroup(
    modifier: Modifier = Modifier,
    data: MyRadioGroupData,
    handleEvent: (event: MyRadioGroupEvent) -> Unit = {},
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
                            isSelected = index == data.selectedItemIndex,
                        ),
                    handleEvent = { event ->
                        when (event) {
                            is ChipUIEvent.OnClick -> {
                                handleEvent(
                                    MyRadioGroupEvent.OnSelectionChange(
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
