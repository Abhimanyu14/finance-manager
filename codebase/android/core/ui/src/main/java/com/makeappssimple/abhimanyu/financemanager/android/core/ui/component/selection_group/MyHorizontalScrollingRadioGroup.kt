package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUI
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIEvent
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.extensions.fadingedge.fadingEdge

@Composable
public fun MyHorizontalScrollingRadioGroup(
    modifier: Modifier = Modifier,
    data: MyHorizontalScrollingRadioGroupData,
    handleEvent: (event: MyHorizontalScrollingRadioGroupEvent) -> Unit = {},
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
                        isSelected = false,
                    ),
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
                    data = chipUIData
                        .copy(
                            isSelected = index == data.selectedItemIndex,
                        ),
                    handleEvent = { event ->
                        when (event) {
                            is ChipUIEvent.OnClick -> {
                                handleEvent(
                                    MyHorizontalScrollingRadioGroupEvent.OnSelectionChange(
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
