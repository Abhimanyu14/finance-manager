package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.DeleteTransactionForUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.bottomsheet.TransactionForValuesScreenBottomSheetType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class TransactionForValuesScreenUIStateDelegateImpl(
    private val coroutineScope: CoroutineScope,
    private val deleteTransactionForUseCase: DeleteTransactionForUseCase,
    private val navigator: Navigator,
) : TransactionForValuesScreenUIStateDelegate {
    // region UI state
    override val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    override val transactionForIdToDelete: MutableStateFlow<Int?> = MutableStateFlow(
        value = null,
    )
    override val screenBottomSheetType: MutableStateFlow<TransactionForValuesScreenBottomSheetType> =
        MutableStateFlow(
            value = TransactionForValuesScreenBottomSheetType.None,
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
    override fun deleteTransactionFor(
    ) {
        coroutineScope.launch {
            transactionForIdToDelete.value?.let { id ->
                val isTransactionForDeleted = deleteTransactionForUseCase(
                    id = id,
                )
                if (!isTransactionForDeleted) {
                    // TODO(Abhi): Show error
                }
            }
        }
    }

    override fun navigateToAddTransactionForScreen() {
        navigator.navigateToAddTransactionForScreen()
    }

    override fun navigateToEditTransactionForScreen(
        transactionForId: Int,
    ) {
        navigator.navigateToEditTransactionForScreen(
            transactionForId = transactionForId,
        )
    }

    override fun navigateUp() {
        navigator.navigateUp()
    }

    override fun resetScreenBottomSheetType() {
        setScreenBottomSheetType(
            updatedTransactionForValuesScreenBottomSheetType = TransactionForValuesScreenBottomSheetType.None,
        )
    }

    override fun setScreenBottomSheetType(
        updatedTransactionForValuesScreenBottomSheetType: TransactionForValuesScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedTransactionForValuesScreenBottomSheetType
        }
    }

    override fun setTransactionForIdToDelete(
        updatedTransactionForIdToDelete: Int?,
    ) {
        transactionForIdToDelete.update {
            updatedTransactionForIdToDelete
        }
    }
    // endregion
}
