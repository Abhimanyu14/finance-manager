package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.filterDigits
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toLongOrZero
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.InsertTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.bottomsheet.AddTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.snackbar.AddTransactionScreenSnackbarType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

internal class AddTransactionScreenUIStateDelegateImpl(
    dateTimeUtil: DateTimeUtil,
    private val coroutineScope: CoroutineScope,
    private val insertTransactionUseCase: InsertTransactionUseCase,
    private val navigator: Navigator,
) : AddTransactionScreenUIStateDelegate {
    // region initial data
    override var originalTransactionData: TransactionData? = null
    override var transactionForValues: ImmutableList<TransactionFor> = persistentListOf()
    override var selectedTransactionType: TransactionType? = null
    // endregion

    // region UI state
    override val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    override val screenBottomSheetType: MutableStateFlow<AddTransactionScreenBottomSheetType> =
        MutableStateFlow(
            value = AddTransactionScreenBottomSheetType.None,
        )
    override val screenSnackbarType: MutableStateFlow<AddTransactionScreenSnackbarType> =
        MutableStateFlow(
            value = AddTransactionScreenSnackbarType.None,
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

    override fun insertTransaction() {
        val selectedAccountFrom = accountFrom.value
        val selectedAccountTo = accountTo.value
        val selectedCategoryId = category.value?.id
        val selectedTransactionForId = if (selectedTransactionForIndex.value != -1) {
            transactionForValues[selectedTransactionForIndex.value].id
        } else {
            -1
        }
        val selectedTransactionDate = transactionDate.value
        val selectedTransactionTime = transactionTime.value
        val enteredAmountValue = amount.value.text.toLongOrZero()
        val enteredTitle = title.value.text
        val selectedTransactionType = this.selectedTransactionType ?: return
        val originalTransaction = originalTransactionData?.transaction

        coroutineScope.launch {
            val isTransactionInsertedSuccessfully = insertTransactionUseCase(
                selectedAccountFrom = selectedAccountFrom,
                selectedAccountTo = selectedAccountTo,
                selectedCategoryId = selectedCategoryId,
                selectedTransactionForId = selectedTransactionForId,
                selectedTransactionDate = selectedTransactionDate,
                selectedTransactionTime = selectedTransactionTime,
                enteredAmountValue = enteredAmountValue,
                enteredTitle = enteredTitle,
                selectedTransactionType = selectedTransactionType,
                originalTransaction = originalTransaction,
            )
            if (isTransactionInsertedSuccessfully) {
                setScreenSnackbarType(AddTransactionScreenSnackbarType.AddTransactionSuccessful)
                navigator.navigateUp()
            } else {
                setScreenSnackbarType(AddTransactionScreenSnackbarType.AddTransactionFailed)
            }
        }
    }

    override fun navigateUp() {
        navigator.navigateUp()
    }

    override fun resetScreenBottomSheetType() {
        setScreenBottomSheetType(
            updatedAddTransactionScreenBottomSheetType = AddTransactionScreenBottomSheetType.None,
        )
    }

    override fun resetScreenSnackbarType() {
        setScreenSnackbarType(
            updatedAddTransactionScreenSnackbarType = AddTransactionScreenSnackbarType.None,
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
        updatedAddTransactionScreenBottomSheetType: AddTransactionScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedAddTransactionScreenBottomSheetType
        }
    }

    override fun setScreenSnackbarType(
        updatedAddTransactionScreenSnackbarType: AddTransactionScreenSnackbarType,
    ) {
        screenSnackbarType.update {
            updatedAddTransactionScreenSnackbarType
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
    // endregion
}
