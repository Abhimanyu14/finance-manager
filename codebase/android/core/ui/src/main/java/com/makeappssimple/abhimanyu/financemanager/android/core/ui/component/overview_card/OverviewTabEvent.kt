package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card

import androidx.compose.runtime.Immutable

@Immutable
public sealed class OverviewTabEvent {
    public data class OnClick(
        val index: Int,
    ) : OverviewTabEvent()
}
