package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.bottomsheet

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenBottomSheetType

internal sealed class ViewTransactionScreenBottomSheetType : ScreenBottomSheetType {
    data object DeleteConfirmation : ViewTransactionScreenBottomSheetType()
    data object None : ViewTransactionScreenBottomSheetType()
}
