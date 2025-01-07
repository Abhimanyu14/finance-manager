package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.listitem.analysis

import androidx.compose.runtime.Immutable

@Immutable
public data class AnalysisListItemData(
    val maxEndTextWidth: Int = 0,
    val amountText: String,
    val emoji: String,
    val percentage: Float,
    val percentageText: String,
    val title: String,
)
