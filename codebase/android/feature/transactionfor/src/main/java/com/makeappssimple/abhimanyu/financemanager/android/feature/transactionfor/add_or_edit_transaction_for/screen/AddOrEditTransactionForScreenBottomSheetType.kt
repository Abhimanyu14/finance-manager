package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.screen

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenBottomSheetType

public sealed class AddOrEditTransactionForScreenBottomSheetType : ScreenBottomSheetType {
    public data object Delete : AddOrEditTransactionForScreenBottomSheetType()
    public data object Edit : AddOrEditTransactionForScreenBottomSheetType()
    public data object None : AddOrEditTransactionForScreenBottomSheetType()
}
