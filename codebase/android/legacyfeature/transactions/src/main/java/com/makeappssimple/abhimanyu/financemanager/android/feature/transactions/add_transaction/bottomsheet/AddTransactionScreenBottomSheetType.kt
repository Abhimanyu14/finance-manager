package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.bottomsheet

import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenBottomSheetType

internal sealed class AddTransactionScreenBottomSheetType : ScreenBottomSheetType {
    data object None : AddTransactionScreenBottomSheetType()
    data object SelectCategory : AddTransactionScreenBottomSheetType()
    data object SelectAccountFrom : AddTransactionScreenBottomSheetType()
    data object SelectAccountTo : AddTransactionScreenBottomSheetType()
}
