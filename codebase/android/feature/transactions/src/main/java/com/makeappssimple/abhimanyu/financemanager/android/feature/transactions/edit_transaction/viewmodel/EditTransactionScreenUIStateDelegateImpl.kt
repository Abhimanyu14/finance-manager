package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.capitalizeWords
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.filterDigits
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toLongOrZero
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.bottomsheet.EditTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.snackbar.EditTransactionScreenSnackbarType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.state.EditTransactionScreenUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

internal class EditTransactionScreenUIStateDelegateImpl(
    dateTimeUtil: DateTimeUtil,
    private val navigator: Navigator,
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
            value = dateTimeUtil.getCurrentLocalDate(),
        )
    override val transactionTime: MutableStateFlow<LocalTime> =
        MutableStateFlow(
            value = dateTimeUtil.getCurrentLocalTime(),
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
        navigator.navigateUp()
    }

    override fun resetScreenBottomSheetType() {
        setScreenBottomSheetType(
            updatedEditTransactionScreenBottomSheetType = EditTransactionScreenBottomSheetType.None,
        )
    }

    override fun resetScreenSnackbarType() {
        setScreenSnackbarType(
            updatedEditTransactionScreenSnackbarType = EditTransactionScreenSnackbarType.None,
        )
    }

    override fun setAccountFrom(
        updatedAccountFrom: Account?,
    ) {
        accountFrom.update {
            updatedAccountFrom
        }
    }

    override fun setAccountTo(
        updatedAccountTo: Account?,
    ) {
        accountTo.update {
            updatedAccountTo
        }
    }

    override fun setAmount(
        updatedAmount: TextFieldValue,
    ) {
        amount.update {
            updatedAmount.copy(
                text = updatedAmount.text.filterDigits(),
            )
        }
    }

    override fun setAmount(
        updatedAmount: String,
    ) {
        amount.update {
            it.copy(
                text = updatedAmount.filterDigits(),
            )
        }
    }

    override fun setCategory(
        updatedCategory: Category?,
    ) {
        category.update {
            updatedCategory
        }
    }

    override fun setIsTransactionDatePickerDialogVisible(
        updatedIsTransactionDatePickerDialogVisible: Boolean,
    ) {
        isTransactionDatePickerDialogVisible.update {
            updatedIsTransactionDatePickerDialogVisible
        }
    }

    override fun setIsTransactionTimePickerDialogVisible(
        updatedIsTransactionTimePickerDialogVisible: Boolean,
    ) {
        isTransactionTimePickerDialogVisible.update {
            updatedIsTransactionTimePickerDialogVisible
        }
    }

    override fun setScreenBottomSheetType(
        updatedEditTransactionScreenBottomSheetType: EditTransactionScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedEditTransactionScreenBottomSheetType
        }
    }

    override fun setScreenSnackbarType(
        updatedEditTransactionScreenSnackbarType: EditTransactionScreenSnackbarType,
    ) {
        screenSnackbarType.update {
            updatedEditTransactionScreenSnackbarType
        }
    }

    override fun setSelectedTransactionForIndex(
        updatedSelectedTransactionForIndex: Int,
    ) {
        selectedTransactionForIndex.update {
            updatedSelectedTransactionForIndex
        }
    }

    override fun setSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    ) {
        selectedTransactionTypeIndex.update {
            updatedSelectedTransactionTypeIndex
        }
    }

    override fun setTitle(
        updatedTitle: TextFieldValue,
    ) {
        title.update {
            updatedTitle
        }
    }

    override fun setTransactionDate(
        updatedTransactionDate: LocalDate,
    ) {
        transactionDate.update {
            updatedTransactionDate
        }
    }

    override fun setTransactionTime(
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
