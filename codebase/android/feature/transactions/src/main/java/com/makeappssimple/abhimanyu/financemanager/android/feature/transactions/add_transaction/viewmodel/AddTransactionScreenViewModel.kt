package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.filter
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNullOrBlank
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orMin
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toIntOrZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toLongOrZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.GetAllCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetMaxRefundAmountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetTitleSuggestionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.GetTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.InsertTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultAccount
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultInvestmentCategory
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.bottomsheet.AddTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.state.AccountFromText
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.state.AccountToText
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.state.AddTransactionScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.state.AddTransactionScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.state.AddTransactionScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.navigation.AddTransactionScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class AddTransactionScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
    private val dateTimeUtil: DateTimeUtil,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getAllAccountsUseCase: GetAllAccountsUseCase,
    private val getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
    private val getTitleSuggestionsUseCase: GetTitleSuggestionsUseCase,
    private val getTransactionDataUseCase: GetTransactionDataUseCase,
    private val getMaxRefundAmountUseCase: GetMaxRefundAmountUseCase,
    private val insertTransactionUseCase: InsertTransactionUseCase,
    private val myPreferencesRepository: MyPreferencesRepository,
    private val navigator: Navigator,
) : ScreenViewModel(),
    AddTransactionScreenUIStateDelegate by AddTransactionScreenUIStateDelegateImpl(
        dateTimeUtil = dateTimeUtil,
        insertTransactionUseCase = insertTransactionUseCase,
        navigator = navigator,
    ) {
    // region screen args
    private val screenArgs = AddTransactionScreenArgs(
        savedStateHandle = savedStateHandle,
        stringDecoder = stringDecoder,
    )
    // endregion

    // region initial data
    private var maxRefundAmount: Amount? = null

    private var defaultAccount: Account? = null
    private var defaultExpenseCategory: Category? = null
    private var defaultIncomeCategory: Category? = null
    private var defaultInvestmentCategory: Category? = null

    private var accounts: ImmutableList<Account> = persistentListOf()
    private var categories: ImmutableList<Category> = persistentListOf()
    private var validTransactionTypesForNewTransaction: ImmutableList<TransactionType> =
        persistentListOf()

    private var uiVisibilityState: AddTransactionScreenUiVisibilityState =
        AddTransactionScreenUiVisibilityState.Expense
    private var filteredCategories: ImmutableList<Category> = persistentListOf()
    private var amountErrorText: String? = null
    private var isCtaButtonEnabled: Boolean = false
    // endregion

    // region observables
    private val titleSuggestions: MutableStateFlow<ImmutableList<String>> = MutableStateFlow(
        value = persistentListOf(),
    )
    // endregion

    // region uiStateAndStateEvents
    internal val uiStateAndStateEvents: MutableStateFlow<AddTransactionScreenUIStateAndStateEvents> =
        MutableStateFlow(
            value = AddTransactionScreenUIStateAndStateEvents(),
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
            completeLoading()
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
        val originalTransactionId = getOriginalTransactionId()
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
        getOriginalTransactionId() ?: return
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
                    AddTransactionScreenUIStateAndStateEvents(
                        state = AddTransactionScreenUIState(
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
                            isBottomSheetVisible = screenBottomSheetType != AddTransactionScreenBottomSheetType.None,
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
                        events = AddTransactionScreenUIStateEvents(
                            clearAmount = ::clearAmount,
                            clearTitle = ::clearTitle,
                            insertTransaction = {
                                insertTransaction(
                                    coroutineScope = viewModelScope,
                                )
                            },
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
                completeLoading()
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
        setUiVisibilityState(AddTransactionScreenUiVisibilityState.Income)

        setCategory(originalTransactionData?.category ?: defaultIncomeCategory)
        clearTitle()
        setAccountFrom(null)
        setAccountTo(originalTransactionData?.accountTo ?: defaultAccount)
    }

    private fun handleSelectedTransactionTypeChangeToExpense() {
        setUiVisibilityState(AddTransactionScreenUiVisibilityState.Expense)

        setCategory(originalTransactionData?.category ?: defaultExpenseCategory)
        clearTitle()
        setAccountFrom(originalTransactionData?.accountFrom ?: defaultAccount)
        setAccountTo(null)
    }

    private fun handleSelectedTransactionTypeChangeToTransfer() {
        setUiVisibilityState(AddTransactionScreenUiVisibilityState.Transfer)

        clearTitle()
        setAccountFrom(originalTransactionData?.accountFrom ?: defaultAccount)
        setAccountTo(originalTransactionData?.accountTo ?: defaultAccount)
    }

    private fun handleSelectedTransactionTypeChangeToInvestment() {
        setUiVisibilityState(AddTransactionScreenUiVisibilityState.Investment)

        setCategory(originalTransactionData?.category ?: defaultInvestmentCategory)
        clearTitle()
        setAccountFrom(originalTransactionData?.accountFrom ?: defaultAccount)
        setAccountTo(null)
    }

    private fun handleSelectedTransactionTypeChangeToRefund() {
        setUiVisibilityState(AddTransactionScreenUiVisibilityState.Refund)

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
        updatedUiVisibilityState: AddTransactionScreenUiVisibilityState,
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
