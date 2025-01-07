package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.selection_group

import androidx.compose.runtime.Immutable

@Immutable
public sealed class MyRadioGroupEvent {
    public data class OnSelectionChange(
        val index: Int,
    ) : MyRadioGroupEvent()
}
