package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield

import androidx.compose.runtime.Immutable

@Immutable
public sealed class MyReadOnlyTextFieldEvent {
    public data object OnClick : MyReadOnlyTextFieldEvent()
}
