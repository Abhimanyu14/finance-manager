package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.defaultBooleanStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.defaultListStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.capitalizeWords
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNullOrBlank
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.Quadruple
import com.makeappssimple.abhimanyu.financemanager.android.core.database.amount.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.GetCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.sortOrder
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourcesCountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.UpdateSourcesBalanceAmountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetTitleSuggestionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetTransactionDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.InsertTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase.UpdateTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavArgs
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isCashSource
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultInvestmentCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.abs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

@HiltViewModel
internal class AddOrEditTransactionScreenViewModelImpl @Inject constructor(
    getCategoriesUseCase: GetCategoriesUseCase,
    getSourcesUseCase: GetSourcesUseCase,
    getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
    savedStateHandle: SavedStateHandle,
    override val navigationManager: NavigationManager,
    private val dispatcherProvider: DispatcherProvider,
    private val getTitleSuggestionsUseCase: GetTitleSuggestionsUseCase,
    private val getSourcesCountUseCase: GetSourcesCountUseCase,
    private val getTransactionDataUseCase: GetTransactionDataUseCase,
    private val insertTransactionUseCase: InsertTransactionUseCase,
    private val dataStore: MyDataStore,
    private val updateSourcesBalanceAmountUseCase: UpdateSourcesBalanceAmountUseCase,
    private val updateTransactionUseCase: UpdateTransactionUseCase,
) : AddOrEditTransactionScreenViewModel, ViewModel() {
    // Navigation parameters
    private var edit: Boolean? = null
    private var originalTransactionId: Int? = null

    // Default data
    private var defaultSource: Source? = null
    private var expenseDefaultCategory: Category? = null
    private var incomeDefaultCategory: Category? = null
    private var investmentDefaultCategory: Category? = null

    // Original transaction data
    private var originalTransactionData: MutableStateFlow<TransactionData?> = MutableStateFlow(
        value = null,
    )
    private var maxRefundAmount: MutableStateFlow<Amount?> = MutableStateFlow(
        value = null,
    )

    // Default data from data source
    private var defaultSourceIdFromDataStore: MutableStateFlow<Int?> = MutableStateFlow(
        value = null,
    )
    private var defaultExpenseCategoryIdFromDataStore: MutableStateFlow<Int?> = MutableStateFlow(
        value = null,
    )
    private var defaultIncomeCategoryIdFromDataStore: MutableStateFlow<Int?> = MutableStateFlow(
        value = null,
    )
    private var defaultInvestmentCategoryIdFromDataStore: MutableStateFlow<Int?> = MutableStateFlow(
        value = null,
    )

    private val categories: StateFlow<List<Category>> = getCategoriesUseCase().defaultListStateIn(
        scope = viewModelScope,
    )

    private var _transactionTypesForNewTransaction: MutableStateFlow<List<TransactionType>> =
        MutableStateFlow(
            value = emptyList(),
        )
    override val transactionTypesForNewTransaction: StateFlow<List<TransactionType>> =
        _transactionTypesForNewTransaction
    override val sources: StateFlow<List<Source>> = getSourcesUseCase()
        .map {
            it.sortedBy { source ->
                source.type.sortOrder
            }
        }
        .defaultListStateIn(
            scope = viewModelScope,
        )
    override val transactionForValues: StateFlow<List<TransactionFor>> =
        getAllTransactionForValuesUseCase().defaultListStateIn(
            scope = viewModelScope,
        )

    private val _uiState: MutableStateFlow<AddOrEditTransactionScreenUiState> = MutableStateFlow(
        value = AddOrEditTransactionScreenUiState(
            selectedTransactionTypeIndex = null,
            amount = "",
            title = "",
            description = "",
            category = null,
            selectedTransactionForIndex = 0,
            sourceFrom = null,
            sourceTo = null,
            transactionCalendar = Calendar.getInstance(Locale.getDefault()),
        ),
    )
    override val uiState: StateFlow<AddOrEditTransactionScreenUiState> = _uiState

    private val _uiVisibilityState: MutableStateFlow<AddOrEditTransactionScreenUiVisibilityState> =
        MutableStateFlow(
            value = AddOrEditTransactionScreenUiVisibilityState.Expense,
        )
    override val uiVisibilityState: StateFlow<AddOrEditTransactionScreenUiVisibilityState> =
        _uiVisibilityState


    // Dependant data
    private val _selectedTransactionType: MutableStateFlow<TransactionType?> = MutableStateFlow(
        value = null,
    )
    override val selectedTransactionType: StateFlow<TransactionType?> = _selectedTransactionType

    override val filteredCategories: StateFlow<List<Category>> = combine(
        flow = categories,
        flow2 = selectedTransactionType,
    ) { categories, selectedTransactionType ->
        categories.filter { category ->
            category.transactionType == selectedTransactionType
        }
    }.defaultListStateIn(
        scope = viewModelScope,
    )

    override val isCtaButtonEnabled: StateFlow<Boolean> = combine(
        flow = uiState,
        flow2 = selectedTransactionType,
    ) { uiState, selectedTransactionType ->
        when (selectedTransactionType) {
            TransactionType.INCOME -> {
                uiState.amount.isNotNullOrBlank() &&
                        uiState.title.isNotNullOrBlank() &&
                        uiState.amount.toInt().isNotZero() &&
                        uiState.amountErrorText.isNull()
            }

            TransactionType.EXPENSE -> {
                uiState.amount.isNotNullOrBlank() &&
                        uiState.title.isNotNullOrBlank() &&
                        uiState.amount.toInt().isNotZero() &&
                        uiState.amountErrorText.isNull()
            }

            TransactionType.TRANSFER -> {
                uiState.amount.isNotNullOrBlank() &&
                        uiState.sourceFrom?.id != uiState.sourceTo?.id &&
                        uiState.amount.toInt().isNotZero() &&
                        uiState.amountErrorText.isNull()
            }

            TransactionType.ADJUSTMENT -> {
                false
            }

            TransactionType.INVESTMENT -> {
                uiState.amount.isNotNullOrBlank() &&
                        uiState.title.isNotNullOrBlank() &&
                        uiState.amount.toInt().isNotZero() &&
                        uiState.amountErrorText.isNull()
            }

            TransactionType.REFUND -> {
                val maxRefundAmountValue = maxRefundAmount.value?.value ?: 0L
                if (uiState.amountErrorText == null &&
                    ((uiState.amount.toLongOrNull() ?: 0L) > maxRefundAmountValue)
                ) {
                    updateAddOrEditTransactionScreenUiState(
                        updatedAddOrEditTransactionScreenUiState = uiState.copy(
                            amountErrorText = maxRefundAmount.value?.run {
                                this.toString()
                            },
                        )
                    )
                    false
                } else if (uiState.amountErrorText != null &&
                    ((uiState.amount.toLongOrNull() ?: 0L) <= maxRefundAmountValue)
                ) {
                    updateAddOrEditTransactionScreenUiState(
                        updatedAddOrEditTransactionScreenUiState = uiState.copy(
                            amountErrorText = null,
                        )
                    )
                    false
                } else {
                    uiState.amount.isNotNullOrBlank() &&
                            uiState.title.isNotNullOrBlank() &&
                            uiState.amount.toInt().isNotZero() &&
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

    private val selectedCategoryId: StateFlow<Int?> = uiState.map {
        it.category?.id
    }.defaultObjectStateIn(
        scope = viewModelScope,
    )
    private val _titleSuggestions: MutableStateFlow<List<String>> = MutableStateFlow(
        value = emptyList(),
    )
    override val titleSuggestions: StateFlow<List<String>> = _titleSuggestions

    init {
        getNavigationArguments(
            savedStateHandle = savedStateHandle,
        )
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            // Default data
            launch {
                dataStore.getDefaultSourceIdFromDataStore().collectLatest {
                    defaultSourceIdFromDataStore.value = it
                }
            }
            launch {
                dataStore.getDefaultExpenseCategoryIdFromDataStore().collectLatest {
                    defaultExpenseCategoryIdFromDataStore.value = it
                }
            }
            launch {
                dataStore.getDefaultIncomeCategoryIdFromDataStore().collectLatest {
                    defaultIncomeCategoryIdFromDataStore.value = it
                }
            }
            launch {
                dataStore.getDefaultInvestmentCategoryIdFromDataStore().collectLatest {
                    defaultInvestmentCategoryIdFromDataStore.value = it
                }
            }

            getTransactionTypesForNewTransaction()
            getOriginalTransactionData()

            // Add transaction except refund type
            // Categories
            launch {
                combine(
                    flow = categories,
                    flow2 = defaultExpenseCategoryIdFromDataStore,
                    flow3 = defaultIncomeCategoryIdFromDataStore,
                    flow4 = defaultInvestmentCategoryIdFromDataStore,
                ) {
                        categories,
                        defaultExpenseCategoryIdFromDataStore,
                        defaultIncomeCategoryIdFromDataStore,
                        defaultInvestmentCategoryIdFromDataStore,
                    ->

                    expenseDefaultCategory = getCategory(
                        categoryId = defaultExpenseCategoryIdFromDataStore,
                    ) ?: categories.firstOrNull { category ->
                        isDefaultExpenseCategory(
                            category = category.title,
                        )
                    }
                    incomeDefaultCategory = getCategory(
                        categoryId = defaultIncomeCategoryIdFromDataStore,
                    ) ?: categories.firstOrNull { category ->
                        isDefaultIncomeCategory(
                            category = category.title,
                        )
                    }
                    investmentDefaultCategory = getCategory(
                        categoryId = defaultInvestmentCategoryIdFromDataStore,
                    ) ?: categories.firstOrNull { category ->
                        isDefaultInvestmentCategory(
                            category = category.title,
                        )
                    }

                    expenseDefaultCategory
                }.collectLatest {
                    updateCategory(
                        updatedCategory = it,
                    )
                }
            }

            // Sources
            launch {
                combine(
                    flow = sources,
                    flow2 = defaultSourceIdFromDataStore,
                ) { sources, defaultSourceIdFromDataStore ->
                    defaultSource = getSource(
                        sourceId = defaultSourceIdFromDataStore,
                    ) ?: sources.firstOrNull { source ->
                        isCashSource(
                            source = source.name,
                        )
                    }
                    defaultSource
                }.collectLatest {
                    updateSourceFrom(
                        updatedSourceFrom = it,
                    )
                    updateSourceTo(
                        updatedSourceTo = it,
                    )
                }
            }

            // Transaction types for new transaction
            launch {
                combine(
                    flow = transactionTypesForNewTransaction,
                    flow2 = uiState,
                ) {
                        transactionTypesForNewTransaction,
                        uiState,
                    ->
                    if (edit == true) {
                        uiState.selectedTransactionTypeIndex?.let {
                            transactionTypesForNewTransaction.getOrNull(
                                index = uiState.selectedTransactionTypeIndex,
                            )
                        }
                    } else {
                        if (originalTransactionId != null) {
                            TransactionType.REFUND
                        } else {
                            uiState.selectedTransactionTypeIndex?.let {
                                transactionTypesForNewTransaction.getOrNull(
                                    index = uiState.selectedTransactionTypeIndex,
                                )
                            }
                        }
                    }
                }.collectLatest {
                    _selectedTransactionType.value = it
                }
            }

            // Selected transaction types
            launch {
                selectedTransactionType.collectLatest {
                    it ?: return@collectLatest
                    val uiVisibilityState: AddOrEditTransactionScreenUiVisibilityState? =
                        when (it) {
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

                    when (it) {
                        TransactionType.INCOME -> {
                            val updatedCategory =
                                if (it == originalTransactionData.value?.transaction?.transactionType) {
                                    originalTransactionData.value?.category ?: incomeDefaultCategory
                                } else {
                                    incomeDefaultCategory
                                }
                            updateCategory(
                                updatedCategory = updatedCategory,
                            )

                            updateSourceFrom(
                                updatedSourceFrom = null,
                            )
                            updateSourceTo(
                                updatedSourceTo = originalTransactionData.value?.sourceTo
                                    ?: defaultSource,
                            )
                        }

                        TransactionType.EXPENSE -> {
                            val updatedCategory =
                                if (it == originalTransactionData.value?.transaction?.transactionType) {
                                    originalTransactionData.value?.category
                                        ?: expenseDefaultCategory
                                } else {
                                    expenseDefaultCategory
                                }
                            updateCategory(
                                updatedCategory = updatedCategory,
                            )

                            updateSourceFrom(
                                updatedSourceFrom = originalTransactionData.value?.sourceFrom
                                    ?: defaultSource,
                            )
                            updateSourceTo(
                                updatedSourceTo = null,
                            )
                        }

                        TransactionType.TRANSFER -> {
                            updateSourceFrom(
                                updatedSourceFrom = originalTransactionData.value?.sourceFrom
                                    ?: defaultSource,
                            )
                            updateSourceTo(
                                updatedSourceTo = originalTransactionData.value?.sourceTo
                                    ?: defaultSource,
                            )
                        }

                        TransactionType.ADJUSTMENT -> {}

                        TransactionType.INVESTMENT -> {
                            val updatedCategory =
                                if (it == originalTransactionData.value?.transaction?.transactionType) {
                                    originalTransactionData.value?.category
                                        ?: investmentDefaultCategory
                                } else {
                                    investmentDefaultCategory
                                }
                            updateCategory(
                                updatedCategory = updatedCategory,
                            )

                            updateSourceFrom(
                                updatedSourceFrom = originalTransactionData.value?.sourceFrom
                                    ?: defaultSource,
                            )
                            updateSourceTo(
                                updatedSourceTo = null,
                            )
                        }

                        TransactionType.REFUND -> {}
                    }
                }
            }

            // Selected category
            launch {
                selectedCategoryId.collectLatest {
                    val selectedCategoryIdValue = it ?: return@collectLatest
                    _titleSuggestions.update {
                        getTitleSuggestionsUseCase(
                            categoryId = selectedCategoryIdValue,
                        )
                    }
                }
            }
        }
    }

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }

    override fun insertTransaction() {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            val uiStateValue = uiState.value
            selectedTransactionType.value?.let { selectedTransactionTypeValue ->
                val amountValue = uiStateValue.amount.toLong()
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
                        uiStateValue.title.capitalizeWords()
                    }
                }
                val transactionForId: Int = when (selectedTransactionTypeValue) {
                    TransactionType.INCOME -> {
                        1
                    }

                    TransactionType.EXPENSE -> {
                        transactionForValues.value[uiStateValue.selectedTransactionForIndex].id
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
                val originalTransactionId = if (edit == false && originalTransactionId != null ||
                    edit == true && originalTransactionData.value?.transaction?.transactionType == TransactionType.REFUND
                ) {
                    originalTransactionId
                } else {
                    null
                }

                val id = insertTransactionUseCase(
                    amountValue = amountValue,
                    sourceFrom = if (sourceFromId != null) {
                        uiStateValue.sourceFrom
                    } else {
                        null
                    },
                    sourceTo = if (sourceToId != null) {
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
                        description = uiStateValue.description,
                        title = title,
                        creationTimestamp = System.currentTimeMillis(),
                        transactionTimestamp = uiStateValue.transactionCalendar.timeInMillis,
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
                navigateUp(
                    navigationManager = navigationManager,
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
                    value = uiStateValue.amount.toLong(),
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
                        uiStateValue.title.capitalizeWords()
                    }
                }
                val transactionForId: Int = when (selectedTransactionTypeValue) {
                    TransactionType.INCOME -> {
                        1
                    }

                    TransactionType.EXPENSE -> {
                        transactionForValues.value[uiStateValue.selectedTransactionForIndex].id
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
                    updateTransactionUseCase(
                        originalTransaction = transaction,
                        updatedTransaction = transaction.copy(
                            amount = amount,
                            categoryId = categoryId,
                            sourceFromId = sourceFromId,
                            sourceToId = sourceToId,
                            description = uiStateValue.description,
                            title = title,
                            creationTimestamp = System.currentTimeMillis(),
                            transactionTimestamp = uiStateValue.transactionCalendar.timeInMillis,
                            transactionForId = transactionForId,
                            transactionType = selectedTransactionTypeValue,
                        ),
                    )

                    // region transaction source updates
                    val sourceBalanceAmountChangeMap = hashMapOf<Int, Long>()
                    originalTransactionData.value?.sourceFrom?.let { transactionSourceFrom ->
                        sourceBalanceAmountChangeMap[transactionSourceFrom.id] =
                            (sourceBalanceAmountChangeMap[transactionSourceFrom.id]
                                ?: 0) + transaction.amount.value
                    }
                    uiStateValue.sourceFrom?.let { sourceFrom ->
                        sourceBalanceAmountChangeMap[sourceFrom.id] =
                            (sourceBalanceAmountChangeMap[sourceFrom.id]
                                ?: 0) - uiState.value.amount.toLong()
                    }
                    originalTransactionData.value?.sourceTo?.let { transactionSourceTo ->
                        sourceBalanceAmountChangeMap[transactionSourceTo.id] =
                            (sourceBalanceAmountChangeMap[transactionSourceTo.id]
                                ?: 0) - transaction.amount.value
                    }
                    uiStateValue.sourceTo?.let { sourceTo ->
                        sourceBalanceAmountChangeMap[sourceTo.id] =
                            (sourceBalanceAmountChangeMap[sourceTo.id]
                                ?: 0) + uiState.value.amount.toLong()
                    }
                    updateSourcesBalanceAmountUseCase(
                        sourcesBalanceAmountChange = sourceBalanceAmountChangeMap.toList(),
                    )
                    // endregion

                }
            }
            navigateUp(
                navigationManager = navigationManager,
            )
        }
    }

    // region UI changes
    override fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    ) {
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiState = _uiState.value.copy(
                selectedTransactionTypeIndex = updatedSelectedTransactionTypeIndex,
            ),
        )
    }

    override fun updateAmount(
        updatedAmount: String,
    ) {
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiState = _uiState.value.copy(
                amount = updatedAmount,
            ),
        )
    }

    override fun clearAmount() {
        updateAmount(
            updatedAmount = "",
        )
    }

    override fun updateTitle(
        updatedTitle: String,
    ) {
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiState = _uiState.value.copy(
                title = updatedTitle,
            ),
        )
    }

    override fun clearTitle() {
        updateTitle(
            updatedTitle = "",
        )
    }

    override fun updateDescription(
        updatedDescription: String,
    ) {
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiState = _uiState.value.copy(
                description = updatedDescription,
            ),
        )
    }

    override fun clearDescription() {
        updateDescription(
            updatedDescription = "",
        )
    }

    override fun updateCategory(
        updatedCategory: Category?,
    ) {
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiState = _uiState.value.copy(
                category = updatedCategory,
            ),
        )
    }

    override fun updateSelectedTransactionForIndex(
        updatedSelectedTransactionForIndex: Int,
    ) {
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiState = _uiState.value.copy(
                selectedTransactionForIndex = updatedSelectedTransactionForIndex,
            ),
        )
    }

    override fun updateSourceFrom(
        updatedSourceFrom: Source?,
    ) {
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiState = _uiState.value.copy(
                sourceFrom = updatedSourceFrom,
            ),
        )
    }

    override fun updateSourceTo(
        updatedSourceTo: Source?,
    ) {
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiState = _uiState.value.copy(
                sourceTo = updatedSourceTo,
            ),
        )
    }

    override fun updateTransactionCalendar(
        updatedTransactionCalendar: Calendar,
    ) {
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiState = _uiState.value.copy(
                transactionCalendar = updatedTransactionCalendar,
            ),
        )
    }
    // endregion

    private fun getNavigationArguments(
        savedStateHandle: SavedStateHandle,
    ) {
        savedStateHandle.get<String>(NavArgs.TRANSACTION_ID)?.let {
            originalTransactionId = it.toIntOrNull()
        }
        savedStateHandle.get<Boolean>(NavArgs.EDIT)?.let {
            edit = it
        }
    }

    private suspend fun getTransactionTypesForNewTransaction() {
        val sourceCount = getSourcesCountUseCase()
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
        _transactionTypesForNewTransaction.value = transactionTypesForNewTransaction

        if (edit == false && originalTransactionId == null) {
            updateSelectedTransactionTypeIndex(
                updatedSelectedTransactionTypeIndex = transactionTypesForNewTransaction.indexOf(
                    element = TransactionType.EXPENSE,
                ),
            )
        }
    }

    private suspend fun getOriginalTransactionData() {
        originalTransactionId?.let { id ->
            viewModelScope.launch(
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

    private suspend fun calculateMaxRefundAmount() {
        val transactionId = originalTransactionId ?: return
        val transactionData = getTransactionDataUseCase(
            id = transactionId,
        )
        if (!((edit == true && transactionData?.transaction?.transactionType == TransactionType.REFUND) || edit == false)) {
            return
        }

        var transactionDataToRefund: TransactionData? = null
        if (edit == true) {
            transactionData?.transaction?.originalTransactionId?.let {
                transactionDataToRefund = getTransactionDataUseCase(
                    id = it,
                )
            }
        } else {
            transactionDataToRefund = transactionData
        }
        if (transactionDataToRefund == null) {
            return
        }

        var refundedAmountCalculated: Amount? = null
        transactionDataToRefund?.transaction?.refundTransactionIds?.forEach {
            if (it != originalTransactionId) {
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
            maxRefundAmount.value = if (refundedAmountCalculated != null) {
                originalTransactionAmount - (refundedAmountCalculated ?: Amount())
            } else {
                originalTransactionAmount
            }
        }
    }

    private fun updateAddOrEditTransactionScreenUiStateWithOriginalTransactionData(
        originalTransaction: Transaction,
        transactionTypesForNewTransaction: List<TransactionType>,
        transactionForValues: List<TransactionFor>,
        maxRefundAmount: Amount?,
    ) {
        val isAddingRefund = edit == false && originalTransactionId != null
        val initialAddOrEditTransactionScreenUiState = if (isAddingRefund) {
            AddOrEditTransactionScreenUiState(
                selectedTransactionTypeIndex = transactionTypesForNewTransaction.indexOf(
                    element = TransactionType.REFUND,
                ),
                amount = (maxRefundAmount?.value ?: 0L).toString(),
                title = TransactionType.REFUND.title,
                description = originalTransaction.description,
                category = originalTransactionData.value?.category,
                selectedTransactionForIndex = transactionForValues.indexOf(
                    element = transactionForValues.firstOrNull {
                        it.id == originalTransaction.transactionForId
                    },
                ),
                sourceFrom = originalTransactionData.value?.sourceTo,
                sourceTo = originalTransactionData.value?.sourceFrom,
                transactionCalendar = Calendar.getInstance(Locale.getDefault()),
            )
        } else {
            AddOrEditTransactionScreenUiState(
                selectedTransactionTypeIndex = transactionTypesForNewTransaction.indexOf(
                    element = originalTransaction.transactionType,
                ),
                amount = abs(originalTransaction.amount.value).toString(),
                title = originalTransaction.title,
                description = originalTransaction.description,
                category = originalTransactionData.value?.category,
                selectedTransactionForIndex = transactionForValues.indexOf(
                    element = transactionForValues.firstOrNull {
                        it.id == originalTransaction.transactionForId
                    },
                ),
                sourceFrom = originalTransactionData.value?.sourceFrom,
                sourceTo = originalTransactionData.value?.sourceTo,
                transactionCalendar = Calendar.getInstance(Locale.getDefault()).apply {
                    timeInMillis = originalTransaction.transactionTimestamp
                },
            )
        }
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiState = initialAddOrEditTransactionScreenUiState,
        )
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
                    transactionTypesForNewTransaction = transactionTypesForNewTransaction,
                    transactionForValues = transactionForValues,
                    maxRefundAmount = maxRefundAmount,
                )
            }
        }
    }

    private fun updateAddOrEditTransactionScreenUiState(
        updatedAddOrEditTransactionScreenUiState: AddOrEditTransactionScreenUiState,
    ) {
        _uiState.update {
            updatedAddOrEditTransactionScreenUiState
        }
    }

    private fun updateAddOrEditTransactionScreenUiVisibilityState(
        updatedAddOrEditTransactionScreenUiVisibilityState: AddOrEditTransactionScreenUiVisibilityState,
    ) {
        _uiVisibilityState.value = updatedAddOrEditTransactionScreenUiVisibilityState
    }

    private fun getCategory(
        categoryId: Int?,
    ): Category? {
        return categories.value.find { category ->
            category.id == categoryId
        }
    }

    private fun getSource(
        sourceId: Int?,
    ): Source? {
        return sources.value.find { source ->
            source.id == sourceId
        }
    }
}
