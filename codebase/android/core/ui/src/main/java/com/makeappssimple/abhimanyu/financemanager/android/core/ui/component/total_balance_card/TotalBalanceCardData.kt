package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card

import androidx.compose.runtime.Immutable

@Immutable
public data class TotalBalanceCardData(
    val isBalanceVisible: Boolean = false,
    val isClickable: Boolean = false,
    val isLoading: Boolean = false,
    val totalBalanceAmount: Long = 0L,
    val totalMinimumBalanceAmount: Long = 0L,
)
