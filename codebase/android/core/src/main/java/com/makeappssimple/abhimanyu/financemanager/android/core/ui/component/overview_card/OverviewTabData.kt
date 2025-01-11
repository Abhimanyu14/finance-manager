package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.overview_card

import androidx.compose.runtime.Immutable

@Immutable
public data class OverviewTabData(
    val items: List<String>,
    val selectedItemIndex: Int,
)
