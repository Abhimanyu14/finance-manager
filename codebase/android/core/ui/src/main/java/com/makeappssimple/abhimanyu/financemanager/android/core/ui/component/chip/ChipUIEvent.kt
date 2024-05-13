package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip

import androidx.compose.runtime.Immutable

@Immutable
public sealed class ChipUIEvent {
    public data object OnClick : ChipUIEvent()
}
