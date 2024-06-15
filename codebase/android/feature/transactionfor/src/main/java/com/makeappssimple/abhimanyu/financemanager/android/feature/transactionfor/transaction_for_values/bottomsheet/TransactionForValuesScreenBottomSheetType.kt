package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.bottomsheet

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenBottomSheetType

public sealed class TransactionForValuesScreenBottomSheetType : ScreenBottomSheetType {
    public data object DeleteConfirmation : TransactionForValuesScreenBottomSheetType()
    public data object None : TransactionForValuesScreenBottomSheetType()

    public data class Menu(
        val isDeleteVisible: Boolean,
        val transactionForId: Int,
    ) : TransactionForValuesScreenBottomSheetType()
}
