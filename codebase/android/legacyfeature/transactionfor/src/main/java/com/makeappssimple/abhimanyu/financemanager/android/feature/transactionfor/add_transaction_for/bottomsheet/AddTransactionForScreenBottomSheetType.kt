package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.bottomsheet

import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenBottomSheetType

internal sealed class AddTransactionForScreenBottomSheetType : ScreenBottomSheetType {
    data object Delete : AddTransactionForScreenBottomSheetType()
    data object Edit : AddTransactionForScreenBottomSheetType()
    data object None : AddTransactionForScreenBottomSheetType()
}
