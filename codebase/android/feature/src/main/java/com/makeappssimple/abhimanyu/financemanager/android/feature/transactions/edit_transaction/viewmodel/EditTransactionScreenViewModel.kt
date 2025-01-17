@file:Suppress("UnusedPrivateMember")

package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.ApplicationScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeKit
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.filter
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orMin
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
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
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LogKit
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultAccount
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultInvestmentCategory
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.bottomsheet.EditTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.state.AccountFromText
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.state.AccountToText
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.state.EditTransactionScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.state.EditTransactionScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.usecase.EditTransactionScreenDataValidationUseCase
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.navigation.EditTransactionScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class EditTransactionScreenViewModel @Inject constructor(
    @ApplicationScope coroutineScope: CoroutineScope,
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
    private val dateTimeKit: DateTimeKit,
    private val editTransactionScreenDataValidationUseCase: EditTransactionScreenDataValidationUseCase,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
    private val getTitleSuggestionsUseCase: GetTitleSuggestionsUseCase,
    private val getTransactionDataUseCase: GetTransactionDataUseCase,
    private val getMaxRefundAmountUseCase: GetMaxRefundAmountUseCase,
    private val myPreferencesRepository: MyPreferencesRepository,
    private val navigationKit: NavigationKit,
    private val updateAccountBalanceAmountUseCase: UpdateAccountBalanceAmountUseCase,
    private val updateTransactionUseCase: UpdateTransactionUseCase,
    internal val logKit: LogKit,
) : ScreenViewModel(
    viewModelScope = coroutineScope,
), EditTransactionScreenUIStateDelegate by EditTransactionScreenUIStateDelegateImpl(
    dateTimeKit = dateTimeKit,
    navigationKit = navigationKit,
) {
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

    private var uiVisibilityState: EditTransactionScreenUiVisibilityState =
        EditTransactionScreenUiVisibilityState.Expense
    private var filteredCategories: ImmutableList<Category> = persistentListOf()
    // endregion

    // region observables
    private val titleSuggestions: MutableStateFlow<ImmutableList<String>> = MutableStateFlow(
        value = persistentListOf(),
    )
    // endregion

    // region uiStateAndStateEvents
    internal val uiState: MutableStateFlow<EditTransactionScreenUIState> =
        MutableStateFlow(
            value = EditTransactionScreenUIState(),
        )
    internal val uiStateEvents: EditTransactionScreenUIStateEvents =
        EditTransactionScreenUIStateEvents(
            clearAmount = ::clearAmount,
            clearTitle = ::clearTitle,
            navigateUp = ::navigateUp,
            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
            resetScreenSnackbarType = ::resetScreenSnackbarType,
            setAccountFrom = ::updateAccountFrom,
            setAccountTo = ::updateAccountTo,
            setAmount = ::updateAmount,
            setCategory = ::updateCategory,
            setIsTransactionDatePickerDialogVisible = ::updateIsTransactionDatePickerDialogVisible,
            setIsTransactionTimePickerDialogVisible = ::updateIsTransactionTimePickerDialogVisible,
            setScreenBottomSheetType = ::updateScreenBottomSheetType,
            setSelectedTransactionForIndex = ::updateSelectedTransactionForIndex,
            setSelectedTransactionTypeIndex = ::updateSelectedTransactionTypeIndex,
            setTitle = ::updateTitle,
            setTransactionDate = ::updateTransactionDate,
            setTransactionTime = ::updateTransactionTime,
            updateTransaction = {
                updateTransaction(
                    uiState = uiState.value,
                )
            },
        )
    // endregion

    // region initViewModel
    internal fun initViewModel() {
        observeData()
        fetchData()
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
        updateSelectedTransactionTypeIndex(
            validTransactionTypesForNewTransaction.indexOf(
                element = TransactionType.REFUND,
            )
        )
        updateAmount(maxRefundAmount.orEmpty().value.toString())
        updateCategory(originalTransactionData.category)
        updateAccountFrom(null)
        updateAccountTo(originalTransactionData.accountFrom)
        updateSelectedTransactionForIndex(
            transactionForValues.indexOf(
                element = transactionForValues.firstOrNull {
                    it.id == originalTransactionData.transaction.id
                },
            )
        )
    }

    private fun processInitialDataForOtherTransactions() {
        updateCategory(defaultExpenseCategory)
        updateAccountFrom(defaultAccount)
        updateAccountTo(defaultAccount)
        updateSelectedTransactionTypeIndex(
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
                val validationState = editTransactionScreenDataValidationUseCase(
                    accountFrom = accountFrom,
                    accountTo = accountTo,
                    maxRefundAmount = maxRefundAmount,
                    amount = amount.text,
                    title = title.text,
                    selectedTransactionType = selectedTransactionType,
                )
                uiState.update {
                    EditTransactionScreenUIState(
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
                        isCtaButtonEnabled = validationState.isCtaButtonEnabled,
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
                        currentLocalDate = dateTimeKit.getCurrentLocalDate().orMin(),
                        transactionDate = transactionDate,
                        transactionTime = transactionTime,
                        amountErrorText = validationState.amountErrorText,
                        amount = amount,
                        title = title,
                    )
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
        updateUiVisibilityState(EditTransactionScreenUiVisibilityState.Income)

        updateCategory(originalTransactionData?.category ?: defaultIncomeCategory)
        clearTitle()
        updateAccountFrom(null)
        updateAccountTo(originalTransactionData?.accountTo ?: defaultAccount)
    }

    private fun handleSelectedTransactionTypeChangeToExpense() {
        updateUiVisibilityState(EditTransactionScreenUiVisibilityState.Expense)

        updateCategory(originalTransactionData?.category ?: defaultExpenseCategory)
        clearTitle()
        updateAccountFrom(originalTransactionData?.accountFrom ?: defaultAccount)
        updateAccountTo(null)
    }

    private fun handleSelectedTransactionTypeChangeToTransfer() {
        updateUiVisibilityState(EditTransactionScreenUiVisibilityState.Transfer)

        clearTitle()
        updateAccountFrom(originalTransactionData?.accountFrom ?: defaultAccount)
        updateAccountTo(originalTransactionData?.accountTo ?: defaultAccount)
    }

    private fun handleSelectedTransactionTypeChangeToInvestment() {
        updateUiVisibilityState(EditTransactionScreenUiVisibilityState.Investment)

        updateCategory(originalTransactionData?.category ?: defaultInvestmentCategory)
        clearTitle()
        updateAccountFrom(originalTransactionData?.accountFrom ?: defaultAccount)
        updateAccountTo(null)
    }

    private fun handleSelectedTransactionTypeChangeToRefund() {
        updateUiVisibilityState(EditTransactionScreenUiVisibilityState.Refund)

        updateAmount(maxRefundAmount.orEmpty().value.toString())
        updateAccountTo(originalTransactionData?.accountFrom)
        updateTransactionDate(
            updatedTransactionDate = dateTimeKit.getLocalDate(
                timestamp = originalTransactionData?.transaction?.transactionTimestamp.orZero(),
            ),
        )
        updateTransactionTime(
            updatedTransactionTime = dateTimeKit.getLocalTime(
                timestamp = originalTransactionData?.transaction?.transactionTimestamp.orZero(),
            ),
        )
    }

    private fun updateUiVisibilityState(
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
}
