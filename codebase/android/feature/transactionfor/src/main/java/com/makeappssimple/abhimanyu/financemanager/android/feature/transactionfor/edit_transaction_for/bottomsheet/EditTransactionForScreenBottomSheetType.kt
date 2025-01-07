package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.bottomsheet

import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenBottomSheetType

internal sealed class EditTransactionForScreenBottomSheetType : ScreenBottomSheetType {
    data object Delete : EditTransactionForScreenBottomSheetType()
    data object Edit : EditTransactionForScreenBottomSheetType()
    data object None : EditTransactionForScreenBottomSheetType()
}
