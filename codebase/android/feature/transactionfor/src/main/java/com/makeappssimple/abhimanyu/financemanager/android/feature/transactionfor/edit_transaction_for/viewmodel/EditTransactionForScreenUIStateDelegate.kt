package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.bottomsheet.EditTransactionForScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.state.EditTransactionForScreenUIState
import kotlinx.coroutines.flow.MutableStateFlow

internal interface EditTransactionForScreenUIStateDelegate {
    // region initial data
    var currentTransactionFor: TransactionFor?
    // endregion

    // region UI state
    val isLoading: MutableStateFlow<Boolean>
    val screenBottomSheetType: MutableStateFlow<EditTransactionForScreenBottomSheetType>
    val title: MutableStateFlow<TextFieldValue>
    // endregion

    // region loading
    fun startLoading()

    fun completeLoading()
    // endregion

    // region state events
    fun clearTitle()

    fun navigateUp()

    fun resetScreenBottomSheetType()

    fun setScreenBottomSheetType(
        updatedEditTransactionForScreenBottomSheetType: EditTransactionForScreenBottomSheetType,
    )

    fun setTitle(
        updatedTitle: TextFieldValue,
    )

    fun updateTransactionFor(
        uiState: EditTransactionForScreenUIState,
    )
    // endregion
}
