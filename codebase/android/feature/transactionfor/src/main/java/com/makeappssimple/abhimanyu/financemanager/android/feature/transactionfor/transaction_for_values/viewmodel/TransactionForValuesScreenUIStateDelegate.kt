package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.bottomsheet.TransactionForValuesScreenBottomSheetType
import kotlinx.coroutines.flow.MutableStateFlow

internal interface TransactionForValuesScreenUIStateDelegate {
    // region UI state
    val isLoading: MutableStateFlow<Boolean>
    val transactionForIdToDelete: MutableStateFlow<Int?>
    val screenBottomSheetType: MutableStateFlow<TransactionForValuesScreenBottomSheetType>
    // endregion

    // region loading
    fun startLoading()

    fun completeLoading()
    // endregion

    // region state events
    fun deleteTransactionFor(
        id: Int,
    )

    fun navigateToAddTransactionForScreen()

    fun navigateToEditTransactionForScreen(
        transactionForId: Int,
    )

    fun navigateUp()

    fun resetScreenBottomSheetType()

    fun setScreenBottomSheetType(
        updatedTransactionForValuesScreenBottomSheetType: TransactionForValuesScreenBottomSheetType,
    )

    fun setTransactionForIdToDelete(
        updatedTransactionForIdToDelete: Int?,
    )
    // endregion
}
