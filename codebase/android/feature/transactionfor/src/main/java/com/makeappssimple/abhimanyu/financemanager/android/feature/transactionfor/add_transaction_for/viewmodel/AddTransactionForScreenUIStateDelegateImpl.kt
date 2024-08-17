package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.InsertTransactionForUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.bottomsheet.AddTransactionForScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.state.AddTransactionForScreenUIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class AddTransactionForScreenUIStateDelegateImpl(
    private val coroutineScope: CoroutineScope,
    private val insertTransactionForUseCase: InsertTransactionForUseCase,
    private val navigator: Navigator,
) : AddTransactionForScreenUIStateDelegate {
    // region UI state
    override val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    override val screenBottomSheetType: MutableStateFlow<AddTransactionForScreenBottomSheetType> =
        MutableStateFlow(
            value = AddTransactionForScreenBottomSheetType.None,
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
    // endregion

    // region state events
    override fun clearTitle() {
        title.update {
            title.value.copy(
                text = "",
            )
        }
    }

    override fun insertTransactionFor(
        uiState: AddTransactionForScreenUIState,
    ) {
        coroutineScope.launch {
            val isTransactionForInserted = insertTransactionForUseCase(
                title = uiState.title?.text.orEmpty(),
            )
            if (isTransactionForInserted == -1L) {
                // TODO(Abhi): Show error
            } else {
                navigator.navigateUp()
            }
        }
    }

    override fun navigateUp() {
        navigator.navigateUp()
    }

    override fun resetScreenBottomSheetType() {
        setScreenBottomSheetType(
            updatedAddTransactionForScreenBottomSheetType = AddTransactionForScreenBottomSheetType.None,
        )
    }

    override fun setScreenBottomSheetType(
        updatedAddTransactionForScreenBottomSheetType: AddTransactionForScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedAddTransactionForScreenBottomSheetType
        }
    }

    override fun setTitle(
        updatedTitle: TextFieldValue,
    ) {
        title.update {
            updatedTitle
        }
    }
    // endregion
}
