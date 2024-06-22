package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.capitalizeWords
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combineAndCollectLatest
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
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toEpochMilli
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
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.UpdateTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transactionfor.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.sortOrder
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.chip.ChipUIData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultAccount
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultInvestmentCategory
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.bottomsheet.AddTransactionScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.state.AddTransactionScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.state.AddTransactionScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.state.AddTransactionScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.navigation.AddTransactionScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.coroutineScope
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
    private val updateTransactionUseCase: UpdateTransactionUseCase,
) : ScreenViewModel, ViewModel() {
    private val screenArgs = AddTransactionScreenArgs(
        savedStateHandle = savedStateHandle,
        stringDecoder = stringDecoder,
    )

    // region initial data
    private var originalTransactionData: TransactionData? = null
    private var maxRefundAmount: Amount? = null

    private var defaultDataIdFromDataStore: DefaultDataId? = null
    private var defaultAccount: Account? = null
    private var defaultExpenseCategory: Category? = null
    private var defaultIncomeCategory: Category? = null
    private var defaultInvestmentCategory: Category? = null

    private var accounts: MutableList<Account> = mutableListOf()
    private var categories: MutableList<Category> = mutableListOf()
    private var transactionForValues: MutableList<TransactionFor> = mutableListOf()
    private var validTransactionTypesForNewTransaction: MutableList<TransactionType> =
        mutableListOf()
    // endregion

    // region UI data
    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    private val screenBottomSheetType: MutableStateFlow<AddTransactionScreenBottomSheetType> =
        MutableStateFlow(
            value = AddTransactionScreenBottomSheetType.None,
        )
    private val uiVisibilityState: MutableStateFlow<AddTransactionScreenUiVisibilityState> =
        MutableStateFlow(
            value = AddTransactionScreenUiVisibilityState.Expense,
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
    private val selectedTransactionType: MutableStateFlow<TransactionType?> = MutableStateFlow(
        value = null,
    )
    // endregion

    internal val uiStateAndStateEvents: MutableStateFlow<AddTransactionScreenUIStateAndStateEvents> =
        MutableStateFlow(
            value = AddTransactionScreenUIStateAndStateEvents(),
        )

    internal fun initViewModel() {
        fetchData()
        observeData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            coroutineScope {
                joinAll(
                    launch {
                        defaultDataIdFromDataStore = myPreferencesRepository.getDefaultDataId()
                    },
                    launch {
                        accounts.addAll(
                            getAllAccountsUseCase()
                                .sortedWith(
                                    comparator = compareBy<Account> {
                                        it.type.sortOrder
                                    }.thenByDescending {
                                        it.balanceAmount.value
                                    }
                                )
                        )
                    },
                    launch {
                        categories.addAll(getAllCategoriesUseCase())
                    },
                    launch {
                        transactionForValues.addAll(getAllTransactionForValuesUseCase())
                    },
                )
            }
            processData()
            isLoading.update {
                false
            }
        }
    }

    private fun observeData() {
        observeForUiStateAndStateEventsChanges()
        observeForTitleSuggestions()
        observeForSelectedTransactionType()
    }

    private fun observeForUiStateAndStateEventsChanges() {
        viewModelScope.launch {
            combineAndCollectLatest(
                isLoading,
                screenBottomSheetType,
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
                uiVisibilityState,
                titleSuggestions,
                selectedTransactionType,
            ) {
                    (
                        isLoading,
                        screenBottomSheetType,
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
                        uiVisibilityState,
                        titleSuggestions,
                        selectedTransactionType,
                    ),
                ->

                selectedTransactionType ?: return@combineAndCollectLatest
                var amountErrorText: String? = null
                val filteredCategories: ImmutableList<Category> = getFilteredCategories(
                    selectedTransactionType = selectedTransactionType,
                )
                val isCtaButtonEnabled: Boolean = getIsCtaButtonEnabled(
                    selectedTransactionType = selectedTransactionType,
                    amount = amount,
                    title = title,
                    amountErrorText = amountErrorText,
                    accountFrom = accountFrom,
                    accountTo = accountTo,
                    setAmountErrorText = {
                        amountErrorText = it
                    },
                )


                uiStateAndStateEvents.update {
                    AddTransactionScreenUIStateAndStateEvents(
                        state = AddTransactionScreenUIState(
                            screenBottomSheetType = screenBottomSheetType,
                            isTransactionDatePickerDialogVisible = isTransactionDatePickerDialogVisible,
                            isTransactionTimePickerDialogVisible = isTransactionTimePickerDialogVisible,
                            isLoading = isLoading,
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
                            transactionTypesForNewTransactionChipUIData = validTransactionTypesForNewTransaction
                                .map { transactionType ->
                                    ChipUIData(
                                        text = transactionType.title,
                                    )
                                },
                            titleSuggestions = titleSuggestions,
                            titleSuggestionsChipUIData = titleSuggestions
                                .map { title ->
                                    ChipUIData(
                                        text = title,
                                    )
                                },
                            accounts = accounts.orEmpty(),
                            transactionForValuesChipUIData = transactionForValues
                                .map { transactionFor ->
                                    ChipUIData(
                                        text = transactionFor.titleToDisplay,
                                    )
                                },
                            currentLocalDate = dateTimeUtil.getCurrentLocalDate().orMin(),
                            selectedTransactionType = selectedTransactionType,
                        ),
                        events = AddTransactionScreenUIStateEvents(
                            clearAmount = ::clearAmount,
                            clearTitle = ::clearTitle,
                            insertTransaction = ::insertTransaction,
                            navigateUp = ::navigateUp,
                            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
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

    private fun observeForSelectedTransactionType() {
        viewModelScope.launch {
            selectedTransactionTypeIndex.collectLatest { selectedTransactionTypeIndex ->
                validTransactionTypesForNewTransaction.getOrNull(selectedTransactionTypeIndex)
                    ?.let { updatedSelectedTransactionType ->
                        handleSelectedTransactionTypeChange(
                            updatedSelectedTransactionType,
                        )
                        selectedTransactionType.update {
                            updatedSelectedTransactionType
                        }
                    }
            }
        }
    }

    private suspend fun processData() {
        updateDefaultCategories()
        updateDefaultAccount()
        updateValidTransactionTypesForNewTransaction()
        updateOriginalTransactionData()
        updateMaxRefundAmount()
        processInitialData()
    }

    private fun updateDefaultCategories() {
        defaultExpenseCategory = defaultDataIdFromDataStore?.expenseCategory?.let { categoryId ->
            getCategory(
                categoryId = categoryId,
            )
        } ?: categories.firstOrNull { category ->
            isDefaultExpenseCategory(
                category = category.title,
            )
        }
        defaultIncomeCategory = defaultDataIdFromDataStore?.incomeCategory?.let { categoryId ->
            getCategory(
                categoryId = categoryId,
            )
        } ?: categories.firstOrNull { category ->
            isDefaultIncomeCategory(
                category = category.title,
            )
        }
        defaultInvestmentCategory =
            defaultDataIdFromDataStore?.investmentCategory?.let { categoryId ->
                getCategory(
                    categoryId = categoryId,
                )
            } ?: categories.firstOrNull { category ->
                isDefaultInvestmentCategory(
                    category = category.title,
                )
            }
    }

    private fun updateDefaultAccount() {
        defaultAccount = defaultDataIdFromDataStore?.account?.let { accountId ->
            getAccount(
                accountId = accountId,
            )
        } ?: accounts.firstOrNull { account ->
            isDefaultAccount(
                account = account.name,
            )
        }
    }

    private fun updateValidTransactionTypesForNewTransaction() {
        val validTransactionTypes = mutableListOf(
            TransactionType.INCOME,
            TransactionType.EXPENSE,
            TransactionType.INVESTMENT,
        )
        if (accounts.size > 1) {
            validTransactionTypes.add(TransactionType.TRANSFER)
        }
        validTransactionTypesForNewTransaction.addAll(validTransactionTypes)
    }

    private suspend fun updateOriginalTransactionData() {
        val originalTransactionId = getOriginalTransactionId() ?: return
        originalTransactionData = getTransactionDataUseCase(
            id = originalTransactionId,
        )
    }

    private suspend fun updateMaxRefundAmount() {
        getOriginalTransactionId()?.let {
            maxRefundAmount = getMaxRefundAmountUseCase(it)
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

    private fun getOriginalTransactionId(): Int? {
        return screenArgs.originalTransactionId
    }

    private fun getFilteredCategories(
        selectedTransactionType: TransactionType,
    ): ImmutableList<Category> {
        return categories.filter { category ->
            category.transactionType == selectedTransactionType
        }
    }

    private fun getIsCtaButtonEnabled(
        selectedTransactionType: TransactionType,
        amount: TextFieldValue,
        title: TextFieldValue,
        amountErrorText: String?,
        accountFrom: Account?,
        accountTo: Account?,
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
                val maxRefundAmountValue = maxRefundAmount?.value.orZero()
                if (amountErrorText.isNull() &&
                    (amount.text.toLongOrZero() > maxRefundAmountValue)
                ) {
                    setAmountErrorText(
                        maxRefundAmount?.run {
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
    ) {
        when (selectedTransactionType) {
            TransactionType.INCOME -> {
                setUiVisibilityState(AddTransactionScreenUiVisibilityState.Income)

                val updatedCategory =
                    if (selectedTransactionType == originalTransactionData?.transaction?.transactionType) {
                        originalTransactionData?.category ?: defaultIncomeCategory
                    } else {
                        defaultIncomeCategory
                    }
                setCategory(updatedCategory)

                setAccountFrom(null)
                setAccountTo(
                    originalTransactionData?.accountTo
                        ?: defaultAccount
                )
            }

            TransactionType.EXPENSE -> {
                setUiVisibilityState(AddTransactionScreenUiVisibilityState.Expense)

                val updatedCategory =
                    if (selectedTransactionType == originalTransactionData?.transaction?.transactionType) {
                        originalTransactionData?.category ?: defaultExpenseCategory
                    } else {
                        defaultExpenseCategory
                    }
                setCategory(updatedCategory)

                setAccountFrom(
                    originalTransactionData?.accountFrom
                        ?: defaultAccount,
                )
                setAccountTo(null)
            }

            TransactionType.TRANSFER -> {
                setUiVisibilityState(AddTransactionScreenUiVisibilityState.Transfer)

                setAccountFrom(
                    originalTransactionData?.accountFrom
                        ?: defaultAccount,
                )
                setAccountTo(
                    originalTransactionData?.accountTo
                        ?: defaultAccount,
                )
            }

            TransactionType.ADJUSTMENT -> {
            }

            TransactionType.INVESTMENT -> {
                setUiVisibilityState(AddTransactionScreenUiVisibilityState.Investment)

                val updatedCategory =
                    if (selectedTransactionType == originalTransactionData?.transaction?.transactionType) {
                        originalTransactionData?.category
                            ?: defaultInvestmentCategory
                    } else {
                        defaultInvestmentCategory
                    }
                setCategory(updatedCategory)

                setAccountFrom(
                    originalTransactionData?.accountFrom
                        ?: defaultAccount
                )
                setAccountTo(null)
            }

            TransactionType.REFUND -> {
                setUiVisibilityState(AddTransactionScreenUiVisibilityState.Refund)
            }
        }
    }

    private fun processInitialData() {
        val originalTransactionData = originalTransactionData
        if (originalTransactionData != null) {
            setCategory(originalTransactionData.category)
            setAccountFrom(originalTransactionData.accountFrom)
            setAccountTo(originalTransactionData.accountTo)
            setSelectedTransactionTypeIndex(
                validTransactionTypesForNewTransaction.indexOf(
                    element = TransactionType.REFUND,
                )
            )
            setSelectedTransactionForIndex(
                transactionForValues.indexOf(
                    element = transactionForValues.firstOrNull {
                        it.id == originalTransactionData.transaction.id
                    },
                )
            )
        } else {
            setCategory(defaultExpenseCategory)
            setAccountFrom(defaultAccount)
            setAccountTo(defaultAccount)
            setSelectedTransactionTypeIndex(
                validTransactionTypesForNewTransaction.indexOf(
                    element = TransactionType.EXPENSE,
                )
            )
        }
    }

    private fun setUiVisibilityState(
        updatedUiVisibilityState: AddTransactionScreenUiVisibilityState,
    ) {
        uiVisibilityState.update {
            updatedUiVisibilityState
        }
    }

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

    private fun insertTransaction() {
        val amountValue = uiStateAndStateEvents.value.state.uiState.amount.text.toLongOrZero()
        val amount = Amount(
            value = amountValue,
        )
        val categoryId = when (uiStateAndStateEvents.value.state.selectedTransactionType) {
            TransactionType.INCOME -> {
                uiStateAndStateEvents.value.state.uiState.category?.id
            }

            TransactionType.EXPENSE -> {
                uiStateAndStateEvents.value.state.uiState.category?.id
            }

            TransactionType.TRANSFER -> {
                null
            }

            TransactionType.ADJUSTMENT -> {
                null
            }

            TransactionType.INVESTMENT -> {
                uiStateAndStateEvents.value.state.uiState.category?.id
            }

            TransactionType.REFUND -> {
                uiStateAndStateEvents.value.state.uiState.category?.id
            }
        }
        val accountFromId = when (uiStateAndStateEvents.value.state.selectedTransactionType) {
            TransactionType.INCOME -> {
                null
            }

            TransactionType.EXPENSE -> {
                uiStateAndStateEvents.value.state.uiState.accountFrom?.id
            }

            TransactionType.TRANSFER -> {
                uiStateAndStateEvents.value.state.uiState.accountFrom?.id
            }

            TransactionType.ADJUSTMENT -> {
                null
            }

            TransactionType.INVESTMENT -> {
                uiStateAndStateEvents.value.state.uiState.accountFrom?.id
            }

            TransactionType.REFUND -> {
                null
            }
        }
        val accountToId = when (uiStateAndStateEvents.value.state.selectedTransactionType) {
            TransactionType.INCOME -> {
                uiStateAndStateEvents.value.state.uiState.accountTo?.id
            }

            TransactionType.EXPENSE -> {
                null
            }

            TransactionType.TRANSFER -> {
                uiStateAndStateEvents.value.state.uiState.accountTo?.id
            }

            TransactionType.ADJUSTMENT -> {
                null
            }

            TransactionType.INVESTMENT -> {
                null
            }

            TransactionType.REFUND -> {
                uiStateAndStateEvents.value.state.uiState.accountTo?.id
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
                uiStateAndStateEvents.value.state.uiState.title.text.capitalizeWords()
            }
        }
        val transactionForId: Int =
            when (uiStateAndStateEvents.value.state.selectedTransactionType) {
                TransactionType.INCOME -> {
                    1
                }

                TransactionType.EXPENSE -> {
                    uiStateAndStateEvents.value.state.uiState.selectedTransactionForIndex
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
                uiStateAndStateEvents.value.state.uiState.transactionDate,
                uiStateAndStateEvents.value.state.uiState.transactionTime
            )
            .toEpochMilli()

        val accountFrom = if (accountFromId.isNotNull()) {
            uiStateAndStateEvents.value.state.uiState.accountFrom
        } else {
            null
        }
        val accountTo = if (accountToId.isNotNull()) {
            uiStateAndStateEvents.value.state.uiState.accountTo
        } else {
            null
        }
        val transaction = Transaction(
            amount = amount,
            categoryId = categoryId,
            originalTransactionId = originalTransactionData?.transaction?.id,
            accountFromId = accountFromId,
            accountToId = accountToId,
            title = title,
            creationTimestamp = dateTimeUtil.getCurrentTimeMillis(),
            transactionTimestamp = transactionTimestamp,
            transactionForId = transactionForId,
            transactionType = uiStateAndStateEvents.value.state.selectedTransactionType,
        )

        viewModelScope.launch {
            val id = insertTransactionUseCase(
                amountValue = amountValue,
                accountFrom = accountFrom,
                accountTo = accountTo,
                transaction = transaction,
            )

            // Only for refund transaction
            originalTransactionData?.transaction?.let { originalTransaction ->
                val refundTransactionIds = originalTransaction.refundTransactionIds?.run {
                    originalTransaction.refundTransactionIds?.toMutableList()
                } ?: mutableListOf()
                refundTransactionIds.add(id.toInt())
                updateTransactionUseCase(
                    originalTransaction = originalTransaction,
                    updatedTransaction = originalTransaction.copy(
                        refundTransactionIds = refundTransactionIds,
                    ),
                )
            }
            navigator.navigateUp()
        }
    }

    private fun navigateUp() {
        navigator.navigateUp()
    }

    private fun resetScreenBottomSheetType() {
        setScreenBottomSheetType(
            updatedAddTransactionScreenBottomSheetType = AddTransactionScreenBottomSheetType.None,
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
        updatedAddTransactionScreenBottomSheetType: AddTransactionScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedAddTransactionScreenBottomSheetType
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
    // endregion
}
