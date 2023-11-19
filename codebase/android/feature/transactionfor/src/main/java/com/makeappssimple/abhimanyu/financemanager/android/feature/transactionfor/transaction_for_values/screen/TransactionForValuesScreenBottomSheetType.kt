package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.screen

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenBottomSheetType

sealed class TransactionForValuesScreenBottomSheetType : ScreenBottomSheetType {
    data object DeleteConfirmation : TransactionForValuesScreenBottomSheetType()
    data object None : TransactionForValuesScreenBottomSheetType()

    data class Menu(
        val isDeleteVisible: Boolean,
        val transactionForId: Int,
    ) : TransactionForValuesScreenBottomSheetType()
}
