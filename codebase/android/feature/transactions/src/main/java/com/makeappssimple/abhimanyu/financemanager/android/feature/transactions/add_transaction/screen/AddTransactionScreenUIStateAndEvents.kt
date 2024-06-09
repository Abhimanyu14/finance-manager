package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtilImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.filter
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.filterDigits
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNullOrBlank
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orMin
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toIntOrZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toLongOrZero
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateAndEvents
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel.AddTransactionScreenInitialData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel.AddTransactionScreenUiStateData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel.AddTransactionScreenUiVisibilityState
import kotlinx.collections.immutable.ImmutableList
import java.time.LocalDate
import java.time.LocalTime

@Stable
internal data class AddTransactionScreenUIStateAndEvents(
    val uiState: AddTransactionScreenUIState,
    val uiStateEvents: AddTransactionScreenUIStateEvents,
) : ScreenUIStateAndEvents

@Composable
internal fun rememberAddTransactionScreenUIStateAndEvents(
    addTransactionScreenInitialData: AddTransactionScreenInitialData?,
    titleSuggestions: ImmutableList<String>,
    dateTimeUtil: DateTimeUtil = remember {
        DateTimeUtilImpl()
    },
): AddTransactionScreenUIStateAndEvents {
    // region screen bottom sheet type
    var screenBottomSheetType: AddTransactionScreenBottomSheetType by remember {
        mutableStateOf(
            value = AddTransactionScreenBottomSheetType.None,
        )
    }
    val setScreenBottomSheetType =
        { updatedAddTransactionScreenBottomSheetType: AddTransactionScreenBottomSheetType ->
            screenBottomSheetType = updatedAddTransactionScreenBottomSheetType
        }
    val resetScreenBottomSheetType = {
        setScreenBottomSheetType(AddTransactionScreenBottomSheetType.None)
    }
    // endregion

    // region selected transaction type index
    var selectedTransactionTypeIndex: Int by rememberSaveable {
        mutableIntStateOf(
            value = 0,
        )
    }
    val setSelectedTransactionTypeIndex = { updatedSelectedTransactionTypeIndex: Int ->
        selectedTransactionTypeIndex = updatedSelectedTransactionTypeIndex
    }
    // endregion

    // region amount
    var amount: TextFieldValue by remember {
        mutableStateOf(
            value = TextFieldValue(),
        )
    }
    val setAmount = { updatedAmount: TextFieldValue ->
        amount = updatedAmount.copy(
            text = updatedAmount.text.filterDigits(),
        )
    }
    val clearAmount = {
        amount = amount.copy(
            text = "",
        )
    }
    // endregion

    // region amount error text
    var amountErrorText: String? by remember {
        mutableStateOf(
            value = null,
        )
    }
    val setAmountErrorText = { updatedAmountErrorText: String? ->
        amountErrorText = updatedAmountErrorText
    }
    // endregion

    // region category
    var category: Category? by remember {
        mutableStateOf(
            value = null,
        )
    }
    val setCategory = { updatedCategory: Category? ->
        category = updatedCategory
    }
    // endregion

    // region title
    var title: TextFieldValue by remember {
        mutableStateOf(
            value = TextFieldValue(),
        )
    }
    val setTitle = { updatedTitle: TextFieldValue ->
        title = updatedTitle
    }
    val clearTitle = {
        title = title.copy(
            text = "",
        )
    }
    // endregion

    // region selected transaction for index
    var selectedTransactionForIndex: Int by rememberSaveable {
        mutableIntStateOf(
            value = 0,
        )
    }
    val setSelectedTransactionForIndex = { updatedSelectedTransactionForIndex: Int ->
        selectedTransactionForIndex = updatedSelectedTransactionForIndex
    }
    // endregion

    // region account from
    var accountFrom: Account? by remember {
        mutableStateOf(
            value = null,
        )
    }
    val setAccountFrom = { updatedAccountFrom: Account? ->
        accountFrom = updatedAccountFrom
    }
    // endregion

    // region account to
    var accountTo: Account? by remember {
        mutableStateOf(
            value = null,
        )
    }
    val setAccountTo = { updatedAccountTo: Account? ->
        accountTo = updatedAccountTo
    }
    // endregion

    // region is transaction date picker dialog visible
    val (isTransactionDatePickerDialogVisible, setIsTransactionDatePickerDialogVisible: (Boolean) -> Unit) = remember {
        mutableStateOf(
            value = false,
        )
    }
    // endregion

    // region transaction date
    var transactionDate: LocalDate by remember {
        mutableStateOf(
            value = dateTimeUtil.getCurrentLocalDate(),
        )
    }
    val setTransactionDate = { updatedTransactionDate: LocalDate ->
        transactionDate = updatedTransactionDate
    }
    // endregion

    // region transaction time
    var transactionTime: LocalTime by remember {
        mutableStateOf(
            value = dateTimeUtil.getCurrentLocalTime(),
        )
    }
    val setTransactionTime = { updatedTransactionTime: LocalTime ->
        transactionTime = updatedTransactionTime
    }
    // endregion

    // region is transaction time picker dialog visible
    val (isTransactionTimePickerDialogVisible, setIsTransactionTimePickerDialogVisible: (Boolean) -> Unit) = remember {
        mutableStateOf(
            value = false,
        )
    }
    // endregion

    // region ui visibility state
    var uiVisibilityState: AddTransactionScreenUiVisibilityState by remember {
        mutableStateOf(
            value = AddTransactionScreenUiVisibilityState.Expense,
        )
    }
    val setUiVisibilityState = { updatedUiVisibilityState: AddTransactionScreenUiVisibilityState ->
        uiVisibilityState = updatedUiVisibilityState
    }
    // endregion

    // region selected transaction type
    val selectedTransactionType: TransactionType = remember(
        addTransactionScreenInitialData,
        selectedTransactionTypeIndex,
    ) {
        getSelectedTransactionType(
            addTransactionScreenInitialData,
            selectedTransactionTypeIndex,
        )
    }
    // endregion

    // region filtered categories
    val filteredCategories: ImmutableList<Category> = remember(
        key1 = addTransactionScreenInitialData?.categories,
        key2 = selectedTransactionType,
    ) {
        getFilteredCategories(
            addTransactionScreenInitialData = addTransactionScreenInitialData,
            selectedTransactionType = selectedTransactionType,
        )
    }
    // endregion

    // region is cta button enabled
    val isCtaButtonEnabled: Boolean = remember(
        selectedTransactionType,
        addTransactionScreenInitialData,
        amount,
        title,
        amountErrorText,
        accountFrom,
        accountTo,
        setAmountErrorText,
    ) {
        getIsCtaButtonEnabled(
            selectedTransactionType = selectedTransactionType,
            amount = amount,
            title = title,
            amountErrorText = amountErrorText,
            accountFrom = accountFrom,
            accountTo = accountTo,
            addTransactionScreenInitialData = addTransactionScreenInitialData,
            setAmountErrorText = setAmountErrorText,
        )
    }
    // endregion

    val uiState: AddTransactionScreenUIState = remember(
        screenBottomSheetType,
        isTransactionDatePickerDialogVisible,
        isTransactionTimePickerDialogVisible,
        addTransactionScreenInitialData,
        uiVisibilityState,
        isCtaButtonEnabled,
        filteredCategories,
        titleSuggestions,
        selectedTransactionType,
        selectedTransactionTypeIndex,
        amount,
        title,
        category,
        accountFrom,
        accountTo,
        transactionDate,
        transactionTime,
        amountErrorText,
    ) {
        AddTransactionScreenUIState(
            screenBottomSheetType = screenBottomSheetType,
            isTransactionDatePickerDialogVisible = isTransactionDatePickerDialogVisible,
            isTransactionTimePickerDialogVisible = isTransactionTimePickerDialogVisible,
            isLoading = addTransactionScreenInitialData.isNull(), // TODO(Abhi): Move this logic outside
            uiState = AddTransactionScreenUiStateData(
                selectedTransactionTypeIndex = selectedTransactionTypeIndex,
                amount = amount,
                title = title,
                category = category,
                selectedTransactionForIndex = selectedTransactionForIndex,
                accountFrom = accountFrom,
                accountTo = accountTo,
                transactionDate = transactionDate,
                transactionTime = transactionTime,
                amountErrorText = amountErrorText,
            ),
            uiVisibilityState = uiVisibilityState,
            isBottomSheetVisible = screenBottomSheetType != AddTransactionScreenBottomSheetType.None, // TODO(Abhi): Move this logic outside
            isCtaButtonEnabled = isCtaButtonEnabled,
            appBarTitleTextStringResourceId = R.string.screen_add_transaction_appbar_title,
            ctaButtonLabelTextStringResourceId = R.string.screen_add_transaction_floating_action_button_content_description,
            accountFromTextFieldLabelTextStringResourceId = if (selectedTransactionType == TransactionType.TRANSFER) {
                R.string.screen_add_or_edit_transaction_account_from
            } else {
                R.string.screen_add_or_edit_transaction_account
            }, // TODO(Abhi): Move this logic outside
            accountToTextFieldLabelTextStringResourceId = if (selectedTransactionType == TransactionType.TRANSFER) {
                R.string.screen_add_or_edit_transaction_account_to
            } else {
                R.string.screen_add_or_edit_transaction_account
            }, // TODO(Abhi): Move this logic outside
            filteredCategories = filteredCategories,
            transactionTypesForNewTransactionChipUIData = addTransactionScreenInitialData?.validTransactionTypesForNewTransaction
                .map { transactionType ->
                    ChipUIData(
                        text = transactionType.title,
                    )
                }, // TODO(Abhi): Move this logic outside
            titleSuggestions = titleSuggestions,
            titleSuggestionsChipUIData = titleSuggestions
                .map { title ->
                    ChipUIData(
                        text = title,
                    )
                }, // TODO(Abhi): Move this logic outside
            accounts = addTransactionScreenInitialData?.accounts.orEmpty(),
            transactionForValuesChipUIData = addTransactionScreenInitialData?.transactionForValues
                .map { transactionFor ->
                    ChipUIData(
                        text = transactionFor.titleToDisplay,
                    )
                }, // TODO(Abhi): Move this logic outside
            currentLocalDate = dateTimeUtil.getCurrentLocalDate()
                .orMin(), // TODO(Abhi): Move this logic outside
            selectedTransactionType = selectedTransactionType,
        )
    }

    val uiStateEvents: AddTransactionScreenUIStateEvents = remember(
        resetScreenBottomSheetType,
        setScreenBottomSheetType,
        setIsTransactionDatePickerDialogVisible,
        setIsTransactionTimePickerDialogVisible,
        setSelectedTransactionTypeIndex,
        setAmount,
        clearAmount,
        setTitle,
        clearTitle,
        setCategory,
        setSelectedTransactionForIndex,
        setAccountFrom,
        setAccountTo,
        setTransactionDate,
        setTransactionTime,
    ) {
        AddTransactionScreenUIStateEvents(
            resetScreenBottomSheetType = resetScreenBottomSheetType,
            setIsTransactionDatePickerDialogVisible = setIsTransactionDatePickerDialogVisible,
            setIsTransactionTimePickerDialogVisible = setIsTransactionTimePickerDialogVisible,
            setScreenBottomSheetType = setScreenBottomSheetType,
            setSelectedTransactionTypeIndex = setSelectedTransactionTypeIndex,
            setAmount = setAmount,
            clearAmount = clearAmount,
            setTitle = setTitle,
            clearTitle = clearTitle,
            setCategory = setCategory,
            setSelectedTransactionForIndex = setSelectedTransactionForIndex,
            setAccountFrom = setAccountFrom,
            setAccountTo = setAccountTo,
            setTransactionDate = setTransactionDate,
            setTransactionTime = setTransactionTime,
        )
    }

    LaunchedEffect(
        key1 = selectedTransactionType,
    ) {
        handleSelectedTransactionTypeChange(
            selectedTransactionType = selectedTransactionType,
            setUiVisibilityState = setUiVisibilityState,
            addTransactionScreenInitialData = addTransactionScreenInitialData,
            setCategory = setCategory,
            setAccountFrom = setAccountFrom,
            setAccountTo = setAccountTo,
        )
    }

    return remember(
        uiState,
        uiStateEvents,
    ) {
        AddTransactionScreenUIStateAndEvents(
            uiState = uiState,
            uiStateEvents = uiStateEvents,
        )
    }
}

