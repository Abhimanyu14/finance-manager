package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.bottomsheet.ViewTransactionScreenBottomSheetType
import kotlinx.coroutines.flow.MutableStateFlow

internal interface ViewTransactionScreenUIStateDelegate {
    // region initial data
    var transactionIdToDelete: Int?
    // endregion

    // region UI state
    val isLoading: MutableStateFlow<Boolean>
    val screenBottomSheetType: MutableStateFlow<ViewTransactionScreenBottomSheetType>
    // endregion

    // region loading
    fun startLoading()

    fun completeLoading()
    // endregion

    // region state events
    fun deleteTransaction()

    fun onRefundButtonClick(
        transactionId: Int,
    )

    fun navigateToEditTransactionScreen(
        transactionId: Int,
    )

    fun navigateToViewTransactionScreen(
        transactionId: Int,
    )

    fun navigateUp()

    fun resetScreenBottomSheetType()

    fun setScreenBottomSheetType(
        updatedViewTransactionScreenBottomSheetType: ViewTransactionScreenBottomSheetType,
    )
    // endregion
}

