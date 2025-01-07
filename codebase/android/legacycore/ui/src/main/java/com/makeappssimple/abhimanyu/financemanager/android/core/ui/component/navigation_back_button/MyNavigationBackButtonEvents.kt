package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.navigation_back_button

import androidx.compose.runtime.Immutable

@Immutable
public sealed class MyNavigationBackButtonEvents {
    public data object OnClick : MyNavigationBackButtonEvents()
}