private fun getFilteredCategories(
    addTransactionScreenInitialData: AddTransactionScreenInitialData?,
    selectedTransactionType: TransactionType,
): ImmutableList<Category> {
    return addTransactionScreenInitialData?.categories.filter { category ->
        category.transactionType == selectedTransactionType
    }
}

private fun getSelectedTransactionType(
    addTransactionScreenInitialData: AddTransactionScreenInitialData?,
    selectedTransactionTypeIndex: Int,
): TransactionType {
    return addTransactionScreenInitialData?.validTransactionTypesForNewTransaction?.get(
        selectedTransactionTypeIndex
    ) ?: TransactionType.EXPENSE // TODO(Abhi): Decouple the default value
}

private fun getIsCtaButtonEnabled(
    selectedTransactionType: TransactionType,
    amount: TextFieldValue,
    title: TextFieldValue,
    amountErrorText: String?,
    accountFrom: Account?,
    accountTo: Account?,
    addTransactionScreenInitialData: AddTransactionScreenInitialData?,
    setAmountErrorText: (updatedAmountErrorText: String?) -> Unit,
): Boolean {
    setAmountErrorText(null)
    return when (selectedTransactionType) {
        TransactionType.INCOME -> {
            amount.text.isNotNullOrBlank() &&
                    title.text.isNotNullOrBlank() &&
                    amount.text.toIntOrZero().isNotZero() &&
                    amountErrorText.isNull()
        }

        TransactionType.EXPENSE -> {
            amount.text.isNotNullOrBlank() &&
                    title.text.isNotNullOrBlank() &&
                    amount.text.toIntOrZero().isNotZero() &&
                    amountErrorText.isNull()
        }

        TransactionType.TRANSFER -> {
            amount.text.isNotNullOrBlank() &&
                    accountFrom?.id != accountTo?.id &&
                    amount.text.toIntOrZero().isNotZero() &&
                    amountErrorText.isNull()
        }

        TransactionType.ADJUSTMENT -> {
            false
        }

        TransactionType.INVESTMENT -> {
            amount.text.isNotNullOrBlank() &&
                    title.text.isNotNullOrBlank() &&
                    amount.text.toIntOrZero().isNotZero() &&
                    amountErrorText.isNull()
        }

        TransactionType.REFUND -> {
            val maxRefundAmountValue =
                addTransactionScreenInitialData?.maxRefundAmount?.value.orZero()
            if (amountErrorText.isNull() &&
                (amount.text.toLongOrZero() > maxRefundAmountValue)
            ) {
                setAmountErrorText(
                    addTransactionScreenInitialData?.maxRefundAmount?.run {
                        this.toString()
                    }
                )
                false
            } else if (amountErrorText.isNotNull() &&
                (amount.text.toLongOrZero() <= maxRefundAmountValue)
            ) {
                false
            } else {
                amount.text.isNotNullOrBlank() &&
                        title.text.isNotNullOrBlank() &&
                        amount.text.toIntOrZero().isNotZero() &&
                        amountErrorText.isNull()
            }
        }
    }
}

