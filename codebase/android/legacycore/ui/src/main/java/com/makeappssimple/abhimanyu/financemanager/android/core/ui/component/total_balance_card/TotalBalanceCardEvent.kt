package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.total_balance_card

import androidx.compose.runtime.Immutable

@Immutable
public sealed class TotalBalanceCardEvent {
    public data object OnClick : TotalBalanceCardEvent()
    public data object OnViewBalanceClick : TotalBalanceCardEvent()
}
