package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.capitalizeWords
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combine
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.filterDigits
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNullOrBlank
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isTrue
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orEmpty
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.orZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toIntOrZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toLongOrZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.common.stringdecoder.StringDecoder
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.Quadruple
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultBooleanStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultListStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.account.GetAllAccountsCountUseCase
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
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.screen.AddOrEditTransactionScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.navigation.AddOrEditTransactionScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
public class AddOrEditTransactionScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
    private val dateTimeUtil: DateTimeUtil,
    private val dispatcherProvider: DispatcherProvider,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getAllAccountsCountUseCase: GetAllAccountsCountUseCase,
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
    private val screenArgs = AddOrEditTransactionScreenArgs(
        savedStateHandle = savedStateHandle,
        stringDecoder = stringDecoder,
    )

    // Original transaction data
    private var originalTransactionData: MutableStateFlow<TransactionData?> = MutableStateFlow(
        value = null,
    )
    private var maxRefundAmount: MutableStateFlow<Amount?> = MutableStateFlow(
        value = null,
    )

    // region Default data
    // Default data from data store
    private var defaultAccount: Account? = null
    private var expenseDefaultCategory: Category? = null
    private var incomeDefaultCategory: Category? = null
    private var investmentDefaultCategory: Category? = null

    // Default data from data store
    private var defaultDataIdFromDataStore: DefaultDataId? = null
    // endregion

    // region Data source
    private var transactionTypesForNewTransaction: MutableStateFlow<ImmutableList<TransactionType>?> =
        MutableStateFlow(
            value = null,
        )
    private var categories: MutableStateFlow<ImmutableList<Category>?> = MutableStateFlow(
        value = null,
    )
    private val titleSuggestions: MutableStateFlow<ImmutableList<String>?> = MutableStateFlow(
        value = null,
    )
    private var transactionForValues: MutableStateFlow<ImmutableList<TransactionFor>?> =
        MutableStateFlow(
            value = null,
        )
    private var accounts: MutableStateFlow<ImmutableList<Account>?> = MutableStateFlow(
        value = null,
    )
    // endregion

    private val uiState: MutableStateFlow<AddOrEditTransactionScreenUiStateData> = MutableStateFlow(
        value = AddOrEditTransactionScreenUiStateData(
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
    private val uiVisibilityState: MutableStateFlow<AddOrEditTransactionScreenUiVisibilityState> =
        MutableStateFlow(
            value = AddOrEditTransactionScreenUiVisibilityState.Expense,
        )

    // Dependant data
    private val selectedTransactionType: MutableStateFlow<TransactionType?> = MutableStateFlow(
        value = null,
    )

    private val filteredCategories: StateFlow<ImmutableList<Category>> = combine(
        flow = categories,
        flow2 = selectedTransactionType,
    ) { categories, selectedTransactionType ->
        categories?.filter { category ->
            category.transactionType == selectedTransactionType
        }?.toImmutableList().orEmpty()
    }.defaultListStateIn(
        scope = viewModelScope,
    )

    private val selectedCategoryId: StateFlow<Int?> = uiState.map {
        it.category?.id
    }.defaultObjectStateIn(
        scope = viewModelScope,
    )

    private val isCtaButtonEnabled: StateFlow<Boolean> = combine(
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
                val maxRefundAmountValue = maxRefundAmount.value?.value.orZero()
                if (uiState.amountErrorText.isNull() &&
                    (uiState.amount.text.toLongOrZero() > maxRefundAmountValue)
                ) {
                    updateAddOrEditTransactionScreenUiState(
                        updatedAddOrEditTransactionScreenUiStateData = uiState.copy(
                            amountErrorText = maxRefundAmount.value?.run {
                                this.toString()
                            },
                        )
                    )
                    false
                } else if (uiState.amountErrorText.isNotNull() &&
                    (uiState.amount.text.toLongOrZero() <= maxRefundAmountValue)
                ) {
                    updateAddOrEditTransactionScreenUiState(
                        updatedAddOrEditTransactionScreenUiStateData = uiState.copy(
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
    }.defaultBooleanStateIn(
        scope = viewModelScope,
    )

    public val screenUIData: StateFlow<MyResult<AddOrEditTransactionScreenUIData>?> = combine(
        uiState,
        uiVisibilityState,
        isCtaButtonEnabled,
        filteredCategories,
        accounts,
        titleSuggestions,
        transactionTypesForNewTransaction,
        transactionForValues,
        selectedTransactionType,
    ) {
            uiState,
            uiVisibilityState,
            isCtaButtonEnabled,
            filteredCategories,
            accounts,
            titleSuggestions,
            transactionTypesForNewTransaction,
            transactionForValues,
            selectedTransactionType,
        ->

        if (
            uiState.isNull() ||
            uiVisibilityState.isNull() ||
            isCtaButtonEnabled.isNull() ||
            filteredCategories.isNull() ||
            accounts.isNull() ||
            transactionTypesForNewTransaction.isNull() ||
            transactionForValues.isNull() ||
            selectedTransactionType.isNull()
        ) {
            MyResult.Loading
        } else {
            MyResult.Success(
                data = AddOrEditTransactionScreenUIData(
                    uiState = uiState,
                    uiVisibilityState = uiVisibilityState,
                    isCtaButtonEnabled = isCtaButtonEnabled,
                    filteredCategories = filteredCategories,
                    accounts = accounts,
                    titleSuggestions = titleSuggestions.orEmpty(),
                    transactionTypesForNewTransaction = transactionTypesForNewTransaction,
                    transactionForValues = transactionForValues,
                    currentLocalDate = dateTimeUtil.getCurrentLocalDate(),
                    selectedTransactionType = selectedTransactionType,
                ),
            )
        }
    }.defaultObjectStateIn(
        scope = viewModelScope,
    )

    public fun initViewModel() {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            fetchData()
        }
    }

    public fun insertTransaction() {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
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
                        originalTransactionData.value?.category?.id
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
                        transactionForValues.value?.get(uiStateValue.selectedTransactionForIndex)?.id
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
                val originalTransactionId =
                    if (screenArgs.isEdit.isFalse() && screenArgs.originalTransactionId.isNotNull() ||
                        screenArgs.isEdit.isTrue() && originalTransactionData.value?.transaction?.transactionType == TransactionType.REFUND
                    ) {
                        screenArgs.originalTransactionId
                    } else {
                        null
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
                        originalTransactionId = originalTransactionId,
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
                originalTransactionData.value?.transaction?.let { originalTransaction ->
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

    public fun updateTransaction() {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            val selectedTransactionTypeValue = selectedTransactionType.value
            val uiStateValue = uiState.value
            selectedTransactionTypeValue?.let {
                val amount = Amount(
                    value = uiStateValue.amount.text.toLongOrZero(),
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
                        originalTransactionData.value?.category?.id
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
                        transactionForValues.value?.get(uiStateValue.selectedTransactionForIndex)?.id
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

                originalTransactionData.value?.transaction?.let { transaction ->
                    val transactionTimestamp = LocalDateTime
                        .of(uiStateValue.transactionDate, uiStateValue.transactionTime)
                        .toEpochMilli()
                    updateTransactionUseCase(
                        originalTransaction = transaction,
                        updatedTransaction = transaction.copy(
                            amount = amount,
                            categoryId = categoryId,
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

                    // region transaction account updates
                    val accountBalanceAmountChangeMap = hashMapOf<Int, Long>()
                    originalTransactionData.value?.accountFrom?.let { transactionAccountFrom ->
                        accountBalanceAmountChangeMap[transactionAccountFrom.id] =
                            accountBalanceAmountChangeMap[transactionAccountFrom.id].orZero() + transaction.amount.value
                    }
                    uiStateValue.accountFrom?.let { accountFrom ->
                        accountBalanceAmountChangeMap[accountFrom.id] =
                            accountBalanceAmountChangeMap[accountFrom.id].orZero() - uiState.value.amount.text.toLongOrZero()
                    }
                    originalTransactionData.value?.accountTo?.let { transactionAccountTo ->
                        accountBalanceAmountChangeMap[transactionAccountTo.id] =
                            accountBalanceAmountChangeMap[transactionAccountTo.id].orZero() - transaction.amount.value
                    }
                    uiStateValue.accountTo?.let { accountTo ->
                        accountBalanceAmountChangeMap[accountTo.id] =
                            accountBalanceAmountChangeMap[accountTo.id].orZero() + uiState.value.amount.text.toLongOrZero()
                    }
                    updateAccountBalanceAmountUseCase(
                        accountsBalanceAmountChange = accountBalanceAmountChangeMap.toList(),
                    )
                    // endregion

                }
            }
            navigator.navigateUp()
        }
    }

    // region UI changes
    public fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    ) {
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiStateData = uiState.value.copy(
                selectedTransactionTypeIndex = updatedSelectedTransactionTypeIndex,
            ),
        )
        selectedTransactionType.value = transactionTypesForNewTransaction.value?.getOrNull(
            index = updatedSelectedTransactionTypeIndex,
        )
    }

    public fun updateAmount(
        updatedAmount: TextFieldValue,
    ) {
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiStateData = uiState.value.copy(
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
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiStateData = uiState.value.copy(
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
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiStateData = uiState.value.copy(
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
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiStateData = uiState.value.copy(
                category = updatedCategory,
            ),
        )
    }

    public fun updateSelectedTransactionForIndex(
        updatedSelectedTransactionForIndex: Int,
    ) {
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiStateData = uiState.value.copy(
                selectedTransactionForIndex = updatedSelectedTransactionForIndex,
            ),
        )
    }

    public fun updateAccountFrom(
        updatedAccountFrom: Account?,
    ) {
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiStateData = uiState.value.copy(
                accountFrom = updatedAccountFrom,
            ),
        )
    }

    public fun updateAccountTo(
        updatedAccountTo: Account?,
    ) {
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiStateData = uiState.value.copy(
                accountTo = updatedAccountTo,
            ),
        )
    }

    public fun updateTransactionDate(
        updatedTransactionDate: LocalDate,
    ) {
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiStateData = uiState.value.copy(
                transactionDate = updatedTransactionDate,
            ),
        )
    }

    public fun updateTransactionTime(
        updatedTransactionTime: LocalTime,
    ) {
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiStateData = uiState.value.copy(
                transactionTime = updatedTransactionTime,
            ),
        )
    }

    public fun updateAddOrEditTransactionScreenUiState(
        updatedAddOrEditTransactionScreenUiStateData: AddOrEditTransactionScreenUiStateData,
    ) {
        uiState.update {
            updatedAddOrEditTransactionScreenUiStateData
        }
    }

    private fun updateAddOrEditTransactionScreenUiVisibilityState(
        updatedAddOrEditTransactionScreenUiVisibilityState: AddOrEditTransactionScreenUiVisibilityState,
    ) {
        uiVisibilityState.value = updatedAddOrEditTransactionScreenUiVisibilityState
    }
    // endregion

    private fun fetchData() {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            // Initial data setup

            // Default data from data store
            awaitAll(
                async {
                    defaultDataIdFromDataStore = myPreferencesRepository.getDefaultDataId().first()
                },
                async {
                    categories.value = getAllCategoriesUseCase().toImmutableList()
                },
                async {
                    accounts.value = getAllAccountsUseCase()
                        .sortedWith(
                            comparator = compareBy<Account> {
                                it.type.sortOrder
                            }.thenByDescending {
                                it.balanceAmount.value
                            }
                        )
                        .toImmutableList()
                },
                async {
                    transactionForValues.value =
                        getAllTransactionForValuesUseCase().toImmutableList()
                },
            )
            getTransactionTypesForNewTransaction()
            getOriginalTransactionData(
                coroutineScope = this,
            )
            setDefaultCategory()
            setDefaultAccount()
            setInitialSelectedTransactionType()

            // Observables
            observeSelectedTransactionType(
                coroutineScope = this,
            )
            observeSelectedCategory(
                coroutineScope = this,
            )
        }
    }

    private fun observeSelectedTransactionType(
        coroutineScope: CoroutineScope,
    ) {
        coroutineScope.launch {
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
        val uiVisibilityState: AddOrEditTransactionScreenUiVisibilityState? =
            when (transactionType) {
                TransactionType.INCOME -> {
                    AddOrEditTransactionScreenUiVisibilityState.Income
                }

                TransactionType.EXPENSE -> {
                    AddOrEditTransactionScreenUiVisibilityState.Expense
                }

                TransactionType.TRANSFER -> {
                    AddOrEditTransactionScreenUiVisibilityState.Transfer
                }

                TransactionType.ADJUSTMENT -> {
                    null
                }

                TransactionType.INVESTMENT -> {
                    AddOrEditTransactionScreenUiVisibilityState.Investment
                }

                TransactionType.REFUND -> {
                    AddOrEditTransactionScreenUiVisibilityState.Refund
                }
            }
        uiVisibilityState?.let {
            updateAddOrEditTransactionScreenUiVisibilityState(
                updatedAddOrEditTransactionScreenUiVisibilityState = uiVisibilityState,
            )
        }

        when (transactionType) {
            TransactionType.INCOME -> {
                val updatedCategory =
                    if (transactionType == originalTransactionData.value?.transaction?.transactionType) {
                        originalTransactionData.value?.category ?: incomeDefaultCategory
                    } else {
                        incomeDefaultCategory
                    }
                updateCategory(
                    updatedCategory = updatedCategory,
                )

                updateAccountFrom(
                    updatedAccountFrom = null,
                )
                updateAccountTo(
                    updatedAccountTo = originalTransactionData.value?.accountTo
                        ?: defaultAccount,
                )
            }

            TransactionType.EXPENSE -> {
                val updatedCategory =
                    if (transactionType == originalTransactionData.value?.transaction?.transactionType) {
                        originalTransactionData.value?.category
                            ?: expenseDefaultCategory
                    } else {
                        expenseDefaultCategory
                    }
                updateCategory(
                    updatedCategory = updatedCategory,
                )

                updateAccountFrom(
                    updatedAccountFrom = originalTransactionData.value?.accountFrom
                        ?: defaultAccount,
                )
                updateAccountTo(
                    updatedAccountTo = null,
                )
            }

            TransactionType.TRANSFER -> {
                updateAccountFrom(
                    updatedAccountFrom = originalTransactionData.value?.accountFrom
                        ?: defaultAccount,
                )
                updateAccountTo(
                    updatedAccountTo = originalTransactionData.value?.accountTo
                        ?: defaultAccount,
                )
            }

            TransactionType.ADJUSTMENT -> {}

            TransactionType.INVESTMENT -> {
                val updatedCategory =
                    if (transactionType == originalTransactionData.value?.transaction?.transactionType) {
                        originalTransactionData.value?.category
                            ?: investmentDefaultCategory
                    } else {
                        investmentDefaultCategory
                    }
                updateCategory(
                    updatedCategory = updatedCategory,
                )

                updateAccountFrom(
                    updatedAccountFrom = originalTransactionData.value?.accountFrom
                        ?: defaultAccount,
                )
                updateAccountTo(
                    updatedAccountTo = null,
                )
            }

            TransactionType.REFUND -> {}
        }
    }

    private fun observeSelectedCategory(
        coroutineScope: CoroutineScope,
    ) {
        coroutineScope.launch {
            combine(
                selectedCategoryId, uiState,
            ) { selectedCategoryId, uiState ->
                Pair(selectedCategoryId, uiState)
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

    private suspend fun getTransactionTypesForNewTransaction() {
        val accountCount = getAllAccountsCountUseCase()
        val excludedTransactionTypes = mutableSetOf(
            TransactionType.ADJUSTMENT,
            TransactionType.REFUND
        )

        // Cannot create transfer with single account
        if (accountCount <= 1) {
            excludedTransactionTypes.add(TransactionType.TRANSFER)
        }
        val transactionTypesForNewTransaction =
            (TransactionType.entries.toSet() - excludedTransactionTypes).toList()
        this.transactionTypesForNewTransaction.value =
            transactionTypesForNewTransaction.toImmutableList()

        if (screenArgs.isEdit.isFalse() &&
            screenArgs.originalTransactionId.isNull()
        ) {
            updateSelectedTransactionTypeIndex(
                updatedSelectedTransactionTypeIndex = transactionTypesForNewTransaction.indexOf(
                    element = TransactionType.EXPENSE,
                ),
            )
        }
    }

    private suspend fun getOriginalTransactionData(
        coroutineScope: CoroutineScope,
    ) {
        screenArgs.originalTransactionId?.let { id ->
            coroutineScope.launch(
                context = dispatcherProvider.io,
            ) {
                launch {
                    getTransactionDataUseCase(
                        id = id,
                    )?.let {
                        originalTransactionData.value = it
                        calculateMaxRefundAmount()
                    }
                }
                observeOriginalTransaction()
            }
        }
    }

    private suspend fun observeOriginalTransaction() {
        combine(
            flow = originalTransactionData,
            flow2 = transactionTypesForNewTransaction,
            flow3 = transactionForValues,
            flow4 = maxRefundAmount,
        ) {
                originalTransactionData,
                transactionTypesForNewTransaction,
                transactionForValues,
                maxRefundAmount,
            ->
            Quadruple(
                first = originalTransactionData,
                second = transactionTypesForNewTransaction,
                third = transactionForValues,
                fourth = maxRefundAmount,
            )
        }.collectLatest {
                (
                    originalTransactionData,
                    transactionTypesForNewTransaction,
                    transactionForValues,
                    maxRefundAmount,
                ),
            ->
            originalTransactionData?.transaction?.let { originalTransaction ->
                updateAddOrEditTransactionScreenUiStateWithOriginalTransactionData(
                    originalTransaction = originalTransaction,
                    transactionTypesForNewTransaction = transactionTypesForNewTransaction.orEmpty(),
                    transactionForValues = transactionForValues.orEmpty(),
                    maxRefundAmount = maxRefundAmount,
                )
            }
        }
    }

    private fun updateAddOrEditTransactionScreenUiStateWithOriginalTransactionData(
        originalTransaction: Transaction,
        transactionTypesForNewTransaction: List<TransactionType>,
        transactionForValues: List<TransactionFor>,
        maxRefundAmount: Amount?,
    ) {
        val isAddingRefund = screenArgs.isEdit.isFalse() &&
                screenArgs.originalTransactionId.isNotNull()
        val initialAddOrEditTransactionScreenUiStateData = if (isAddingRefund) {
            AddOrEditTransactionScreenUiStateData(
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
                category = originalTransactionData.value?.category,
                selectedTransactionForIndex = transactionForValues.indexOf(
                    element = transactionForValues.firstOrNull {
                        it.id == originalTransaction.transactionForId
                    },
                ),
                accountFrom = originalTransactionData.value?.accountTo,
                accountTo = originalTransactionData.value?.accountFrom,
                transactionDate = dateTimeUtil.getCurrentLocalDate(),
                transactionTime = dateTimeUtil.getCurrentLocalTime(),
            )
        } else {
            AddOrEditTransactionScreenUiStateData(
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
                category = originalTransactionData.value?.category,
                selectedTransactionForIndex = transactionForValues.indexOf(
                    element = transactionForValues.firstOrNull {
                        it.id == originalTransaction.transactionForId
                    },
                ),
                accountFrom = originalTransactionData.value?.accountFrom,
                accountTo = originalTransactionData.value?.accountTo,
                transactionDate = dateTimeUtil.getLocalDate(
                    timestamp = originalTransaction.transactionTimestamp,
                ),
                transactionTime = dateTimeUtil.getLocalTime(
                    timestamp = originalTransaction.transactionTimestamp,
                ),
            )
        }
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiStateData = initialAddOrEditTransactionScreenUiStateData,
        )

        // TODO(Abhi): This is a better race condition, but still not reactive
        setInitialSelectedTransactionType()
    }

    private suspend fun calculateMaxRefundAmount() {
        val transactionId = screenArgs.originalTransactionId ?: return
        val transactionData = getTransactionDataUseCase(
            id = transactionId,
        )
        if (!((screenArgs.isEdit.isTrue() &&
                    transactionData?.transaction?.transactionType == TransactionType.REFUND) ||
                    screenArgs.isEdit.isFalse())
        ) {
            return
        }

        var transactionDataToRefund: TransactionData? = null
        if (screenArgs.isEdit.isTrue()) {
            transactionData?.transaction?.originalTransactionId?.let {
                transactionDataToRefund = getTransactionDataUseCase(
                    id = it,
                )
            }
        } else {
            transactionDataToRefund = transactionData
        }
        if (transactionDataToRefund.isNull()) {
            return
        }

        var refundedAmountCalculated: Amount? = null
        transactionDataToRefund?.transaction?.refundTransactionIds?.forEach {
            if (it != screenArgs.originalTransactionId) {
                getTransactionDataUseCase(
                    id = it,
                )?.transaction?.amount?.let { prevRefundedTransactionAmount ->
                    refundedAmountCalculated = refundedAmountCalculated?.run {
                        this.plus(prevRefundedTransactionAmount)
                    } ?: prevRefundedTransactionAmount
                }
            }
        }
        transactionDataToRefund?.transaction?.amount?.let { originalTransactionAmount ->
            maxRefundAmount.value = if (refundedAmountCalculated.isNotNull()) {
                originalTransactionAmount.minus((refundedAmountCalculated ?: Amount()))
            } else {
                originalTransactionAmount
            }
        }
    }

    private fun setDefaultCategory() {
        expenseDefaultCategory = getCategory(
            categoryId = defaultDataIdFromDataStore?.expenseCategory,
        ) ?: categories.value?.firstOrNull { category ->
            isDefaultExpenseCategory(
                category = category.title,
            )
        }
        incomeDefaultCategory = getCategory(
            categoryId = defaultDataIdFromDataStore?.incomeCategory,
        ) ?: categories.value?.firstOrNull { category ->
            isDefaultIncomeCategory(
                category = category.title,
            )
        }
        investmentDefaultCategory = getCategory(
            categoryId = defaultDataIdFromDataStore?.investmentCategory,
        ) ?: categories.value?.firstOrNull { category ->
            isDefaultInvestmentCategory(
                category = category.title,
            )
        }
        if (screenArgs.isEdit != true) {
            updateCategory(
                updatedCategory = expenseDefaultCategory,
            )
        }
    }

    private fun setDefaultAccount() {
        defaultAccount = getAccount(
            accountId = defaultDataIdFromDataStore?.account,
        ) ?: accounts.value?.firstOrNull { account ->
            isDefaultAccount(
                account = account.name,
            )
        }
        if (screenArgs.isEdit != true) {
            updateAccountFrom(
                updatedAccountFrom = defaultAccount,
            )
            updateAccountTo(
                updatedAccountTo = defaultAccount,
            )
        }
    }

    private fun setInitialSelectedTransactionType() {
        selectedTransactionType.value = if (screenArgs.isEdit.isTrue()) {
            if (originalTransactionData.value?.transaction?.transactionType == TransactionType.REFUND) {
                TransactionType.REFUND
            } else {
                uiState.value.selectedTransactionTypeIndex?.let {
                    transactionTypesForNewTransaction.value?.getOrNull(
                        index = uiState.value.selectedTransactionTypeIndex.orZero(),
                    )
                }
            }
        } else {
            if (screenArgs.originalTransactionId.isNotNull()) {
                TransactionType.REFUND
            } else {
                uiState.value.selectedTransactionTypeIndex?.let {
                    transactionTypesForNewTransaction.value?.getOrNull(
                        index = uiState.value.selectedTransactionTypeIndex.orZero(),
                    )
                }
            }
        }
    }

    private fun getCategory(
        categoryId: Int?,
    ): Category? {
        return categories.value?.find { category ->
            category.id == categoryId
        }
    }

    private fun getAccount(
        accountId: Int?,
    ): Account? {
        return accounts.value?.find { account ->
            account.id == accountId
        }
    }
}
