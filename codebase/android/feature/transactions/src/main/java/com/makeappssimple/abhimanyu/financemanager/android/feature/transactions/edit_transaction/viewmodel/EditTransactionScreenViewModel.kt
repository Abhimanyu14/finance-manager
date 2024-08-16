@file:Suppress("UnusedPrivateMember")

package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.capitalizeWords
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.filter
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.filterDigits
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNullOrBlank
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orMin
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toIntOrZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toLongOrZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.UpdateAccountBalanceAmountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.GetAllCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetMaxRefundAmountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetTitleSuggestionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.UpdateTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultAccount
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultInvestmentCategory
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.bottomsheet.EditTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.snackbar.EditTransactionScreenSnackbarType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.state.AccountFromText
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.state.AccountToText
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.state.EditTransactionScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.state.EditTransactionScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.state.EditTransactionScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.navigation.EditTransactionScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
public class EditTransactionScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
    private val dateTimeUtil: DateTimeUtil,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
    private val getTitleSuggestionsUseCase: GetTitleSuggestionsUseCase,
    private val getTransactionDataUseCase: GetTransactionDataUseCase,
    private val getMaxRefundAmountUseCase: GetMaxRefundAmountUseCase,
    private val myPreferencesRepository: MyPreferencesRepository,
    private val navigator: Navigator,
    private val updateAccountBalanceAmountUseCase: UpdateAccountBalanceAmountUseCase,
    private val updateTransactionUseCase: UpdateTransactionUseCase,
) : ScreenViewModel() {
    // region screen args
    private val screenArgs = EditTransactionScreenArgs(
        savedStateHandle = savedStateHandle,
        stringDecoder = stringDecoder,
    )
    // endregion

    // region initial data
    private var originalTransactionData: TransactionData? = null
    private var maxRefundAmount: Amount? = null

    private var defaultAccount: Account? = null
    private var defaultExpenseCategory: Category? = null
    private var defaultIncomeCategory: Category? = null
    private var defaultInvestmentCategory: Category? = null

    private var accounts: ImmutableList<Account> = persistentListOf()
    private var categories: ImmutableList<Category> = persistentListOf()
    private var transactionForValues: ImmutableList<TransactionFor> = persistentListOf()
    private var validTransactionTypesForNewTransaction: ImmutableList<TransactionType> =
        persistentListOf()
    private var selectedTransactionType: TransactionType? = null

    private var uiVisibilityState: EditTransactionScreenUiVisibilityState =
        EditTransactionScreenUiVisibilityState.Expense
    private var filteredCategories: ImmutableList<Category> = persistentListOf()
    private var amountErrorText: String? = null
    private var isCtaButtonEnabled: Boolean = false
    // endregion

    // region UI data
    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    private val screenBottomSheetType: MutableStateFlow<EditTransactionScreenBottomSheetType> =
        MutableStateFlow(
            value = EditTransactionScreenBottomSheetType.None,
        )
    private val screenSnackbarType: MutableStateFlow<EditTransactionScreenSnackbarType> =
        MutableStateFlow(
            value = EditTransactionScreenSnackbarType.None,
        )
    private val selectedTransactionTypeIndex: MutableStateFlow<Int> =
        MutableStateFlow(
            value = 0,
        )
    private val amount: MutableStateFlow<TextFieldValue> =
        MutableStateFlow(
            value = TextFieldValue(),
        )
    private val category: MutableStateFlow<Category?> =
        MutableStateFlow(
            value = null,
        )
    private val title: MutableStateFlow<TextFieldValue> =
        MutableStateFlow(
            value = TextFieldValue(),
        )
    private val selectedTransactionForIndex: MutableStateFlow<Int> =
        MutableStateFlow(
            value = 0,
        )
    private val accountFrom: MutableStateFlow<Account?> =
        MutableStateFlow(
            value = null,
        )
    private val accountTo: MutableStateFlow<Account?> =
        MutableStateFlow(
            value = null,
        )
    private val transactionDate: MutableStateFlow<LocalDate> =
        MutableStateFlow(
            value = dateTimeUtil.getCurrentLocalDate(),
        )
    private val transactionTime: MutableStateFlow<LocalTime> =
        MutableStateFlow(
            value = dateTimeUtil.getCurrentLocalTime(),
        )
    private val isTransactionDatePickerDialogVisible: MutableStateFlow<Boolean> = MutableStateFlow(
        value = false,
    )
    private val isTransactionTimePickerDialogVisible: MutableStateFlow<Boolean> = MutableStateFlow(
        value = false,
    )
    // endregion

    // region observables
    private val titleSuggestions: MutableStateFlow<ImmutableList<String>> = MutableStateFlow(
        value = persistentListOf(),
    )
    // endregion

    // region uiStateAndStateEvents
    internal val uiStateAndStateEvents: MutableStateFlow<EditTransactionScreenUIStateAndStateEvents> =
        MutableStateFlow(
            value = EditTransactionScreenUIStateAndStateEvents(),
        )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        fetchData()
        observeData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            startLoading()
            joinAll(
                launch {
                    accounts = getAllAccountsUseCase()
                },
                launch {
                    categories = getAllCategoriesUseCase()
                },
                launch {
                    transactionForValues = getAllTransactionForValuesUseCase()
                },
            )
            updateDefaultData()
            updateValidTransactionTypesForNewTransaction()
            updateDataForRefundTransaction()
            processInitialData()
            // completeLoading()
        }
    }

    private fun observeData() {
        observeForUiStateAndStateEvents()
        observeForTitleSuggestions()
        observeForSelectedTransactionType()
    }
    // endregion

    // region updateDefaultData
    private suspend fun updateDefaultData() {
        val defaultDataIdFromDataStore = myPreferencesRepository.getDefaultDataId()
        defaultDataIdFromDataStore?.let {
            updateDefaultCategories(
                defaultDataIdFromDataStore = defaultDataIdFromDataStore,
            )
            updateDefaultAccount(
                defaultDataIdFromDataStore = defaultDataIdFromDataStore,
            )
        }
    }

    private fun updateDefaultCategories(
        defaultDataIdFromDataStore: DefaultDataId,
    ) {
        defaultExpenseCategory = getCategory(
            categoryId = defaultDataIdFromDataStore.expenseCategory,
        ) ?: categories.firstOrNull { category ->
            isDefaultExpenseCategory(
                category = category.title,
            )
        }
        defaultIncomeCategory = getCategory(
            categoryId = defaultDataIdFromDataStore.incomeCategory,
        ) ?: categories.firstOrNull { category ->
            isDefaultIncomeCategory(
                category = category.title,
            )
        }
        defaultInvestmentCategory = getCategory(
            categoryId = defaultDataIdFromDataStore.investmentCategory,
        ) ?: categories.firstOrNull { category ->
            isDefaultInvestmentCategory(
                category = category.title,
            )
        }
    }

    private fun updateDefaultAccount(
        defaultDataIdFromDataStore: DefaultDataId,
    ) {
        defaultAccount = getAccount(
            accountId = defaultDataIdFromDataStore.account,
        ) ?: accounts.firstOrNull { account ->
            isDefaultAccount(
                account = account.name,
            )
        }
    }

    private fun getCategory(
        categoryId: Int,
    ): Category? {
        return categories.find { category ->
            category.id == categoryId
        }
    }

    private fun getAccount(
        accountId: Int,
    ): Account? {
        return accounts.find { account ->
            account.id == accountId
        }
    }
    // endregion

    // region updateValidTransactionTypesForNewTransaction
    private fun updateValidTransactionTypesForNewTransaction() {
        val originalTransactionId = screenArgs.transactionId
        val validTransactionTypes = when {
            originalTransactionId != null -> {
                listOf(
                    TransactionType.REFUND,
                )
            }

            accounts.size > 1 -> {
                listOf(
                    TransactionType.INCOME,
                    TransactionType.EXPENSE,
                    TransactionType.TRANSFER,
                    TransactionType.INVESTMENT,
                )
            }

            else -> {
                listOf(
                    TransactionType.INCOME,
                    TransactionType.EXPENSE,
                    TransactionType.INVESTMENT,
                )
            }
        }
        validTransactionTypesForNewTransaction = validTransactionTypes.toImmutableList()
    }
    // endregion

    // region updateDataForRefundTransaction
    private suspend fun updateDataForRefundTransaction() {
        getOriginalTransactionId()
        updateOriginalTransactionData()
        updateMaxRefundAmount()
    }

    private suspend fun updateOriginalTransactionData() {
        val originalTransactionId = getOriginalTransactionId() ?: return
        originalTransactionData = getTransactionDataUseCase(
            id = originalTransactionId,
        )
    }

    private suspend fun updateMaxRefundAmount() {
        val originalTransactionId = getOriginalTransactionId() ?: return
        maxRefundAmount = getMaxRefundAmountUseCase(
            id = originalTransactionId,
        )
    }
    // endregion

    // region processInitialData
    private fun processInitialData() {
        val originalTransactionData = originalTransactionData
        if (originalTransactionData != null) {
            processInitialDataForRefundTransaction(
                originalTransactionData = originalTransactionData,
            )
        } else {
            processInitialDataForOtherTransactions()
        }
    }

    private fun processInitialDataForRefundTransaction(
        originalTransactionData: TransactionData,
    ) {
        setSelectedTransactionTypeIndex(
            validTransactionTypesForNewTransaction.indexOf(
                element = TransactionType.REFUND,
            )
        )
        setAmount(maxRefundAmount.orEmpty().value.toString())
        setCategory(originalTransactionData.category)
        setAccountFrom(null)
        setAccountTo(originalTransactionData.accountFrom)
        setSelectedTransactionForIndex(
            transactionForValues.indexOf(
                element = transactionForValues.firstOrNull {
                    it.id == originalTransactionData.transaction.id
                },
            )
        )
    }

    private fun processInitialDataForOtherTransactions() {
        setCategory(defaultExpenseCategory)
        setAccountFrom(defaultAccount)
        setAccountTo(defaultAccount)
        setSelectedTransactionTypeIndex(
            validTransactionTypesForNewTransaction.indexOf(
                element = TransactionType.EXPENSE,
            )
        )
    }
    // endregion

    // region observeForUiStateAndStateEvents
    private fun observeForUiStateAndStateEvents() {
        viewModelScope.launch {
            combineAndCollectLatest(
                isLoading,
                screenBottomSheetType,
                screenSnackbarType,
                selectedTransactionTypeIndex,
                amount,
                title,
                accountFrom,
                accountTo,
                isTransactionDatePickerDialogVisible,
                isTransactionTimePickerDialogVisible,
                category,
                selectedTransactionForIndex,
                transactionDate,
                transactionTime,
                titleSuggestions,
            ) {
                    (
                        isLoading,
                        screenBottomSheetType,
                        screenSnackbarType,
                        selectedTransactionTypeIndex,
                        amount,
                        title,
                        accountFrom,
                        accountTo,
                        isTransactionDatePickerDialogVisible,
                        isTransactionTimePickerDialogVisible,
                        category,
                        selectedTransactionForIndex,
                        transactionDate,
                        transactionTime,
                        titleSuggestions,
                    ),
                ->
                updateIsCtaButtonEnabledAndAmountErrorText(
                    selectedTransactionType = selectedTransactionType,
                    amount = amount,
                    title = title,
                    accountFrom = accountFrom,
                    accountTo = accountTo,
                )

                uiStateAndStateEvents.update {
                    EditTransactionScreenUIStateAndStateEvents(
                        state = EditTransactionScreenUIState(
                            accountFrom = accountFrom,
                            accountFromText = if (selectedTransactionType == TransactionType.TRANSFER) {
                                AccountFromText.AccountFrom
                            } else {
                                AccountFromText.Account
                            },
                            accountTo = accountTo,
                            accountToText = if (selectedTransactionType == TransactionType.TRANSFER) {
                                AccountToText.AccountTo
                            } else {
                                AccountToText.Account
                            },
                            screenBottomSheetType = screenBottomSheetType,
                            screenSnackbarType = screenSnackbarType,
                            uiVisibilityState = uiVisibilityState,
                            isBottomSheetVisible = screenBottomSheetType != EditTransactionScreenBottomSheetType.None,
                            isCtaButtonEnabled = isCtaButtonEnabled,
                            isLoading = isLoading,
                            isTransactionDatePickerDialogVisible = isTransactionDatePickerDialogVisible,
                            isTransactionTimePickerDialogVisible = isTransactionTimePickerDialogVisible,
                            category = category,
                            selectedTransactionForIndex = selectedTransactionForIndex,
                            selectedTransactionTypeIndex = selectedTransactionTypeIndex,
                            accounts = accounts.orEmpty(),
                            filteredCategories = filteredCategories,
                            titleSuggestionsChipUIData = titleSuggestions
                                .map { title ->
                                    ChipUIData(
                                        text = title,
                                    )
                                },
                            transactionForValuesChipUIData = transactionForValues
                                .map { transactionFor ->
                                    ChipUIData(
                                        text = transactionFor.titleToDisplay,
                                    )
                                },
                            transactionTypesForNewTransactionChipUIData = validTransactionTypesForNewTransaction
                                .map { transactionType ->
                                    ChipUIData(
                                        text = transactionType.title,
                                    )
                                },
                            titleSuggestions = titleSuggestions,
                            currentLocalDate = dateTimeUtil.getCurrentLocalDate().orMin(),
                            transactionDate = transactionDate,
                            transactionTime = transactionTime,
                            amountErrorText = amountErrorText,
                            amount = amount,
                            title = title,
                        ),
                        events = EditTransactionScreenUIStateEvents(
                            clearAmount = ::clearAmount,
                            clearTitle = ::clearTitle,
                            navigateUp = ::navigateUp,
                            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
                            resetScreenSnackbarType = ::resetScreenSnackbarType,
                            setAccountFrom = ::setAccountFrom,
                            setAccountTo = ::setAccountTo,
                            setAmount = ::setAmount,
                            setCategory = ::setCategory,
                            setIsTransactionDatePickerDialogVisible = ::setIsTransactionDatePickerDialogVisible,
                            setIsTransactionTimePickerDialogVisible = ::setIsTransactionTimePickerDialogVisible,
                            setScreenBottomSheetType = ::setScreenBottomSheetType,
                            setSelectedTransactionForIndex = ::setSelectedTransactionForIndex,
                            setSelectedTransactionTypeIndex = ::setSelectedTransactionTypeIndex,
                            setTitle = ::setTitle,
                            setTransactionDate = ::setTransactionDate,
                            setTransactionTime = ::setTransactionTime,
                            updateTransaction = ::updateTransaction,
                        ),
                    )
                }
            }
        }
    }

    private fun updateIsCtaButtonEnabledAndAmountErrorText(
        selectedTransactionType: TransactionType?,
        amount: TextFieldValue,
        title: TextFieldValue,
        accountFrom: Account?,
        accountTo: Account?,
    ) {
        amountErrorText = null
        if (selectedTransactionType == null) {
            isCtaButtonEnabled = false
            return
        }

        isCtaButtonEnabled = when (selectedTransactionType) {
            TransactionType.INCOME -> {
                title.text.isNotNullOrBlank() && amount.text.toIntOrZero().isNotZero()
            }

            TransactionType.EXPENSE -> {
                title.text.isNotNullOrBlank() && amount.text.toIntOrZero().isNotZero()
            }

            TransactionType.TRANSFER -> {
                accountFrom?.id != accountTo?.id && amount.text.toIntOrZero().isNotZero()
            }

            TransactionType.ADJUSTMENT -> {
                false
            }

            TransactionType.INVESTMENT -> {
                title.text.isNotNullOrBlank() && amount.text.toIntOrZero().isNotZero()
            }

            TransactionType.REFUND -> {
                val maxRefundAmountValue = maxRefundAmount?.value.orZero()
                val enteredAmountValue = amount.text.toLongOrZero()
                if (enteredAmountValue > maxRefundAmountValue) {
                    amountErrorText = maxRefundAmount?.toString()
                    false
                } else {
                    amount.text.toIntOrZero().isNotZero()
                }
            }
        }
    }
    // endregion

    // region observeForTitleSuggestions
    private fun observeForTitleSuggestions() {
        viewModelScope.launch {
            combineAndCollectLatest(
                flow = title,
                flow2 = category
            ) { (title, category) ->
                titleSuggestions.update {
                    category?.id?.let { categoryId ->
                        getTitleSuggestionsUseCase(
                            categoryId = categoryId,
                            enteredTitle = title.text,
                        )
                    } ?: persistentListOf()
                }
            }
        }
    }
    // endregion

    // region observeForSelectedTransactionType
    private fun observeForSelectedTransactionType() {
        viewModelScope.launch {
            selectedTransactionTypeIndex.collectLatest { selectedTransactionTypeIndex ->
                startLoading()
                val updatedSelectedTransactionType =
                    validTransactionTypesForNewTransaction.getOrNull(
                        index = selectedTransactionTypeIndex,
                    )
                if (updatedSelectedTransactionType != null) {
                    handleSelectedTransactionTypeChange(
                        updatedSelectedTransactionType = updatedSelectedTransactionType,
                    )
                }
                // completeLoading()
            }
        }
    }

    private fun handleSelectedTransactionTypeChange(
        updatedSelectedTransactionType: TransactionType,
    ) {
        updateFilteredCategories(
            updatedSelectedTransactionType = updatedSelectedTransactionType,
        )
        when (updatedSelectedTransactionType) {
            TransactionType.INCOME -> {
                handleSelectedTransactionTypeChangeToIncome()
            }

            TransactionType.EXPENSE -> {
                handleSelectedTransactionTypeChangeToExpense()
            }

            TransactionType.TRANSFER -> {
                handleSelectedTransactionTypeChangeToTransfer()
            }

            TransactionType.ADJUSTMENT -> {}

            TransactionType.INVESTMENT -> {
                handleSelectedTransactionTypeChangeToInvestment()
            }

            TransactionType.REFUND -> {
                handleSelectedTransactionTypeChangeToRefund()
            }
        }

        selectedTransactionType = updatedSelectedTransactionType
    }

    private fun updateFilteredCategories(
        updatedSelectedTransactionType: TransactionType,
    ) {
        filteredCategories = categories.filter { category ->
            category.transactionType == updatedSelectedTransactionType
        }
    }

    private fun handleSelectedTransactionTypeChangeToIncome() {
        setUiVisibilityState(EditTransactionScreenUiVisibilityState.Income)

        setCategory(originalTransactionData?.category ?: defaultIncomeCategory)
        clearTitle()
        setAccountFrom(null)
        setAccountTo(originalTransactionData?.accountTo ?: defaultAccount)
    }

    private fun handleSelectedTransactionTypeChangeToExpense() {
        setUiVisibilityState(EditTransactionScreenUiVisibilityState.Expense)

        setCategory(originalTransactionData?.category ?: defaultExpenseCategory)
        clearTitle()
        setAccountFrom(originalTransactionData?.accountFrom ?: defaultAccount)
        setAccountTo(null)
    }

    private fun handleSelectedTransactionTypeChangeToTransfer() {
        setUiVisibilityState(EditTransactionScreenUiVisibilityState.Transfer)

        clearTitle()
        setAccountFrom(originalTransactionData?.accountFrom ?: defaultAccount)
        setAccountTo(originalTransactionData?.accountTo ?: defaultAccount)
    }

    private fun handleSelectedTransactionTypeChangeToInvestment() {
        setUiVisibilityState(EditTransactionScreenUiVisibilityState.Investment)

        setCategory(originalTransactionData?.category ?: defaultInvestmentCategory)
        clearTitle()
        setAccountFrom(originalTransactionData?.accountFrom ?: defaultAccount)
        setAccountTo(null)
    }

    private fun handleSelectedTransactionTypeChangeToRefund() {
        setUiVisibilityState(EditTransactionScreenUiVisibilityState.Refund)

        setAmount(maxRefundAmount.orEmpty().value.toString())
        setAccountTo(originalTransactionData?.accountFrom)
        setTransactionDate(
            updatedTransactionDate = dateTimeUtil.getLocalDate(
                timestamp = originalTransactionData?.transaction?.transactionTimestamp.orZero(),
            ),
        )
        setTransactionTime(
            updatedTransactionTime = dateTimeUtil.getLocalTime(
                timestamp = originalTransactionData?.transaction?.transactionTimestamp.orZero(),
            ),
        )
    }

    private fun setUiVisibilityState(
        updatedUiVisibilityState: EditTransactionScreenUiVisibilityState,
    ) {
        uiVisibilityState = updatedUiVisibilityState
    }
    // endregion

    // region common
    private fun getOriginalTransactionId(): Int? {
        return screenArgs.transactionId
    }
    // endregion

    // region loading
    private fun startLoading() {
        isLoading.update {
            true
        }
    }

    private fun completeLoading() {
        isLoading.update {
            false
        }
    }
    // endregion

    // region state events
    private fun clearAmount() {
        amount.update {
            it.copy(
                text = "",
            )
        }
    }

    private fun clearTitle() {
        title.update {
            it.copy(
                text = "",
            )
        }
    }

    private fun navigateUp() {
        navigator.navigateUp()
    }

    private fun resetScreenBottomSheetType() {
        setScreenBottomSheetType(
            updatedEditTransactionScreenBottomSheetType = EditTransactionScreenBottomSheetType.None,
        )
    }

    private fun resetScreenSnackbarType() {
        setScreenSnackbarType(
            updatedEditTransactionScreenSnackbarType = EditTransactionScreenSnackbarType.None,
        )
    }

    private fun setAccountFrom(
        updatedAccountFrom: Account?,
    ) {
        accountFrom.update {
            updatedAccountFrom
        }
    }

    private fun setAccountTo(
        updatedAccountTo: Account?,
    ) {
        accountTo.update {
            updatedAccountTo
        }
    }

    private fun setAmount(
        updatedAmount: TextFieldValue,
    ) {
        amount.update {
            updatedAmount.copy(
                text = updatedAmount.text.filterDigits(),
            )
        }
    }

    private fun setAmount(
        updatedAmount: String,
    ) {
        amount.update {
            it.copy(
                text = updatedAmount.filterDigits(),
            )
        }
    }

    private fun setCategory(
        updatedCategory: Category?,
    ) {
        category.update {
            updatedCategory
        }
    }

    private fun setIsTransactionDatePickerDialogVisible(
        updatedIsTransactionDatePickerDialogVisible: Boolean,
    ) {
        isTransactionDatePickerDialogVisible.update {
            updatedIsTransactionDatePickerDialogVisible
        }
    }

    private fun setIsTransactionTimePickerDialogVisible(
        updatedIsTransactionTimePickerDialogVisible: Boolean,
    ) {
        isTransactionTimePickerDialogVisible.update {
            updatedIsTransactionTimePickerDialogVisible
        }
    }

    private fun setScreenBottomSheetType(
        updatedEditTransactionScreenBottomSheetType: EditTransactionScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedEditTransactionScreenBottomSheetType
        }
    }

    private fun setScreenSnackbarType(
        updatedEditTransactionScreenSnackbarType: EditTransactionScreenSnackbarType,
    ) {
        screenSnackbarType.update {
            updatedEditTransactionScreenSnackbarType
        }
    }

    private fun setSelectedTransactionForIndex(
        updatedSelectedTransactionForIndex: Int,
    ) {
        selectedTransactionForIndex.update {
            updatedSelectedTransactionForIndex
        }
    }

    private fun setSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    ) {
        selectedTransactionTypeIndex.update {
            updatedSelectedTransactionTypeIndex
        }
    }

    private fun setTitle(
        updatedTitle: TextFieldValue,
    ) {
        title.update {
            updatedTitle
        }
    }

    private fun setTransactionDate(
        updatedTransactionDate: LocalDate,
    ) {
        transactionDate.update {
            updatedTransactionDate
        }
    }

    private fun setTransactionTime(
        updatedTransactionTime: LocalTime,
    ) {
        transactionTime.update {
            updatedTransactionTime
        }
    }

    private fun updateTransaction() {
        // TODO(Abhi): Fix update transaction logic
        val enteredAmountValue = amount.value.text.toLongOrZero()
        val amount = Amount(
            value = enteredAmountValue,
        )
        val categoryId = when (uiStateAndStateEvents.value.state.selectedTransactionType) {
            TransactionType.INCOME -> {
                uiStateAndStateEvents.value.state.category?.id
            }

            TransactionType.EXPENSE -> {
                uiStateAndStateEvents.value.state.category?.id
            }

            TransactionType.TRANSFER -> {
                null
            }

            TransactionType.ADJUSTMENT -> {
                null
            }

            TransactionType.INVESTMENT -> {
                uiStateAndStateEvents.value.state.category?.id
            }

            TransactionType.REFUND -> {
                uiStateAndStateEvents.value.state.category?.id
            }
        }
        val accountFromId = when (uiStateAndStateEvents.value.state.selectedTransactionType) {
            TransactionType.INCOME -> {
                null
            }

            TransactionType.EXPENSE -> {
                uiStateAndStateEvents.value.state.accountFrom?.id
            }

            TransactionType.TRANSFER -> {
                uiStateAndStateEvents.value.state.accountFrom?.id
            }

            TransactionType.ADJUSTMENT -> {
                null
            }

            TransactionType.INVESTMENT -> {
                uiStateAndStateEvents.value.state.accountFrom?.id
            }

            TransactionType.REFUND -> {
                null
            }
        }
        val accountToId = when (uiStateAndStateEvents.value.state.selectedTransactionType) {
            TransactionType.INCOME -> {
                uiStateAndStateEvents.value.state.accountTo?.id
            }

            TransactionType.EXPENSE -> {
                null
            }

            TransactionType.TRANSFER -> {
                uiStateAndStateEvents.value.state.accountTo?.id
            }

            TransactionType.ADJUSTMENT -> {
                null
            }

            TransactionType.INVESTMENT -> {
                null
            }

            TransactionType.REFUND -> {
                uiStateAndStateEvents.value.state.accountTo?.id
            }
        }
        val title = when (uiStateAndStateEvents.value.state.selectedTransactionType) {
            TransactionType.TRANSFER -> {
                TransactionType.TRANSFER.title
            }

            TransactionType.REFUND -> {
                TransactionType.REFUND.title
            }

            else -> {
                uiStateAndStateEvents.value.state.title.text.capitalizeWords()
            }
        }
        val transactionForId: Int =
            when (uiStateAndStateEvents.value.state.selectedTransactionType) {
                TransactionType.INCOME -> {
                    1
                }

                TransactionType.EXPENSE -> {
                    uiStateAndStateEvents.value.state.selectedTransactionForIndex
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
        val transactionTimestamp = LocalDateTime
            .of(
                uiStateAndStateEvents.value.state.transactionDate,
                uiStateAndStateEvents.value.state.transactionTime
            )
            .toEpochMilli()

        val accountFrom = if (accountFromId.isNotNull()) {
            uiStateAndStateEvents.value.state.accountFrom
        } else {
            null
        }
        val accountTo = if (accountToId.isNotNull()) {
            uiStateAndStateEvents.value.state.accountTo
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
            transactionType = uiStateAndStateEvents.value.state.selectedTransactionType,
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

