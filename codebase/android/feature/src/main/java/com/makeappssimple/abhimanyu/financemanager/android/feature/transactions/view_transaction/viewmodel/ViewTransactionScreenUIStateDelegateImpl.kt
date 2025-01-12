package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.DeleteTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.view_transaction.bottomsheet.ViewTransactionScreenBottomSheetType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class ViewTransactionScreenUIStateDelegateImpl(
    private val coroutineScope: CoroutineScope,
    private val deleteTransactionUseCase: DeleteTransactionUseCase,
    private val navigationKit: NavigationKit,
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

    override fun <T> withLoading(
        block: () -> T,
    ): T {
        startLoading()
        val result = block()
        completeLoading()
        return result
    }

    override suspend fun <T> withLoadingSuspend(
        block: suspend () -> T,
    ): T {
        startLoading()
        try {
            return block()
        } finally {
            completeLoading()
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
        navigationKit.navigateToAddTransactionScreen(
            transactionId = transactionId,
        )
    }

    override fun navigateToEditTransactionScreen(
        transactionId: Int,
    ) {
        navigationKit.navigateToEditTransactionScreen(
            transactionId = transactionId,
        )
    }

    override fun navigateToViewTransactionScreen(
        transactionId: Int,
    ) {
        navigationKit.navigateToViewTransactionScreen(
            transactionId = transactionId,
        )
    }

    override fun navigateUp() {
        navigationKit.navigateUp()
    }

    override fun resetScreenBottomSheetType() {
        updateScreenBottomSheetType(
            updatedViewTransactionScreenBottomSheetType = ViewTransactionScreenBottomSheetType.None,
        )
    }

    override fun updateScreenBottomSheetType(
        updatedViewTransactionScreenBottomSheetType: ViewTransactionScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedViewTransactionScreenBottomSheetType
        }
    }
    // endregion
}
