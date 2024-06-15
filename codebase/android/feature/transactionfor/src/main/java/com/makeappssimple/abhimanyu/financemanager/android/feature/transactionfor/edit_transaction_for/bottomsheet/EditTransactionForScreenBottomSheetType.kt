package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.bottomsheet

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenBottomSheetType

public sealed class EditTransactionForScreenBottomSheetType : ScreenBottomSheetType {
    public data object Delete : EditTransactionForScreenBottomSheetType()
    public data object Edit : EditTransactionForScreenBottomSheetType()
    public data object None : EditTransactionForScreenBottomSheetType()
}
