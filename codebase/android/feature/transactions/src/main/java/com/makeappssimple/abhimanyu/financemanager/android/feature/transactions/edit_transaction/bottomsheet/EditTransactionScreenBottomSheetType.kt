package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.bottomsheet

import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.base.ScreenBottomSheetType

internal sealed class EditTransactionScreenBottomSheetType : ScreenBottomSheetType {
    data object None : EditTransactionScreenBottomSheetType()
    data object SelectCategory : EditTransactionScreenBottomSheetType()
    data object SelectAccountFrom : EditTransactionScreenBottomSheetType()
    data object SelectAccountTo : EditTransactionScreenBottomSheetType()
}
