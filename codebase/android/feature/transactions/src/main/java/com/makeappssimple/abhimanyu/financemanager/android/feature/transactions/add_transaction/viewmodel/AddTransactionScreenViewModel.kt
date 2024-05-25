package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.capitalizeWords
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combine
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.filterDigits
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNullOrBlank
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toIntOrZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toLongOrZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultListStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.UpdateAccountBalanceAmountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.GetAllCategoriesUseCase
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
import com.makeappssimple.abhimanyu.financemanager.android.core.model.minus
import com.makeappssimple.abhimanyu.financemanager.android.core.model.plus
import com.makeappssimple.abhimanyu.financemanager.android.core.model.sortOrder
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultAccount
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultInvestmentCategory
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.screen.AddTransactionScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.navigation.AddTransactionScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject
import kotlin.math.abs

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
    private val insertTransactionUseCase: InsertTransactionUseCase,
    private val myPreferencesRepository: MyPreferencesRepository,
    private val navigator: Navigator,
    private val updateAccountBalanceAmountUseCase: UpdateAccountBalanceAmountUseCase,
    private val updateTransactionUseCase: UpdateTransactionUseCase,
) : ScreenViewModel, ViewModel() {
    private val screenArgs = AddTransactionScreenArgs(
        savedStateHandle = savedStateHandle,
        stringDecoder = stringDecoder,
    )

    // region Transaction data
    private var originalTransactionData: TransactionData? = null
    private var maxRefundAmount: Amount? = null
    // endregion

    // region Default data
    private var defaultAccount: Account? = null
    private var defaultExpenseCategory: Category? = null
    private var defaultIncomeCategory: Category? = null
    private var defaultInvestmentCategory: Category? = null
    private var defaultDataIdFromDataStore: DefaultDataId? = null
    // endregion

    // region Data source
    private var validTransactionTypesForNewTransaction: MutableList<TransactionType> =
        mutableListOf()
    private var categories: MutableList<Category> = mutableListOf()
    private var transactionForValues: MutableList<TransactionFor> = mutableListOf()
    private var accounts: MutableList<Account> = mutableListOf()

    private val titleSuggestions: MutableStateFlow<ImmutableList<String>?> = MutableStateFlow(
        value = null,
    )
    // endregion

    private val uiState: MutableStateFlow<AddTransactionScreenUiStateData> = MutableStateFlow(
        value = AddTransactionScreenUiStateData(
            selectedTransactionTypeIndex = null,
            amount = TextFieldValue(),
            title = TextFieldValue(),
            description = TextFieldValue(),
            category = null,
            selectedTransactionForIndex = 0,
            accountFrom = null,
            accountTo = null,
            transactionDate = dateTimeUtil.getCurrentLocalDate(),
            transactionTime = dateTimeUtil.getCurrentLocalTime(),
        ),
    )
    private val uiVisibilityState: MutableStateFlow<AddTransactionScreenUiVisibilityState> =
        MutableStateFlow(
            value = AddTransactionScreenUiVisibilityState.Expense,
        )

    // Dependant data
    private val selectedTransactionType: MutableStateFlow<TransactionType?> = MutableStateFlow(
        value = null,
    )

    private val filteredCategories: StateFlow<ImmutableList<Category>> =
        selectedTransactionType.map { selectedTransactionType ->
            categories.filter { category ->
                category.transactionType == selectedTransactionType
            }.toImmutableList().orEmpty()
        }.defaultListStateIn(
            scope = viewModelScope,
        )

    private val selectedCategoryId: Flow<Int?> = uiState.map {
        it.category?.id
    }

    private val isCtaButtonEnabled: Flow<Boolean> = combine(
        flow = uiState,
        flow2 = selectedTransactionType,
    ) { uiState, selectedTransactionType ->
        when (selectedTransactionType) {
            TransactionType.INCOME -> {
                uiState.amount.text.isNotNullOrBlank() &&
                        uiState.title.text.isNotNullOrBlank() &&
                        uiState.amount.text.toIntOrZero().isNotZero() &&
                        uiState.amountErrorText.isNull()
            }

            TransactionType.EXPENSE -> {
                uiState.amount.text.isNotNullOrBlank() &&
                        uiState.title.text.isNotNullOrBlank() &&
                        uiState.amount.text.toIntOrZero().isNotZero() &&
                        uiState.amountErrorText.isNull()
            }

            TransactionType.TRANSFER -> {
                uiState.amount.text.isNotNullOrBlank() &&
                        uiState.accountFrom?.id != uiState.accountTo?.id &&
                        uiState.amount.text.toIntOrZero().isNotZero() &&
                        uiState.amountErrorText.isNull()
            }

            TransactionType.ADJUSTMENT -> {
                false
            }

            TransactionType.INVESTMENT -> {
                uiState.amount.text.isNotNullOrBlank() &&
                        uiState.title.text.isNotNullOrBlank() &&
                        uiState.amount.text.toIntOrZero().isNotZero() &&
                        uiState.amountErrorText.isNull()
            }

            TransactionType.REFUND -> {
                val maxRefundAmountValue = maxRefundAmount?.value.orZero()
                if (uiState.amountErrorText.isNull() &&
                    (uiState.amount.text.toLongOrZero() > maxRefundAmountValue)
                ) {
                    updateAddTransactionScreenUiState(
                        updatedAddTransactionScreenUiStateData = uiState.copy(
                            amountErrorText = maxRefundAmount?.run {
                                this.toString()
                            },
                        )
                    )
                    false
                } else if (uiState.amountErrorText.isNotNull() &&
                    (uiState.amount.text.toLongOrZero() <= maxRefundAmountValue)
                ) {
                    updateAddTransactionScreenUiState(
                        updatedAddTransactionScreenUiStateData = uiState.copy(
                            amountErrorText = null,
                        )
                    )
                    false
                } else {
                    uiState.amount.text.isNotNullOrBlank() &&
                            uiState.title.text.isNotNullOrBlank() &&
                            uiState.amount.text.toIntOrZero().isNotZero() &&
                            uiState.amountErrorText.isNull()
                }
            }

            null -> {
                false
            }
        }
    }

    private val isDataFetchCompleted: MutableStateFlow<Boolean> = MutableStateFlow(
        value = false,
    )

    public val screenUIData: StateFlow<MyResult<AddTransactionScreenUIData>?> = combine(
        uiState,
        uiVisibilityState,
        isCtaButtonEnabled,
        filteredCategories,
        titleSuggestions,
        selectedTransactionType,
        isDataFetchCompleted,
    ) {
            uiState,
            uiVisibilityState,
            isCtaButtonEnabled,
            filteredCategories,
            titleSuggestions,
            selectedTransactionType,
            isDataFetchCompleted,
        ->
        if (
            isCtaButtonEnabled.isNull() ||
            filteredCategories.isNull() ||
            accounts.isEmpty() ||
            validTransactionTypesForNewTransaction.isEmpty() ||
            transactionForValues.isEmpty() ||
            selectedTransactionType.isNull() ||
            isDataFetchCompleted.not()
        ) {
            MyResult.Loading
        } else {
            MyResult.Success(
                data = AddTransactionScreenUIData(
                    uiState = uiState,
                    uiVisibilityState = uiVisibilityState,
                    isCtaButtonEnabled = isCtaButtonEnabled,
                    filteredCategories = filteredCategories,
                    accounts = accounts.toImmutableList(),
                    titleSuggestions = titleSuggestions.orEmpty(),
                    transactionTypesForNewTransaction = validTransactionTypesForNewTransaction.toImmutableList(),
                    transactionForValues = transactionForValues.toImmutableList(),
                    currentLocalDate = dateTimeUtil.getCurrentLocalDate(),
                    selectedTransactionType = selectedTransactionType,
                ),
            )
        }
    }.defaultObjectStateIn(
        scope = viewModelScope,
    )

    public fun initViewModel() {
        fetchData()
        observeData()
    }

    public fun insertTransaction() {
        viewModelScope.launch {
            val uiStateValue = uiState.value
            selectedTransactionType.value?.let { selectedTransactionTypeValue ->
                val amountValue = uiStateValue.amount.text.toLongOrZero()
                val amount = Amount(
                    value = amountValue,
                )
                val categoryId = when (selectedTransactionTypeValue) {
                    TransactionType.INCOME -> {
                        uiStateValue.category?.id
                    }

                    TransactionType.EXPENSE -> {
                        uiStateValue.category?.id
                    }

                    TransactionType.TRANSFER -> {
                        null
                    }

                    TransactionType.ADJUSTMENT -> {
                        null
                    }

                    TransactionType.INVESTMENT -> {
                        uiStateValue.category?.id
                    }

                    TransactionType.REFUND -> {
                        originalTransactionData?.category?.id
                    }
                }
                val accountFromId = when (selectedTransactionTypeValue) {
                    TransactionType.INCOME -> {
                        null
                    }

                    TransactionType.EXPENSE -> {
                        uiStateValue.accountFrom?.id
                    }

                    TransactionType.TRANSFER -> {
                        uiStateValue.accountFrom?.id
                    }

                    TransactionType.ADJUSTMENT -> {
                        null
                    }

                    TransactionType.INVESTMENT -> {
                        uiStateValue.accountFrom?.id
                    }

                    TransactionType.REFUND -> {
                        null
                    }
                }
                val accountToId = when (selectedTransactionTypeValue) {
                    TransactionType.INCOME -> {
                        uiStateValue.accountTo?.id
                    }

                    TransactionType.EXPENSE -> {
                        null
                    }

                    TransactionType.TRANSFER -> {
                        uiStateValue.accountTo?.id
                    }

                    TransactionType.ADJUSTMENT -> {
                        null
                    }

                    TransactionType.INVESTMENT -> {
                        null
                    }

                    TransactionType.REFUND -> {
                        uiStateValue.accountTo?.id
                    }
                }
                val title = when (selectedTransactionTypeValue) {
                    TransactionType.TRANSFER -> {
                        TransactionType.TRANSFER.title
                    }

                    TransactionType.REFUND -> {
                        TransactionType.REFUND.title
                    }

                    else -> {
                        uiStateValue.title.text.capitalizeWords()
                    }
                }
                val transactionForId: Int = when (selectedTransactionTypeValue) {
                    TransactionType.INCOME -> {
                        1
                    }

                    TransactionType.EXPENSE -> {
                        transactionForValues.getOrNull(uiStateValue.selectedTransactionForIndex)?.id
                            ?: 1
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
                    .of(uiStateValue.transactionDate, uiStateValue.transactionTime)
                    .toEpochMilli()
                val id = insertTransactionUseCase(
                    amountValue = amountValue,
                    accountFrom = if (accountFromId.isNotNull()) {
                        uiStateValue.accountFrom
                    } else {
                        null
                    },
                    accountTo = if (accountToId.isNotNull()) {
                        uiStateValue.accountTo
                    } else {
                        null
                    },
                    transaction = Transaction(
                        amount = amount,
                        categoryId = categoryId,
                        originalTransactionId = getOriginalTransactionId(),
                        accountFromId = accountFromId,
                        accountToId = accountToId,
                        description = uiStateValue.description.text,
                        title = title,
                        creationTimestamp = dateTimeUtil.getCurrentTimeMillis(),
                        transactionTimestamp = transactionTimestamp,
                        transactionForId = transactionForId,
                        transactionType = selectedTransactionTypeValue,
                    ),
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
    }

    // region UI changes
    public fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    ) {
        updateAddTransactionScreenUiState(
            updatedAddTransactionScreenUiStateData = uiState.value.copy(
                selectedTransactionTypeIndex = updatedSelectedTransactionTypeIndex,
            ),
        )
        selectedTransactionType.value = validTransactionTypesForNewTransaction.getOrNull(
            index = updatedSelectedTransactionTypeIndex,
        )
    }

    public fun updateAmount(
        updatedAmount: TextFieldValue,
    ) {
        updateAddTransactionScreenUiState(
            updatedAddTransactionScreenUiStateData = uiState.value.copy(
                amount = updatedAmount.copy(
                    updatedAmount.text.filterDigits(),
                ),
            ),
        )
    }

    public fun clearAmount() {
        updateAmount(
            updatedAmount = uiState.value.amount.copy(
                text = "",
            ),
        )
    }

    public fun navigateUp() {
        navigator.navigateUp()
    }

    public fun updateTitle(
        updatedTitle: TextFieldValue,
    ) {
        updateAddTransactionScreenUiState(
            updatedAddTransactionScreenUiStateData = uiState.value.copy(
                title = updatedTitle,
            ),
        )
    }

    public fun clearTitle() {
        updateTitle(
            updatedTitle = uiState.value.title.copy(
                text = "",
            ),
        )
    }

    public fun updateDescription(
        updatedDescription: TextFieldValue,
    ) {
        updateAddTransactionScreenUiState(
            updatedAddTransactionScreenUiStateData = uiState.value.copy(
                description = updatedDescription,
            ),
        )
    }

    public fun clearDescription() {
        updateDescription(
            updatedDescription = uiState.value.description.copy(
                text = "",
            ),
        )
    }

    public fun updateCategory(
        updatedCategory: Category?,
    ) {
        updateAddTransactionScreenUiState(
            updatedAddTransactionScreenUiStateData = uiState.value.copy(
                category = updatedCategory,
            ),
        )
    }

    public fun updateSelectedTransactionForIndex(
        updatedSelectedTransactionForIndex: Int,
    ) {
        updateAddTransactionScreenUiState(
            updatedAddTransactionScreenUiStateData = uiState.value.copy(
                selectedTransactionForIndex = updatedSelectedTransactionForIndex,
            ),
        )
    }

    public fun updateAccountFrom(
        updatedAccountFrom: Account?,
    ) {
        updateAddTransactionScreenUiState(
            updatedAddTransactionScreenUiStateData = uiState.value.copy(
                accountFrom = updatedAccountFrom,
            ),
        )
    }

    public fun updateAccountTo(
        updatedAccountTo: Account?,
    ) {
        updateAddTransactionScreenUiState(
            updatedAddTransactionScreenUiStateData = uiState.value.copy(
                accountTo = updatedAccountTo,
            ),
        )
    }

    public fun updateTransactionDate(
        updatedTransactionDate: LocalDate,
    ) {
        updateAddTransactionScreenUiState(
            updatedAddTransactionScreenUiStateData = uiState.value.copy(
                transactionDate = updatedTransactionDate,
            ),
        )
    }

    public fun updateTransactionTime(
        updatedTransactionTime: LocalTime,
    ) {
        updateAddTransactionScreenUiState(
            updatedAddTransactionScreenUiStateData = uiState.value.copy(
                transactionTime = updatedTransactionTime,
            ),
        )
    }

    private fun updateAddTransactionScreenUiState(
        updatedAddTransactionScreenUiStateData: AddTransactionScreenUiStateData,
    ) {
        uiState.update {
            updatedAddTransactionScreenUiStateData
        }
    }

    private fun updateAddTransactionScreenUiVisibilityState(
        updatedAddTransactionScreenUiVisibilityState: AddTransactionScreenUiVisibilityState,
    ) {
        uiVisibilityState.value = updatedAddTransactionScreenUiVisibilityState
    }
    // endregion

    private fun fetchData() {
        viewModelScope.launch {
            awaitAll(
                async {
                    defaultDataIdFromDataStore = myPreferencesRepository.getDefaultDataId()
                },
                async {
                    categories.addAll(getAllCategoriesUseCase())
                },
                async {
                    accounts.addAll(getAllAccountsUseCase()
                        .sortedWith(
                            comparator = compareBy<Account> {
                                it.type.sortOrder
                            }.thenByDescending {
                                it.balanceAmount.value
                            }
                        ))
                },
                async {
                    transactionForValues.addAll(getAllTransactionForValuesUseCase())
                },
            )
            calculateValidTransactionTypesForNewTransaction()
            setDefaultCategory()
            setDefaultAccount()
            if (isAddingRefundTransaction()) {
                getOriginalTransactionData()
            } else {
                setInitialSelectedTransactionType()
            }
            isDataFetchCompleted.update {
                true
            }
        }
    }

    private fun observeData() {
        observeSelectedTransactionType()
        observeSelectedCategory()
    }

    private fun observeSelectedTransactionType() {
        viewModelScope.launch {
            selectedTransactionType.collectLatest { transactionType ->
                transactionType?.let {
                    handleTransactionTypeChange(
                        transactionType = transactionType,
                    )
                }
            }
        }
    }

    private fun handleTransactionTypeChange(
        transactionType: TransactionType,
    ) {
        val uiVisibilityState: AddTransactionScreenUiVisibilityState? =
            when (transactionType) {
                TransactionType.INCOME -> {
                    AddTransactionScreenUiVisibilityState.Income
                }

                TransactionType.EXPENSE -> {
                    AddTransactionScreenUiVisibilityState.Expense
                }

                TransactionType.TRANSFER -> {
                    AddTransactionScreenUiVisibilityState.Transfer
                }

                TransactionType.ADJUSTMENT -> {
                    null
                }

                TransactionType.INVESTMENT -> {
                    AddTransactionScreenUiVisibilityState.Investment
                }

                TransactionType.REFUND -> {
                    AddTransactionScreenUiVisibilityState.Refund
                }
            }
        uiVisibilityState?.let {
            updateAddTransactionScreenUiVisibilityState(
                updatedAddTransactionScreenUiVisibilityState = uiVisibilityState,
            )
        }

        when (transactionType) {
            TransactionType.INCOME -> {
                val updatedCategory =
                    if (transactionType == originalTransactionData?.transaction?.transactionType) {
                        originalTransactionData?.category ?: defaultIncomeCategory
                    } else {
                        defaultIncomeCategory
                    }
                updateCategory(
                    updatedCategory = updatedCategory,
                )

                updateAccountFrom(
                    updatedAccountFrom = null,
                )
                updateAccountTo(
                    updatedAccountTo = originalTransactionData?.accountTo
                        ?: defaultAccount,
                )
            }

            TransactionType.EXPENSE -> {
                val updatedCategory =
                    if (transactionType == originalTransactionData?.transaction?.transactionType) {
                        originalTransactionData?.category
                            ?: defaultExpenseCategory
                    } else {
                        defaultExpenseCategory
                    }
                updateCategory(
                    updatedCategory = updatedCategory,
                )

                updateAccountFrom(
                    updatedAccountFrom = originalTransactionData?.accountFrom
                        ?: defaultAccount,
                )
                updateAccountTo(
                    updatedAccountTo = null,
                )
            }

            TransactionType.TRANSFER -> {
                updateAccountFrom(
                    updatedAccountFrom = originalTransactionData?.accountFrom
                        ?: defaultAccount,
                )
                updateAccountTo(
                    updatedAccountTo = originalTransactionData?.accountTo
                        ?: defaultAccount,
                )
            }

            TransactionType.ADJUSTMENT -> {}

            TransactionType.INVESTMENT -> {
                val updatedCategory =
                    if (transactionType == originalTransactionData?.transaction?.transactionType) {
                        originalTransactionData?.category
                            ?: defaultInvestmentCategory
                    } else {
                        defaultInvestmentCategory
                    }
                updateCategory(
                    updatedCategory = updatedCategory,
                )

                updateAccountFrom(
                    updatedAccountFrom = originalTransactionData?.accountFrom
                        ?: defaultAccount,
                )
                updateAccountTo(
                    updatedAccountTo = null,
                )
            }

            TransactionType.REFUND -> {}
        }
    }

    private fun observeSelectedCategory() {
        viewModelScope.launch {
            combine(
                flow = selectedCategoryId,
                flow2 = uiState,
            ) { selectedCategoryId, uiState ->
                Pair(
                    first = selectedCategoryId,
                    second = uiState,
                )
            }.collectLatest { (selectedCategoryId, uiState) ->
                selectedCategoryId?.let {
                    titleSuggestions.update {
                        getTitleSuggestionsUseCase(
                            categoryId = selectedCategoryId,
                            enteredTitle = uiState.title.text,
                        ).toImmutableList()
                    }
                }
            }
        }
    }

    private fun calculateValidTransactionTypesForNewTransaction() {
        val excludedTransactionTypes = mutableSetOf(
            TransactionType.ADJUSTMENT,
            TransactionType.REFUND
        )
        // Cannot create transfer with single account
        if (accounts.size <= 1) {
            excludedTransactionTypes.add(TransactionType.TRANSFER)
        }

        val transactionTypesRemainingAfterExclusion =
            (TransactionType.entries.toSet() - excludedTransactionTypes).toList()
        validTransactionTypesForNewTransaction.addAll(transactionTypesRemainingAfterExclusion)

        updateSelectedTransactionTypeIndex(
            updatedSelectedTransactionTypeIndex = transactionTypesRemainingAfterExclusion.indexOf(
                element = TransactionType.EXPENSE,
            ),
        )
    }

    private suspend fun getOriginalTransactionData() {
        val originalTransactionId = getOriginalTransactionId() ?: return
        val originalTransactionData = getTransactionDataUseCase(
            id = originalTransactionId,
        ) ?: return
        this.originalTransactionData = originalTransactionData
        if (originalTransactionData.transaction.transactionType == TransactionType.REFUND) {
            calculateMaxRefundAmount()
        }
        updateAddTransactionScreenUiStateWithOriginalTransactionData(
            originalTransaction = originalTransactionData.transaction,
            transactionTypesForNewTransaction = validTransactionTypesForNewTransaction,
            transactionForValues = transactionForValues,
            maxRefundAmount = maxRefundAmount,
        )
    }

    private suspend fun calculateMaxRefundAmount() {
        val transactionDataToRefund: TransactionData = originalTransactionData ?: return
        var refundedAmountCalculated: Amount? = null
        transactionDataToRefund.transaction.refundTransactionIds?.forEach {
            if (it != getOriginalTransactionId()) {
                getTransactionDataUseCase(
                    id = it,
                )?.transaction?.amount?.let { prevRefundedTransactionAmount ->
                    refundedAmountCalculated = refundedAmountCalculated?.run {
                        this.plus(prevRefundedTransactionAmount)
                    } ?: prevRefundedTransactionAmount
                }
            }
        }
        transactionDataToRefund.transaction.amount.let { originalTransactionAmount ->
            maxRefundAmount = if (refundedAmountCalculated.isNotNull()) {
                originalTransactionAmount.minus((refundedAmountCalculated ?: Amount()))
            } else {
                originalTransactionAmount
            }
        }
    }

    private fun updateAddTransactionScreenUiStateWithOriginalTransactionData(
        originalTransaction: Transaction,
        transactionTypesForNewTransaction: List<TransactionType>,
        transactionForValues: List<TransactionFor>,
        maxRefundAmount: Amount?,
    ) {
        val initialAddTransactionScreenUiStateData = if (isAddingRefundTransaction()) {
            AddTransactionScreenUiStateData(
                selectedTransactionTypeIndex = transactionTypesForNewTransaction.indexOf(
                    element = TransactionType.REFUND,
                ),
                amount = uiState.value.amount.copy(
                    text = maxRefundAmount?.value.orZero().toString(),
                    selection = TextRange(maxRefundAmount?.value.orZero().toString().length),
                ),
                title = uiState.value.title.copy(
                    text = TransactionType.REFUND.title,
                ),
                description = uiState.value.description.copy(
                    text = originalTransaction.description,
                ),
                category = originalTransactionData?.category,
                selectedTransactionForIndex = transactionForValues.indexOf(
                    element = transactionForValues.firstOrNull {
                        it.id == originalTransaction.transactionForId
                    },
                ),
                accountFrom = originalTransactionData?.accountTo,
                accountTo = originalTransactionData?.accountFrom,
                transactionDate = dateTimeUtil.getCurrentLocalDate(),
                transactionTime = dateTimeUtil.getCurrentLocalTime(),
            )
        } else {
            AddTransactionScreenUiStateData(
                selectedTransactionTypeIndex = transactionTypesForNewTransaction.indexOf(
                    element = originalTransaction.transactionType,
                ),
                amount = uiState.value.amount.copy(
                    text = abs(originalTransaction.amount.value).toString(),
                    selection = TextRange(abs(originalTransaction.amount.value).toString().length),
                ),
                title = uiState.value.title.copy(
                    text = originalTransaction.title,
                ),
                description = uiState.value.description.copy(
                    text = originalTransaction.description,
                ),
                category = originalTransactionData?.category,
                selectedTransactionForIndex = transactionForValues.indexOf(
                    element = transactionForValues.firstOrNull {
                        it.id == originalTransaction.transactionForId
                    },
                ),
                accountFrom = originalTransactionData?.accountFrom,
                accountTo = originalTransactionData?.accountTo,
                transactionDate = dateTimeUtil.getLocalDate(
                    timestamp = originalTransaction.transactionTimestamp,
                ),
                transactionTime = dateTimeUtil.getLocalTime(
                    timestamp = originalTransaction.transactionTimestamp,
                ),
            )
        }
        updateAddTransactionScreenUiState(
            updatedAddTransactionScreenUiStateData = initialAddTransactionScreenUiStateData,
        )

        setInitialSelectedTransactionType()
    }

    private fun setDefaultCategory() {
        defaultExpenseCategory = getCategory(
            categoryId = defaultDataIdFromDataStore?.expenseCategory,
        ) ?: categories.firstOrNull { category ->
            isDefaultExpenseCategory(
                category = category.title,
            )
        }
        defaultIncomeCategory = getCategory(
            categoryId = defaultDataIdFromDataStore?.incomeCategory,
        ) ?: categories.firstOrNull { category ->
            isDefaultIncomeCategory(
                category = category.title,
            )
        }
        defaultInvestmentCategory = getCategory(
            categoryId = defaultDataIdFromDataStore?.investmentCategory,
        ) ?: categories.firstOrNull { category ->
            isDefaultInvestmentCategory(
                category = category.title,
            )
        }
        updateCategory(
            updatedCategory = defaultExpenseCategory,
        )
    }

    private fun setDefaultAccount() {
        defaultAccount = getAccount(
            accountId = defaultDataIdFromDataStore?.account,
        ) ?: accounts.firstOrNull { account ->
            isDefaultAccount(
                account = account.name,
            )
        }
        updateAccountFrom(
            updatedAccountFrom = defaultAccount,
        )
        updateAccountTo(
            updatedAccountTo = defaultAccount,
        )
    }

    private fun setInitialSelectedTransactionType() {
        selectedTransactionType.value = if (isAddingRefundTransaction()) {
            TransactionType.REFUND
        } else {
            uiState.value.selectedTransactionTypeIndex?.let {
                validTransactionTypesForNewTransaction.getOrNull(
                    index = uiState.value.selectedTransactionTypeIndex.orZero(),
                )
            }
        }
    }

    private fun getCategory(
        categoryId: Int?,
    ): Category? {
        return categories.find { category ->
            category.id == categoryId
        }
    }

    private fun getAccount(
        accountId: Int?,
    ): Account? {
        return accounts.find { account ->
            account.id == accountId
        }
    }

    private fun isAddingRefundTransaction(): Boolean {
        return getOriginalTransactionId().isNotNull()
    }

    private fun getOriginalTransactionId(): Int? {
        return screenArgs.originalTransactionId
    }
}
