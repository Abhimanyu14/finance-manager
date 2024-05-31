package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtilImpl
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.filterDigits
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNullOrBlank
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
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
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel.AddTransactionScreenData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel.AddTransactionScreenUiStateData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel.AddTransactionScreenUiVisibilityState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel.orDefault
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import java.time.LocalDate
import java.time.LocalTime

@Stable
internal class AddTransactionScreenUIStateAndEvents(
    val state: AddTransactionScreenUIState,
    val events: AddTransactionScreenUIStateEvents,
) : ScreenUIStateAndEvents

@Composable
internal fun rememberAddTransactionScreenUIStateAndEvents(
    addTransactionScreenData: AddTransactionScreenData?,
    titleSuggestions: ImmutableList<String>?,
): AddTransactionScreenUIStateAndEvents {
    val dateTimeUtil = remember {
        DateTimeUtilImpl()
    }

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

    // region is transaction date picker dialog visible
    val (isTransactionDatePickerDialogVisible, setIsTransactionDatePickerDialogVisible: (Boolean) -> Unit) = remember {
        mutableStateOf(
            value = false,
        )
    }
    // endregion

    // region is transaction time picker dialog visible
    val (isTransactionTimePickerDialogVisible, setIsTransactionTimePickerDialogVisible: (Boolean) -> Unit) = remember {
        mutableStateOf(
            value = false,
        )
    }
    // endregion

    // region selected transaction type index
    var selectedTransactionTypeIndex: Int by remember {
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

    // region selected transaction for index
    var selectedTransactionForIndex: Int by remember {
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
        selectedTransactionTypeIndex,
    ) {
        addTransactionScreenData?.validTransactionTypesForNewTransaction?.get(
            selectedTransactionTypeIndex
        ) ?: TransactionType.EXPENSE // TODO(Abhi): Decouple the default value
    }
    // endregion

    // region filtered categories
    val filteredCategories: ImmutableList<Category> = remember(
        key1 = addTransactionScreenData,
        key2 = selectedTransactionType,
    ) {
        addTransactionScreenData?.categories?.filter { category ->
            category.transactionType == selectedTransactionType
        }?.toImmutableList().orEmpty()
    }
    // endregion

    // region
    // TODO(Abhi): Clean up
//    private val selectedCategoryId: Flow<Int?> = uiState.map {
//        it.category?.id
//    }
    // endregion

    // region is cta button enabled
    val isCtaButtonEnabled: Boolean = remember(
        selectedTransactionType,
        addTransactionScreenData,
        amount,
        title,
        amountErrorText,
        accountFrom,
        accountTo,
    ) {
        when (selectedTransactionType) {
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
                val maxRefundAmountValue = addTransactionScreenData?.maxRefundAmount?.value.orZero()
                if (amountErrorText.isNull() &&
                    (amount.text.toLongOrZero() > maxRefundAmountValue)
                ) {
                    amountErrorText = addTransactionScreenData?.maxRefundAmount?.run {
                        this.toString()
                    }
                    false
                } else if (amountErrorText.isNotNull() &&
                    (amount.text.toLongOrZero() <= maxRefundAmountValue)
                ) {
                    amountErrorText = null
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
    // endregion

    LaunchedEffect(
        key1 = selectedTransactionType,
    ) {
        when (selectedTransactionType) {
            TransactionType.INCOME -> {
                setUiVisibilityState(AddTransactionScreenUiVisibilityState.Income)

                val updatedCategory =
                    if (selectedTransactionType == addTransactionScreenData?.originalTransactionData?.transaction?.transactionType) {
                        addTransactionScreenData.originalTransactionData.category
                            ?: addTransactionScreenData.defaultIncomeCategory
                    } else {
                        addTransactionScreenData?.defaultIncomeCategory
                    }
                setCategory(updatedCategory)

                setAccountFrom(null)
                setAccountTo(
                    addTransactionScreenData?.originalTransactionData?.accountTo
                        ?: addTransactionScreenData?.defaultAccount
                )
            }

            TransactionType.EXPENSE -> {
                setUiVisibilityState(AddTransactionScreenUiVisibilityState.Expense)

                val updatedCategory =
                    if (selectedTransactionType == addTransactionScreenData?.originalTransactionData?.transaction?.transactionType) {
                        addTransactionScreenData.originalTransactionData.category
                            ?: addTransactionScreenData.defaultExpenseCategory
                    } else {
                        addTransactionScreenData?.defaultExpenseCategory
                    }
                setCategory(updatedCategory)

                setAccountFrom(
                    addTransactionScreenData?.originalTransactionData?.accountFrom
                        ?: addTransactionScreenData?.defaultAccount,
                )
                setAccountTo(null)
            }

            TransactionType.TRANSFER -> {
                setUiVisibilityState(AddTransactionScreenUiVisibilityState.Transfer)

                setAccountFrom(
                    addTransactionScreenData?.originalTransactionData?.accountFrom
                        ?: addTransactionScreenData?.defaultAccount,
                )
                setAccountTo(
                    addTransactionScreenData?.originalTransactionData?.accountTo
                        ?: addTransactionScreenData?.defaultAccount,
                )
            }

            TransactionType.ADJUSTMENT -> {
            }

            TransactionType.INVESTMENT -> {
                setUiVisibilityState(AddTransactionScreenUiVisibilityState.Investment)

                val updatedCategory =
                    if (selectedTransactionType == addTransactionScreenData?.originalTransactionData?.transaction?.transactionType) {
                        addTransactionScreenData.originalTransactionData.category
                            ?: addTransactionScreenData.defaultInvestmentCategory
                    } else {
                        addTransactionScreenData?.defaultInvestmentCategory
                    }
                setCategory(updatedCategory)

                setAccountFrom(
                    addTransactionScreenData?.originalTransactionData?.accountFrom
                        ?: addTransactionScreenData?.defaultAccount
                )
                setAccountTo(null)
            }

            TransactionType.REFUND -> {
                setUiVisibilityState(AddTransactionScreenUiVisibilityState.Refund)
            }
        }
    }

    return remember(
        screenBottomSheetType,
        setScreenBottomSheetType,
        isTransactionDatePickerDialogVisible,
        setIsTransactionDatePickerDialogVisible,
        isTransactionTimePickerDialogVisible,
        setIsTransactionTimePickerDialogVisible,
        uiVisibilityState,
        isCtaButtonEnabled,
        filteredCategories,
        titleSuggestions,
        selectedTransactionType,
    ) {
        AddTransactionScreenUIStateAndEvents(
            state = AddTransactionScreenUIState(
                screenBottomSheetType = screenBottomSheetType,
                isTransactionDatePickerDialogVisible = isTransactionDatePickerDialogVisible,
                isTransactionTimePickerDialogVisible = isTransactionTimePickerDialogVisible,
                isLoading = false,
                uiState = AddTransactionScreenUiStateData(
                    selectedTransactionTypeIndex = selectedTransactionTypeIndex,
                    amount = amount,
                    title = title,
                    description = TextFieldValue(),
                    category = category,
                    selectedTransactionForIndex = selectedTransactionForIndex,
                    accountFrom = accountFrom,
                    accountTo = accountTo,
                    transactionDate = transactionDate,
                    transactionTime = transactionTime,
                    amountErrorText = amountErrorText,
                ),
                uiVisibilityState = uiVisibilityState.orDefault(),
                isBottomSheetVisible = screenBottomSheetType != AddTransactionScreenBottomSheetType.None,
                isCtaButtonEnabled = isCtaButtonEnabled,
                appBarTitleTextStringResourceId = R.string.screen_add_transaction_appbar_title,
                ctaButtonLabelTextStringResourceId = R.string.screen_add_transaction_floating_action_button_content_description,
                accountFromTextFieldLabelTextStringResourceId = if (selectedTransactionType == TransactionType.TRANSFER) {
                    R.string.screen_add_or_edit_transaction_account_from
                } else {
                    R.string.screen_add_or_edit_transaction_account
                },
                accountToTextFieldLabelTextStringResourceId = if (selectedTransactionType == TransactionType.TRANSFER) {
                    R.string.screen_add_or_edit_transaction_account_to
                } else {
                    R.string.screen_add_or_edit_transaction_account
                },
                filteredCategories = filteredCategories,
                transactionTypesForNewTransactionChipUIData = addTransactionScreenData?.validTransactionTypesForNewTransaction
                    ?.map { transactionType ->
                        ChipUIData(
                            text = transactionType.title,
                        )
                    }
                    ?.toImmutableList()
                    .orEmpty(),
                titleSuggestions = titleSuggestions.orEmpty(),
                titleSuggestionsChipUIData = titleSuggestions
                    ?.map { title ->
                        ChipUIData(
                            text = title,
                        )
                    }
                    ?.toImmutableList()
                    .orEmpty(),
                accounts = addTransactionScreenData?.accounts.orEmpty(),
                transactionForValuesChipUIData = addTransactionScreenData?.transactionForValues
                    ?.map { transactionFor ->
                        ChipUIData(
                            text = transactionFor.titleToDisplay,
                        )
                    }
                    ?.toImmutableList()
                    .orEmpty(),
                currentLocalDate = dateTimeUtil.getCurrentLocalDate().orMin(),
                selectedTransactionType = selectedTransactionType,
            ),
            events = AddTransactionScreenUIStateEvents(
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
        )
    }
}
