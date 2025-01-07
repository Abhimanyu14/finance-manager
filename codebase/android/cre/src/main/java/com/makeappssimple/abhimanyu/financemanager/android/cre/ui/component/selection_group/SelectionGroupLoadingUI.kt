package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.selection_group

import androidx.compose.runtime.Composable
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.chip.ChipUI
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.chip.ChipUIData

@Composable
public fun SelectionGroupLoadingUI(
    size: Int,
) {
    MutableList(
        size = size,
    ) { _ ->
        ChipUI(
            data = ChipUIData(
                isLoading = true,
                isSelected = false,
            ),
        )
    }
}
