package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.datetime.DateTimeUtil
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.capitalizeWords
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.filterDigits
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isFalse
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNullOrBlank
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isTrue
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
import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase.GetAllCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.preferences.repository.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAllAccountsCountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.GetAllAccountsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.source.usecase.UpdateAccountsBalanceAmountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetTitleSuggestionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.GetTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.InsertTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.UpdateTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transactionfor.usecase.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.MyLogger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.model.sortOrder
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultAccount
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultInvestmentCategory
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.screen.AddOrEditTransactionScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.navigation.AddOrEditTransactionScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
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
internal class AddOrEditTransactionScreenViewModelImpl @Inject constructor(
    savedStateHandle: SavedStateHandle,
    stringDecoder: StringDecoder,
    override val myLogger: MyLogger,
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
    private val navigationManager: NavigationManager,
    private val updateAccountsBalanceAmountUseCase: UpdateAccountsBalanceAmountUseCase,
    private val updateTransactionUseCase: UpdateTransactionUseCase,
) : AddOrEditTransactionScreenViewModel, ViewModel() {
    private val addOrEditTransactionScreenArgs: AddOrEditTransactionScreenArgs =
        AddOrEditTransactionScreenArgs(
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
    private var defaultAccount: Source? = null
    private var expenseDefaultCategory: Category? = null
    private var incomeDefaultCategory: Category? = null
    private var investmentDefaultCategory: Category? = null

    // Default data from data store
    private var defaultDataIdFromDataStore: DefaultDataId? = null
    // endregion

    // region Data source
    private var transactionTypesForNewTransaction: MutableStateFlow<List<TransactionType>?> =
        MutableStateFlow(
            value = null,
        )
    private var categories: MutableStateFlow<List<Category>?> = MutableStateFlow(
        value = null,
    )
    private val titleSuggestions: MutableStateFlow<List<String>?> = MutableStateFlow(
        value = null,
    )
    private var transactionForValues: MutableStateFlow<List<TransactionFor>?> = MutableStateFlow(
        value = null,
    )
    private var sources: MutableStateFlow<List<Source>?> = MutableStateFlow(
        value = null,
    )
    // endregion

    private val uiState: MutableStateFlow<AddOrEditTransactionScreenUiStateData> = MutableStateFlow(
        value = AddOrEditTransactionScreenUiStateData(
            selectedTransactionTypeIndex = null,
            amount = TextFieldValue(
                text = "",
            ),
            title = TextFieldValue(
                text = "",
            ),
            description = TextFieldValue(
                text = "",
            ),
            category = null,
            selectedTransactionForIndex = 0,
            sourceFrom = null,
            sourceTo = null,
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

    private val filteredCategories: StateFlow<List<Category>> = combine(
        flow = categories,
        flow2 = selectedTransactionType,
    ) { categories, selectedTransactionType ->
        categories?.filter { category ->
            category.transactionType == selectedTransactionType
        }.orEmpty()
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
                        uiState.sourceFrom?.id != uiState.sourceTo?.id &&
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

    override val screenUIData: StateFlow<MyResult<AddOrEditTransactionScreenUIData>?> = combine(
        uiState,
        uiVisibilityState,
        isCtaButtonEnabled,
        filteredCategories,
        sources,
        titleSuggestions,
        transactionTypesForNewTransaction,
        transactionForValues,
        selectedTransactionType,
    ) { flows ->
        val uiState = flows[0] as? AddOrEditTransactionScreenUiStateData
        val uiVisibilityState = flows[1] as? AddOrEditTransactionScreenUiVisibilityState
        val isCtaButtonEnabled = flows[2] as? Boolean
        val filteredCategories = flows[3] as? List<Category>
        val sources = flows[4] as? List<Source>
        val titleSuggestions = flows[5] as? List<String>
        val transactionTypesForNewTransaction = flows[6] as? List<TransactionType>
        val transactionForValues = flows[7] as? List<TransactionFor>
        val selectedTransactionType = flows[8] as? TransactionType

        if (
            uiState.isNull() ||
            uiVisibilityState.isNull() ||
            isCtaButtonEnabled.isNull() ||
            filteredCategories.isNull() ||
            sources.isNull() ||
            titleSuggestions.isNull() ||
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
                    sources = sources,
                    titleSuggestions = titleSuggestions,
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

    init {
        initViewModel()
    }

    private fun initViewModel() {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            fetchData()
        }
    }

    override fun insertTransaction() {
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
                val sourceFromId = when (selectedTransactionTypeValue) {
                    TransactionType.INCOME -> {
                        null
                    }

                    TransactionType.EXPENSE -> {
                        uiStateValue.sourceFrom?.id
                    }

                    TransactionType.TRANSFER -> {
                        uiStateValue.sourceFrom?.id
                    }

                    TransactionType.ADJUSTMENT -> {
                        null
                    }

                    TransactionType.INVESTMENT -> {
                        uiStateValue.sourceFrom?.id
                    }

                    TransactionType.REFUND -> {
                        null
                    }
                }
                val sourceToId = when (selectedTransactionTypeValue) {
                    TransactionType.INCOME -> {
                        uiStateValue.sourceTo?.id
                    }

                    TransactionType.EXPENSE -> {
                        null
                    }

                    TransactionType.TRANSFER -> {
                        uiStateValue.sourceTo?.id
                    }

                    TransactionType.ADJUSTMENT -> {
                        null
                    }

                    TransactionType.INVESTMENT -> {
                        null
                    }

                    TransactionType.REFUND -> {
                        uiStateValue.sourceTo?.id
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
                    if (addOrEditTransactionScreenArgs.isEdit.isFalse() && addOrEditTransactionScreenArgs.originalTransactionId.isNotNull() ||
                        addOrEditTransactionScreenArgs.isEdit.isTrue() && originalTransactionData.value?.transaction?.transactionType == TransactionType.REFUND
                    ) {
                        addOrEditTransactionScreenArgs.originalTransactionId
                    } else {
                        null
                    }

                val transactionTimestamp = LocalDateTime
                    .of(uiStateValue.transactionDate, uiStateValue.transactionTime)
                    .toEpochMilli()
                val id = insertTransactionUseCase(
                    amountValue = amountValue,
                    accountFrom = if (sourceFromId.isNotNull()) {
                        uiStateValue.sourceFrom
                    } else {
                        null
                    },
                    accountTo = if (sourceToId.isNotNull()) {
                        uiStateValue.sourceTo
                    } else {
                        null
                    },
                    transaction = Transaction(
                        amount = amount,
                        categoryId = categoryId,
                        originalTransactionId = originalTransactionId,
                        sourceFromId = sourceFromId,
                        sourceToId = sourceToId,
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
                navigationManager.navigate(
                    navigationCommand = MyNavigationDirections.NavigateUp
                )
            }
        }
    }

    override fun updateTransaction() {
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
                val sourceFromId = when (selectedTransactionTypeValue) {
                    TransactionType.INCOME -> {
                        null
                    }

                    TransactionType.EXPENSE -> {
                        uiStateValue.sourceFrom?.id
                    }

                    TransactionType.TRANSFER -> {
                        uiStateValue.sourceFrom?.id
                    }

                    TransactionType.ADJUSTMENT -> {
                        null
                    }

                    TransactionType.INVESTMENT -> {
                        uiStateValue.sourceFrom?.id
                    }

                    TransactionType.REFUND -> {
                        null
                    }
                }
                val sourceToId = when (selectedTransactionTypeValue) {
                    TransactionType.INCOME -> {
                        uiStateValue.sourceTo?.id
                    }

                    TransactionType.EXPENSE -> {
                        null
                    }

                    TransactionType.TRANSFER -> {
                        uiStateValue.sourceTo?.id
                    }

                    TransactionType.ADJUSTMENT -> {
                        null
                    }

                    TransactionType.INVESTMENT -> {
                        null
                    }

                    TransactionType.REFUND -> {
                        uiStateValue.sourceTo?.id
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
                            sourceFromId = sourceFromId,
                            sourceToId = sourceToId,
                            description = uiStateValue.description.text,
                            title = title,
                            creationTimestamp = dateTimeUtil.getCurrentTimeMillis(),
                            transactionTimestamp = transactionTimestamp,
                            transactionForId = transactionForId,
                            transactionType = selectedTransactionTypeValue,
                        ),
                    )

                    // region transaction source updates
                    val sourceBalanceAmountChangeMap = hashMapOf<Int, Long>()
                    originalTransactionData.value?.sourceFrom?.let { transactionSourceFrom ->
                        sourceBalanceAmountChangeMap[transactionSourceFrom.id] =
                            sourceBalanceAmountChangeMap[transactionSourceFrom.id].orZero() + transaction.amount.value
                    }
                    uiStateValue.sourceFrom?.let { sourceFrom ->
                        sourceBalanceAmountChangeMap[sourceFrom.id] =
                            sourceBalanceAmountChangeMap[sourceFrom.id].orZero() - uiState.value.amount.text.toLongOrZero()
                    }
                    originalTransactionData.value?.sourceTo?.let { transactionSourceTo ->
                        sourceBalanceAmountChangeMap[transactionSourceTo.id] =
                            sourceBalanceAmountChangeMap[transactionSourceTo.id].orZero() - transaction.amount.value
                    }
                    uiStateValue.sourceTo?.let { sourceTo ->
                        sourceBalanceAmountChangeMap[sourceTo.id] =
                            sourceBalanceAmountChangeMap[sourceTo.id].orZero() + uiState.value.amount.text.toLongOrZero()
                    }
                    updateAccountsBalanceAmountUseCase(
                        sourcesBalanceAmountChange = sourceBalanceAmountChangeMap.toList(),
                    )
                    // endregion

                }
            }
            navigationManager.navigate(
                navigationCommand = MyNavigationDirections.NavigateUp
            )
        }
    }

    // region UI changes
    override fun updateSelectedTransactionTypeIndex(
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

    override fun updateAmount(
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

    override fun clearAmount() {
        updateAmount(
            updatedAmount = uiState.value.amount.copy(
                text = "",
            ),
        )
    }

    override fun navigateUp() {
        navigationManager.navigate(
            MyNavigationDirections.NavigateUp
        )
    }

    override fun updateTitle(
        updatedTitle: TextFieldValue,
    ) {
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiStateData = uiState.value.copy(
                title = updatedTitle,
            ),
        )
    }

    override fun clearTitle() {
        updateTitle(
            updatedTitle = uiState.value.title.copy(
                text = "",
            ),
        )
    }

    override fun updateDescription(
        updatedDescription: TextFieldValue,
    ) {
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiStateData = uiState.value.copy(
                description = updatedDescription,
            ),
        )
    }

    override fun clearDescription() {
        updateDescription(
            updatedDescription = uiState.value.description.copy(
                text = "",
            ),
        )
    }

    override fun updateCategory(
        updatedCategory: Category?,
    ) {
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiStateData = uiState.value.copy(
                category = updatedCategory,
            ),
        )
    }

    override fun updateSelectedTransactionForIndex(
        updatedSelectedTransactionForIndex: Int,
    ) {
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiStateData = uiState.value.copy(
                selectedTransactionForIndex = updatedSelectedTransactionForIndex,
            ),
        )
    }

    override fun updateAccountFrom(
        updatedSourceFrom: Source?,
    ) {
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiStateData = uiState.value.copy(
                sourceFrom = updatedSourceFrom,
            ),
        )
    }

    override fun updateAccountTo(
        updatedSourceTo: Source?,
    ) {
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiStateData = uiState.value.copy(
                sourceTo = updatedSourceTo,
            ),
        )
    }

    override fun updateTransactionDate(
        updatedTransactionDate: LocalDate,
    ) {
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiStateData = uiState.value.copy(
                transactionDate = updatedTransactionDate,
            ),
        )
    }

    override fun updateTransactionTime(
        updatedTransactionTime: LocalTime,
    ) {
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiStateData = uiState.value.copy(
                transactionTime = updatedTransactionTime,
            ),
        )
    }

    private fun updateAddOrEditTransactionScreenUiState(
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
                    categories.value = getAllCategoriesUseCase()
                },
                async {
                    sources.value = getAllAccountsUseCase().sortedBy { source ->
                        source.type.sortOrder
                    }
                },
                async {
                    transactionForValues.value = getAllTransactionForValuesUseCase()
                },
            )
            getTransactionTypesForNewTransaction()
            getOriginalTransactionData(
                coroutineScope = this,
            )
            setDefaultCategory()
            setDefaultSource()
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
                transactionType ?: return@collectLatest
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
                            updatedSourceFrom = null,
                        )
                        updateAccountTo(
                            updatedSourceTo = originalTransactionData.value?.sourceTo
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
                            updatedSourceFrom = originalTransactionData.value?.sourceFrom
                                ?: defaultAccount,
                        )
                        updateAccountTo(
                            updatedSourceTo = null,
                        )
                    }

                    TransactionType.TRANSFER -> {
                        updateAccountFrom(
                            updatedSourceFrom = originalTransactionData.value?.sourceFrom
                                ?: defaultAccount,
                        )
                        updateAccountTo(
                            updatedSourceTo = originalTransactionData.value?.sourceTo
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
                            updatedSourceFrom = originalTransactionData.value?.sourceFrom
                                ?: defaultAccount,
                        )
                        updateAccountTo(
                            updatedSourceTo = null,
                        )
                    }

                    TransactionType.REFUND -> {}
                }
            }
        }
    }

    private fun observeSelectedCategory(
        coroutineScope: CoroutineScope,
    ) {
        coroutineScope.launch {
            selectedCategoryId.collectLatest { selectedCategoryIdValue ->
                selectedCategoryIdValue ?: return@collectLatest
                titleSuggestions.update {
                    getTitleSuggestionsUseCase(
                        categoryId = selectedCategoryIdValue,
                    )
                }
            }
        }
    }

    private suspend fun getTransactionTypesForNewTransaction() {
        val sourceCount = getAllAccountsCountUseCase()
        val excludedTransactionTypes = mutableSetOf(
            TransactionType.ADJUSTMENT,
            TransactionType.REFUND
        )

        // Cannot create transfer with single source
        if (sourceCount <= 1) {
            excludedTransactionTypes.add(TransactionType.TRANSFER)
        }
        val transactionTypesForNewTransaction =
            (TransactionType.values().toSet() - excludedTransactionTypes).toList()
        this.transactionTypesForNewTransaction.value = transactionTypesForNewTransaction

        if (addOrEditTransactionScreenArgs.isEdit.isFalse() &&
            addOrEditTransactionScreenArgs.originalTransactionId.isNull()
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
        addOrEditTransactionScreenArgs.originalTransactionId?.let { id ->
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
        val isAddingRefund = addOrEditTransactionScreenArgs.isEdit.isFalse() &&
                addOrEditTransactionScreenArgs.originalTransactionId.isNotNull()
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
                sourceFrom = originalTransactionData.value?.sourceTo,
                sourceTo = originalTransactionData.value?.sourceFrom,
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
                sourceFrom = originalTransactionData.value?.sourceFrom,
                sourceTo = originalTransactionData.value?.sourceTo,
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

        // TODO-Abhi: This is a better race condition, but still not reactive
        setInitialSelectedTransactionType()
    }

    private suspend fun calculateMaxRefundAmount() {
        val transactionId = addOrEditTransactionScreenArgs.originalTransactionId ?: return
        val transactionData = getTransactionDataUseCase(
            id = transactionId,
        )
        if (!((addOrEditTransactionScreenArgs.isEdit.isTrue() &&
                    transactionData?.transaction?.transactionType == TransactionType.REFUND) ||
                    addOrEditTransactionScreenArgs.isEdit.isFalse())
        ) {
            return
        }

        var transactionDataToRefund: TransactionData? = null
        if (addOrEditTransactionScreenArgs.isEdit.isTrue()) {
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
            if (it != addOrEditTransactionScreenArgs.originalTransactionId) {
                getTransactionDataUseCase(
                    id = it,
                )?.transaction?.amount?.let { prevRefundedTransactionAmount ->
                    refundedAmountCalculated = refundedAmountCalculated?.run {
                        this + prevRefundedTransactionAmount
                    } ?: prevRefundedTransactionAmount
                }
            }
        }
        transactionDataToRefund?.transaction?.amount?.let { originalTransactionAmount ->
            maxRefundAmount.value = if (refundedAmountCalculated.isNotNull()) {
                originalTransactionAmount - (refundedAmountCalculated ?: Amount())
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
        if (addOrEditTransactionScreenArgs.isEdit != true) {
            updateCategory(
                updatedCategory = expenseDefaultCategory,
            )
        }
    }

    private fun setDefaultSource() {
        defaultAccount = getSource(
            sourceId = defaultDataIdFromDataStore?.source,
        ) ?: sources.value?.firstOrNull { source ->
            isDefaultAccount(
                source = source.name,
            )
        }
        if (addOrEditTransactionScreenArgs.isEdit != true) {
            updateAccountFrom(
                updatedSourceFrom = defaultAccount,
            )
            updateAccountTo(
                updatedSourceTo = defaultAccount,
            )
        }
    }

    private fun setInitialSelectedTransactionType() {
        selectedTransactionType.value = if (addOrEditTransactionScreenArgs.isEdit.isTrue()) {
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
            if (addOrEditTransactionScreenArgs.originalTransactionId.isNotNull()) {
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

    private fun getSource(
        sourceId: Int?,
    ): Source? {
        return sources.value?.find { source ->
            source.id == sourceId
        }
    }
}
