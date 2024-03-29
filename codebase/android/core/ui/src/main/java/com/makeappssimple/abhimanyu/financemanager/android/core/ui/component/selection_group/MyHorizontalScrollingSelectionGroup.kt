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
data class MyHorizontalScrollingSelectionGroupData(
    val isLoading: Boolean = false,
    val loadingItemSize: Int = 3,
    val items: List<ChipUIData>,
)

@Immutable
data class MyHorizontalScrollingSelectionGroupEvents(
    val onSelectionChange: (index: Int) -> Unit,
)

@Composable
fun MyHorizontalScrollingSelectionGroup(
    modifier: Modifier = Modifier,
    data: MyHorizontalScrollingSelectionGroupData,
    events: MyHorizontalScrollingSelectionGroupEvents,
) {
    LazyRow(
        horizontalArrangement = Arrangement.SpaceBetween,
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
            ) { index, data ->
                ChipUI(
                    data = data,
                    events = ChipUIEvents(
                        onClick = {
                            events.onSelectionChange(index)
                        }
                    ),
                    isSelected = false,
                )
            }
        }
    }
}
