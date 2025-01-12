package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeKit
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.capitalizeWords
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.filterDigits
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toLongOrZero
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.bottomsheet.EditTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.snackbar.EditTransactionScreenSnackbarType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.state.EditTransactionScreenUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

internal class EditTransactionScreenUIStateDelegateImpl(
    dateTimeKit: DateTimeKit,
    private val navigationKit: NavigationKit,
) : EditTransactionScreenUIStateDelegate {
    // region initial data
    override var selectedTransactionType: TransactionType? = null
    // endregion

    // region UI state
    override val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    override val screenBottomSheetType: MutableStateFlow<EditTransactionScreenBottomSheetType> =
        MutableStateFlow(
            value = EditTransactionScreenBottomSheetType.None,
        )
    override val screenSnackbarType: MutableStateFlow<EditTransactionScreenSnackbarType> =
        MutableStateFlow(
            value = EditTransactionScreenSnackbarType.None,
        )
    override val selectedTransactionTypeIndex: MutableStateFlow<Int> =
        MutableStateFlow(
            value = 0,
        )
    override val amount: MutableStateFlow<TextFieldValue> =
        MutableStateFlow(
            value = TextFieldValue(),
        )
    override val category: MutableStateFlow<Category?> =
        MutableStateFlow(
            value = null,
        )
    override val title: MutableStateFlow<TextFieldValue> =
        MutableStateFlow(
            value = TextFieldValue(),
        )
    override val selectedTransactionForIndex: MutableStateFlow<Int> =
        MutableStateFlow(
            value = 0,
        )
    override val accountFrom: MutableStateFlow<Account?> =
        MutableStateFlow(
            value = null,
        )
    override val accountTo: MutableStateFlow<Account?> =
        MutableStateFlow(
            value = null,
        )
    override val transactionDate: MutableStateFlow<LocalDate> =
        MutableStateFlow(
            value = dateTimeKit.getCurrentLocalDate(),
        )
    override val transactionTime: MutableStateFlow<LocalTime> =
        MutableStateFlow(
            value = dateTimeKit.getCurrentLocalTime(),
        )
    override val isTransactionDatePickerDialogVisible: MutableStateFlow<Boolean> = MutableStateFlow(
        value = false,
    )
    override val isTransactionTimePickerDialogVisible: MutableStateFlow<Boolean> = MutableStateFlow(
        value = false,
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
    override fun clearAmount() {
        amount.update {
            it.copy(
                text = "",
            )
        }
    }

    override fun clearTitle() {
        title.update {
            it.copy(
                text = "",
            )
        }
    }

    override fun navigateUp() {
        navigationKit.navigateUp()
    }

    override fun resetScreenBottomSheetType() {
        updateScreenBottomSheetType(
            updatedEditTransactionScreenBottomSheetType = EditTransactionScreenBottomSheetType.None,
        )
    }

    override fun resetScreenSnackbarType() {
        updateScreenSnackbarType(
            updatedEditTransactionScreenSnackbarType = EditTransactionScreenSnackbarType.None,
        )
    }

    override fun updateAccountFrom(
        updatedAccountFrom: Account?,
    ) {
        accountFrom.update {
            updatedAccountFrom
        }
    }

    override fun updateAccountTo(
        updatedAccountTo: Account?,
    ) {
        accountTo.update {
            updatedAccountTo
        }
    }

    override fun updateAmount(
        updatedAmount: TextFieldValue,
    ) {
        amount.update {
            updatedAmount.copy(
                text = updatedAmount.text.filterDigits(),
            )
        }
    }

    override fun updateAmount(
        updatedAmount: String,
    ) {
        amount.update {
            it.copy(
                text = updatedAmount.filterDigits(),
            )
        }
    }

    override fun updateCategory(
        updatedCategory: Category?,
    ) {
        category.update {
            updatedCategory
        }
    }

    override fun updateIsTransactionDatePickerDialogVisible(
        updatedIsTransactionDatePickerDialogVisible: Boolean,
    ) {
        isTransactionDatePickerDialogVisible.update {
            updatedIsTransactionDatePickerDialogVisible
        }
    }

    override fun updateIsTransactionTimePickerDialogVisible(
        updatedIsTransactionTimePickerDialogVisible: Boolean,
    ) {
        isTransactionTimePickerDialogVisible.update {
            updatedIsTransactionTimePickerDialogVisible
        }
    }

    override fun updateScreenBottomSheetType(
        updatedEditTransactionScreenBottomSheetType: EditTransactionScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedEditTransactionScreenBottomSheetType
        }
    }

    override fun updateScreenSnackbarType(
        updatedEditTransactionScreenSnackbarType: EditTransactionScreenSnackbarType,
    ) {
        screenSnackbarType.update {
            updatedEditTransactionScreenSnackbarType
        }
    }

    override fun updateSelectedTransactionForIndex(
        updatedSelectedTransactionForIndex: Int,
    ) {
        selectedTransactionForIndex.update {
            updatedSelectedTransactionForIndex
        }
    }

    override fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    ) {
        selectedTransactionTypeIndex.update {
            updatedSelectedTransactionTypeIndex
        }
    }

    override fun updateTitle(
        updatedTitle: TextFieldValue,
    ) {
        title.update {
            updatedTitle
        }
    }

    override fun updateTransactionDate(
        updatedTransactionDate: LocalDate,
    ) {
        transactionDate.update {
            updatedTransactionDate
        }
    }

    override fun updateTransactionTime(
        updatedTransactionTime: LocalTime,
    ) {
        transactionTime.update {
            updatedTransactionTime
        }
    }

    override fun updateTransaction(
        uiState: EditTransactionScreenUIState,
    ) {
        // TODO(Abhi): Fix update transaction logic
        val enteredAmountValue = amount.value.text.toLongOrZero()
        val amount = Amount(
            value = enteredAmountValue,
        )
        val categoryId = when (uiState.selectedTransactionType) {
            TransactionType.INCOME -> {
                uiState.category?.id
            }

            TransactionType.EXPENSE -> {
                uiState.category?.id
            }

            TransactionType.TRANSFER -> {
                null
            }

            TransactionType.ADJUSTMENT -> {
                null
            }

            TransactionType.INVESTMENT -> {
                uiState.category?.id
            }

            TransactionType.REFUND -> {
                uiState.category?.id
            }
        }
        val accountFromId = when (uiState.selectedTransactionType) {
            TransactionType.INCOME -> {
                null
            }

            TransactionType.EXPENSE -> {
                uiState.accountFrom?.id
            }

            TransactionType.TRANSFER -> {
                uiState.accountFrom?.id
            }

            TransactionType.ADJUSTMENT -> {
                null
            }

            TransactionType.INVESTMENT -> {
                uiState.accountFrom?.id
            }

            TransactionType.REFUND -> {
                null
            }
        }
        val accountToId = when (uiState.selectedTransactionType) {
            TransactionType.INCOME -> {
                uiState.accountTo?.id
            }

            TransactionType.EXPENSE -> {
                null
            }

            TransactionType.TRANSFER -> {
                uiState.accountTo?.id
            }

            TransactionType.ADJUSTMENT -> {
                null
            }

            TransactionType.INVESTMENT -> {
                null
            }

            TransactionType.REFUND -> {
                uiState.accountTo?.id
            }
        }
        val title = when (uiState.selectedTransactionType) {
            TransactionType.TRANSFER -> {
                TransactionType.TRANSFER.title
            }

            TransactionType.REFUND -> {
                TransactionType.REFUND.title
            }

            else -> {
                uiState.title.text.capitalizeWords()
            }
        }
        val transactionForId: Int =
            when (uiState.selectedTransactionType) {
                TransactionType.INCOME -> {
                    1
                }

                TransactionType.EXPENSE -> {
                    uiState.selectedTransactionForIndex
                }

                TransactionType.TRANSFER -> {
                    1
                }

                TransactionType.ADJUSTMENT -> {
                    1
                }

                TransactionType.INVESTMENT -> {
                    1
                }

                TransactionType.REFUND -> {
                    1
                }
            }
        val transactionTimestamp = LocalDateTime.of(
            uiState.transactionDate,
            uiState.transactionTime
        )
            .toEpochMilli()

        val accountFrom = if (accountFromId.isNotNull()) {
            uiState.accountFrom
        } else {
            null
        }
        val accountTo = if (accountToId.isNotNull()) {
            uiState.accountTo
        } else {
            null
        }
        /*
        val transaction = Transaction(
            amount = amount,
            categoryId = categoryId,
            originalTransactionId = currentTransactionData?.transaction?.id,
            accountFromId = accountFromId,
            accountToId = accountToId,
            title = title,
            creationTimestamp = dateTimeUtil.getCurrentTimeMillis(),
            transactionTimestamp = transactionTimestamp,
            transactionForId = transactionForId,
            transactionType = uiState.selectedTransactionType,
        )

        viewModelScope.launch {
            currentTransactionData?.transaction?.let { originalTransaction ->
                updateTransactionUseCase(
                    originalTransaction = originalTransaction,
                    updatedTransaction = transaction,
                )
            }
            navigator.navigateUp()
        }
        */
    }
    // endregion
}
