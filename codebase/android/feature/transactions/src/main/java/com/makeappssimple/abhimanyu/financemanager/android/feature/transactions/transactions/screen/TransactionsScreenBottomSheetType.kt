package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.screen

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenBottomSheetType

public sealed class TransactionsScreenBottomSheetType : ScreenBottomSheetType {
    public data object Filters : TransactionsScreenBottomSheetType()
    public data object None : TransactionsScreenBottomSheetType()
    public data object SelectTransactionFor : TransactionsScreenBottomSheetType()
    public data object Sort : TransactionsScreenBottomSheetType()
}
