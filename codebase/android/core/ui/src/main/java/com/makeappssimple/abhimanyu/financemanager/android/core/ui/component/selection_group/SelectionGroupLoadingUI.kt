package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group

import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUI
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIEvents

@Composable
fun SelectionGroupLoadingUI(
    size: Int,
) {
    MutableList(
        size = size,
    ) { _ ->
        ChipUI(
            data = ChipUIData(
                isLoading = true,
            ),
            events = ChipUIEvents(),
            isSelected = false,
        )
    }
}
