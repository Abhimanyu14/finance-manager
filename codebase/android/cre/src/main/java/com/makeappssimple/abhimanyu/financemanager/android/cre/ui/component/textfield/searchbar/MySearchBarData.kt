package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.textfield.searchbar

import androidx.compose.runtime.Immutable

@Immutable
public data class MySearchBarData(
    val autoFocus: Boolean = true,
    val isLoading: Boolean = false,
    val placeholderText: String = "",
    val searchText: String = "",
)
