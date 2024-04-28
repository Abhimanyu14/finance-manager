package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.screen

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenBottomSheetType

public sealed class ViewTransactionScreenBottomSheetType : ScreenBottomSheetType {
    public data object DeleteConfirmation : ViewTransactionScreenBottomSheetType()
    public data object None : ViewTransactionScreenBottomSheetType()
}
