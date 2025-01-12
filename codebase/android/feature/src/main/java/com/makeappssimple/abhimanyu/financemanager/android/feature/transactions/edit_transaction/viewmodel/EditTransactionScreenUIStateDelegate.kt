package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.bottomsheet.EditTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.snackbar.EditTransactionScreenSnackbarType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.state.EditTransactionScreenUIState
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import java.time.LocalTime

internal interface EditTransactionScreenUIStateDelegate {
    // region initial data
    var selectedTransactionType: TransactionType?
    // endregion

    // region UI state
    val isLoading: MutableStateFlow<Boolean>
    val screenBottomSheetType: MutableStateFlow<EditTransactionScreenBottomSheetType>
    val screenSnackbarType: MutableStateFlow<EditTransactionScreenSnackbarType>
    val selectedTransactionTypeIndex: MutableStateFlow<Int>
    val amount: MutableStateFlow<TextFieldValue>
    val category: MutableStateFlow<Category?>
    val title: MutableStateFlow<TextFieldValue>
    val selectedTransactionForIndex: MutableStateFlow<Int>
    val accountFrom: MutableStateFlow<Account?>
    val accountTo: MutableStateFlow<Account?>
    val transactionDate: MutableStateFlow<LocalDate>
    val transactionTime: MutableStateFlow<LocalTime>
    val isTransactionDatePickerDialogVisible: MutableStateFlow<Boolean>
    val isTransactionTimePickerDialogVisible: MutableStateFlow<Boolean>
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
    fun clearAmount()

    fun clearTitle()

    fun navigateUp()

    fun resetScreenBottomSheetType()

    fun resetScreenSnackbarType()

    fun updateAccountFrom(
        updatedAccountFrom: Account?,
    )

    fun updateAccountTo(
        updatedAccountTo: Account?,
    )

    fun updateAmount(
        updatedAmount: TextFieldValue,
    )

    fun updateAmount(
        updatedAmount: String,
    )

    fun updateCategory(
        updatedCategory: Category?,
    )

    fun updateIsTransactionDatePickerDialogVisible(
        updatedIsTransactionDatePickerDialogVisible: Boolean,
    )

    fun updateIsTransactionTimePickerDialogVisible(
        updatedIsTransactionTimePickerDialogVisible: Boolean,
    )

    fun updateScreenBottomSheetType(
        updatedEditTransactionScreenBottomSheetType: EditTransactionScreenBottomSheetType,
    )

    fun updateScreenSnackbarType(
        updatedEditTransactionScreenSnackbarType: EditTransactionScreenSnackbarType,
    )

    fun updateSelectedTransactionForIndex(
        updatedSelectedTransactionForIndex: Int,
    )

    fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    )

    fun updateTitle(
        updatedTitle: TextFieldValue,
    )

    fun updateTransactionDate(
        updatedTransactionDate: LocalDate,
    )

    fun updateTransactionTime(
        updatedTransactionTime: LocalTime,
    )

    fun updateTransaction(
        uiState: EditTransactionScreenUIState,
    )
    // endregion
}
