package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.bottomsheet

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenBottomSheetType

public sealed class AddTransactionForScreenBottomSheetType : ScreenBottomSheetType {
    public data object Delete : AddTransactionForScreenBottomSheetType()
    public data object Edit : AddTransactionForScreenBottomSheetType()
    public data object None : AddTransactionForScreenBottomSheetType()
}
