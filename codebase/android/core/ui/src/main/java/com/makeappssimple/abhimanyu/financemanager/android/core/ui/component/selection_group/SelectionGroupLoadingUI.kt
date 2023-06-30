package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group

import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUI
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData

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
            isSelected = false,
        )
    }
}
