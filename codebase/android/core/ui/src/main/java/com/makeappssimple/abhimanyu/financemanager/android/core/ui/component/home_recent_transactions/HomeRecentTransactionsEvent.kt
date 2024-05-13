package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.home_recent_transactions

import androidx.compose.runtime.Immutable

@Immutable
public sealed class HomeRecentTransactionsEvent {
    public data object OnClick : HomeRecentTransactionsEvent()
}
