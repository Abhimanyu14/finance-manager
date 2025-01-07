package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.actionbutton

import androidx.compose.runtime.Immutable

@Immutable
public sealed class ActionButtonEvent {
    public data object OnClick : ActionButtonEvent()
}
