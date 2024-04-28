package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.screen

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenBottomSheetType

public sealed class AddOrEditTransactionScreenBottomSheetType : ScreenBottomSheetType {
    public data object None : AddOrEditTransactionScreenBottomSheetType()
    public data object SelectCategory : AddOrEditTransactionScreenBottomSheetType()
    public data object SelectAccountFrom : AddOrEditTransactionScreenBottomSheetType()
    public data object SelectAccountTo : AddOrEditTransactionScreenBottomSheetType()
}
