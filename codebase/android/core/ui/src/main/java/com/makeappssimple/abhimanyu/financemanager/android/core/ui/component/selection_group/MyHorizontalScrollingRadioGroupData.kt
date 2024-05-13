package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Immutable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData

@Immutable
public data class MyHorizontalScrollingRadioGroupData(
    val horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween,
    val isLoading: Boolean = false,
    val loadingItemSize: Int = 3,
    val items: List<ChipUIData>,
    val selectedItemIndex: Int?,
)
