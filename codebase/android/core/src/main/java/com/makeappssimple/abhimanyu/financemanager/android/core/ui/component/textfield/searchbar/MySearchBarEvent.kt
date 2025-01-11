package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.textfield.searchbar

import androidx.compose.runtime.Immutable

@Immutable
public sealed class MySearchBarEvent {
    public data object OnSearch : MySearchBarEvent()
    public data class OnSearchTextChange(
        val updatedSearchText: String,
    ) : MySearchBarEvent()
}
