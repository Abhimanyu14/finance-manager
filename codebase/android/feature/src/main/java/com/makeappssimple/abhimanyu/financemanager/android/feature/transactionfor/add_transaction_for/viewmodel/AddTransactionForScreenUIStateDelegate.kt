package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.bottomsheet.AddTransactionForScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.state.AddTransactionForScreenUIState
import kotlinx.coroutines.flow.MutableStateFlow

internal interface AddTransactionForScreenUIStateDelegate {
    // region UI state
    val isLoading: MutableStateFlow<Boolean>
    val screenBottomSheetType: MutableStateFlow<AddTransactionForScreenBottomSheetType>
    val title: MutableStateFlow<TextFieldValue>
    // endregion

    // region loading
    fun startLoading()

    fun completeLoading()

    fun <T> withLoading(
        block: () -> T,
    ): T

    suspend fun <T> withLoadingSuspend(
        block: suspend () -> T,
    ): T
    // endregion

    // region state events
    fun clearTitle()

    fun insertTransactionFor(
        uiState: AddTransactionForScreenUIState,
    )

    fun navigateUp()

    fun resetScreenBottomSheetType()

    fun updateScreenBottomSheetType(
        updatedAddTransactionForScreenBottomSheetType: AddTransactionForScreenBottomSheetType,
    )

    fun updateTitle(
        updatedTitle: TextFieldValue,
    )
    // endregion
}
