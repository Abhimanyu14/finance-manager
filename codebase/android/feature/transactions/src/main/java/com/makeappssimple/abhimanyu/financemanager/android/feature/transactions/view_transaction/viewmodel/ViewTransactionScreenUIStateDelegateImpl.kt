package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.DeleteTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.bottomsheet.ViewTransactionScreenBottomSheetType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class ViewTransactionScreenUIStateDelegateImpl(
    private val coroutineScope: CoroutineScope,
    private val deleteTransactionUseCase: DeleteTransactionUseCase,
    private val navigator: Navigator,
) : ViewTransactionScreenUIStateDelegate {
    // region initial data
    override var transactionIdToDelete: Int? = null
    // endregion

    // region UI state
    override val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )

    override val screenBottomSheetType: MutableStateFlow<ViewTransactionScreenBottomSheetType> =
        MutableStateFlow(
            value = ViewTransactionScreenBottomSheetType.None,
        )
    // endregion

    // region loading
    override fun startLoading() {
        isLoading.update {
            true
        }
    }

    override fun completeLoading() {
        isLoading.update {
            false
        }
    }
    // endregion

    // region state events
    override fun deleteTransaction() {
        val id = transactionIdToDelete ?: return
        coroutineScope.launch {
            startLoading()
            val isTransactionDeleted = deleteTransactionUseCase(
                id = id,
            )
            transactionIdToDelete = null
            resetScreenBottomSheetType()
            if (isTransactionDeleted) {
                // TODO(Abhi): Show success message
                // TODO(Abhi): Change to navigate up only if the current transaction is deleted
                navigateUp()
            } else {
                // TODO(Abhi): Show error message
            }
            completeLoading()
        }
    }

    override fun onRefundButtonClick(
        transactionId: Int,
    ) {
        navigator.navigateToAddTransactionScreen(
            transactionId = transactionId,
        )
    }

    override fun navigateToEditTransactionScreen(
        transactionId: Int,
    ) {
        navigator.navigateToEditTransactionScreen(
            transactionId = transactionId,
        )
    }

    override fun navigateToViewTransactionScreen(
        transactionId: Int,
    ) {
        navigator.navigateToViewTransactionScreen(
            transactionId = transactionId,
        )
    }

    override fun navigateUp() {
        navigator.navigateUp()
    }

    override fun resetScreenBottomSheetType() {
        setScreenBottomSheetType(
            updatedViewTransactionScreenBottomSheetType = ViewTransactionScreenBottomSheetType.None,
        )
    }

    override fun setScreenBottomSheetType(
        updatedViewTransactionScreenBottomSheetType: ViewTransactionScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedViewTransactionScreenBottomSheetType
        }
    }
    // endregion
}