private fun handleSelectedTransactionTypeChange(
    selectedTransactionType: TransactionType,
    setUiVisibilityState: (AddTransactionScreenUiVisibilityState) -> Unit,
    addTransactionScreenInitialData: AddTransactionScreenInitialData?,
    setCategory: (Category?) -> Unit,
    setAccountFrom: (Account?) -> Unit,
    setAccountTo: (Account?) -> Unit,
) {
    when (selectedTransactionType) {
        TransactionType.INCOME -> {
            setUiVisibilityState(AddTransactionScreenUiVisibilityState.Income)

            val updatedCategory =
                if (selectedTransactionType == addTransactionScreenInitialData?.originalTransactionData?.transaction?.transactionType) {
                    addTransactionScreenInitialData.originalTransactionData.category
                        ?: addTransactionScreenInitialData.defaultIncomeCategory
                } else {
                    addTransactionScreenInitialData?.defaultIncomeCategory
                }
            setCategory(updatedCategory)

            setAccountFrom(null)
            setAccountTo(
                addTransactionScreenInitialData?.originalTransactionData?.accountTo
                    ?: addTransactionScreenInitialData?.defaultAccount
            )
        }

        TransactionType.EXPENSE -> {
            setUiVisibilityState(AddTransactionScreenUiVisibilityState.Expense)

            val updatedCategory =
                if (selectedTransactionType == addTransactionScreenInitialData?.originalTransactionData?.transaction?.transactionType) {
                    addTransactionScreenInitialData.originalTransactionData.category
                        ?: addTransactionScreenInitialData.defaultExpenseCategory
                } else {
                    addTransactionScreenInitialData?.defaultExpenseCategory
                }
            setCategory(updatedCategory)

            setAccountFrom(
                addTransactionScreenInitialData?.originalTransactionData?.accountFrom
                    ?: addTransactionScreenInitialData?.defaultAccount,
            )
            setAccountTo(null)
        }

        TransactionType.TRANSFER -> {
            setUiVisibilityState(AddTransactionScreenUiVisibilityState.Transfer)

            setAccountFrom(
                addTransactionScreenInitialData?.originalTransactionData?.accountFrom
                    ?: addTransactionScreenInitialData?.defaultAccount,
            )
            setAccountTo(
                addTransactionScreenInitialData?.originalTransactionData?.accountTo
                    ?: addTransactionScreenInitialData?.defaultAccount,
            )
        }

        TransactionType.ADJUSTMENT -> {
        }

        TransactionType.INVESTMENT -> {
            setUiVisibilityState(AddTransactionScreenUiVisibilityState.Investment)

            val updatedCategory =
                if (selectedTransactionType == addTransactionScreenInitialData?.originalTransactionData?.transaction?.transactionType) {
                    addTransactionScreenInitialData.originalTransactionData.category
                        ?: addTransactionScreenInitialData.defaultInvestmentCategory
                } else {
                    addTransactionScreenInitialData?.defaultInvestmentCategory
                }
            setCategory(updatedCategory)

            setAccountFrom(
                addTransactionScreenInitialData?.originalTransactionData?.accountFrom
                    ?: addTransactionScreenInitialData?.defaultAccount
            )
            setAccountTo(null)
        }

        TransactionType.REFUND -> {
            setUiVisibilityState(AddTransactionScreenUiVisibilityState.Refund)
        }
    }
}
