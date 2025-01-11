package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.bottomsheet

import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenBottomSheetType

internal sealed class TransactionsScreenBottomSheetType : ScreenBottomSheetType {
    data object Filters : TransactionsScreenBottomSheetType()
    data object Menu : TransactionsScreenBottomSheetType()
    data object None : TransactionsScreenBottomSheetType()
    data object SelectTransactionFor : TransactionsScreenBottomSheetType()
    data object Sort : TransactionsScreenBottomSheetType()
}
