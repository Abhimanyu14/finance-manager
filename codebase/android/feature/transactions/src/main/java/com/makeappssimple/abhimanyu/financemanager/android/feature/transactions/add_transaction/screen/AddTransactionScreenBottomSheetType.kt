package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.screen

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenBottomSheetType

public sealed class AddTransactionScreenBottomSheetType : ScreenBottomSheetType {
    public data object None : AddTransactionScreenBottomSheetType()
    public data object SelectCategory : AddTransactionScreenBottomSheetType()
    public data object SelectAccountFrom : AddTransactionScreenBottomSheetType()
    public data object SelectAccountTo : AddTransactionScreenBottomSheetType()
}
