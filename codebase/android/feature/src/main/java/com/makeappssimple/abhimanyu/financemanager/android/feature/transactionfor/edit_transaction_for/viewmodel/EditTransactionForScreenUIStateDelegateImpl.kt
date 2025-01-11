package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.UpdateTransactionForUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.bottomsheet.EditTransactionForScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.state.EditTransactionForScreenUIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class EditTransactionForScreenUIStateDelegateImpl(
    private val coroutineScope: CoroutineScope,
    private val navigationKit: NavigationKit,
    private val updateTransactionForUseCase: UpdateTransactionForUseCase,
) : EditTransactionForScreenUIStateDelegate {
    // region initial data
    override var currentTransactionFor: TransactionFor? = null
    // endregion

    // region UI state
    override val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    override val screenBottomSheetType: MutableStateFlow<EditTransactionForScreenBottomSheetType> =
        MutableStateFlow(
            value = EditTransactionForScreenBottomSheetType.None,
        )
    override val title: MutableStateFlow<TextFieldValue> = MutableStateFlow(
        value = TextFieldValue(),
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
    override fun clearTitle() {
        title.update {
            title.value.copy(
                text = "",
            )
        }
    }

    override fun navigateUp() {
        navigationKit.navigateUp()
    }

    override fun resetScreenBottomSheetType() {
        setScreenBottomSheetType(
            updatedEditTransactionForScreenBottomSheetType = EditTransactionForScreenBottomSheetType.None,
        )
    }

    override fun setScreenBottomSheetType(
        updatedEditTransactionForScreenBottomSheetType: EditTransactionForScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedEditTransactionForScreenBottomSheetType
        }
    }

    override fun setTitle(
        updatedTitle: TextFieldValue,
    ) {
        title.update {
            updatedTitle
        }
    }

    override fun updateTransactionFor(
        uiState: EditTransactionForScreenUIState,
    ) {
        val currentTransactionForValue = currentTransactionFor ?: return
        coroutineScope.launch {
            val isTransactionForUpdated = updateTransactionForUseCase(
                currentTransactionFor = currentTransactionForValue,
                title = uiState.title.text,
            )
            if (isTransactionForUpdated) {
                navigationKit.navigateUp()
            } else {
                // TODO(Abhi): Show error
            }
        }
    }
    // endregion
}
