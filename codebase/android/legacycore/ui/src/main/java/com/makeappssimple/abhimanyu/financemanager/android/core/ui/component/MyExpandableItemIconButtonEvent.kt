package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component

import androidx.compose.runtime.Immutable

@Immutable
public sealed class MyExpandableItemIconButtonEvent {
    public data object OnClick : MyExpandableItemIconButtonEvent()
}
