package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.save_button

import androidx.compose.runtime.Immutable

@Immutable
public sealed class SaveButtonEvent {
    public data object OnClick : SaveButtonEvent()
}
