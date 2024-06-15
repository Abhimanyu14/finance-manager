package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.bottomsheet

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenBottomSheetType

public sealed class EditTransactionScreenBottomSheetType : ScreenBottomSheetType {
    public data object None : EditTransactionScreenBottomSheetType()
    public data object SelectCategory : EditTransactionScreenBottomSheetType()
    public data object SelectAccountFrom : EditTransactionScreenBottomSheetType()
    public data object SelectAccountTo : EditTransactionScreenBottomSheetType()
}
