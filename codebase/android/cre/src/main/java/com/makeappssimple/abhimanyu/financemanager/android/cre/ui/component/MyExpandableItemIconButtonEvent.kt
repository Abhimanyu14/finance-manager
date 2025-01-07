package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component

import androidx.compose.runtime.Immutable

@Immutable
public sealed class MyExpandableItemIconButtonEvent {
    public data object OnClick : MyExpandableItemIconButtonEvent()
}
