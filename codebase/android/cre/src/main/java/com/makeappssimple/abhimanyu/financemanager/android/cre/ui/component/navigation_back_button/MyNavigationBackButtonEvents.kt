package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.navigation_back_button

import androidx.compose.runtime.Immutable

@Immutable
public sealed class MyNavigationBackButtonEvents {
    public data object OnClick : MyNavigationBackButtonEvents()
}
