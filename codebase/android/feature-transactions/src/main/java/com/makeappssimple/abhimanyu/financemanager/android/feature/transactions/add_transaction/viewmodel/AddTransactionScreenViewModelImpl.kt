package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.defaultBooleanStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.defaultListStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.database.amount.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.GetCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.datastore.MyDataStore
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.sortOrder
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.updateBalanceAmount
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourcesCountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.UpdateSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetTitleSuggestionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.InsertTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.extensions.capitalizeWords
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.extensions.isNotNullOrBlank
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.extensions.isNotZero
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isCashSource
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultInvestmentCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

@HiltViewModel
internal class AddTransactionScreenViewModelImpl @Inject constructor(
    dataStore: MyDataStore,
    getCategoriesUseCase: GetCategoriesUseCase,
    getSourcesUseCase: GetSourcesUseCase,
    getTitleSuggestionsUseCase: GetTitleSuggestionsUseCase,
    getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
    override val navigationManager: NavigationManager,
    private val dispatcherProvider: DispatcherProvider,
    private val getSourcesCountUseCase: GetSourcesCountUseCase,
    private val insertTransactionsUseCase: InsertTransactionsUseCase,
    private val updateSourcesUseCase: UpdateSourcesUseCase,
) : AddTransactionScreenViewModel, ViewModel() {
    private var defaultSource: Source? = null
    private var expenseDefaultCategory: Category? = null
    private var incomeDefaultCategory: Category? = null
    private var investmentDefaultCategory: Category? = null
    private val categories: StateFlow<List<Category>> = getCategoriesUseCase().defaultListStateIn(
        scope = viewModelScope,
    )

    override val transactionTypesForNewTransaction: StateFlow<List<TransactionType>> = flow {
        val sourceCount = getSourcesCountUseCase()
        val transactionTypesForNewTransaction = TransactionType.values().filter {
            if (sourceCount > 1) {
                it != TransactionType.ADJUSTMENT
            } else {
                it != TransactionType.ADJUSTMENT && it != TransactionType.TRANSFER
            }
        }
        emit(
            value = transactionTypesForNewTransaction,
        )
    }.defaultListStateIn(
        scope = viewModelScope,
    )
    override val sources: StateFlow<List<Source>> = getSourcesUseCase()
        .transformLatest {
            emit(
                value = it.sortedBy { source ->
                    source.type.sortOrder
                },
            )
        }.defaultListStateIn(
            scope = viewModelScope,
        )
    override val transactionForValues: StateFlow<List<TransactionFor>> =
        getAllTransactionForValuesUseCase().defaultListStateIn(
            scope = viewModelScope,
        )

    private val _uiState: MutableStateFlow<AddTransactionScreenUiState> = MutableStateFlow(
        value = AddTransactionScreenUiState(
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
    override val uiState: StateFlow<AddTransactionScreenUiState> = _uiState

    private val _uiVisibilityState: MutableStateFlow<AddTransactionScreenUiVisibilityState> =
        MutableStateFlow(
            value = AddTransactionScreenUiVisibilityState.Expense,
        )
    override val uiVisibilityState: StateFlow<AddTransactionScreenUiVisibilityState> =
        _uiVisibilityState

    override val selectedTransactionType: StateFlow<TransactionType?> = combine(
        flow = transactionTypesForNewTransaction,
        flow2 = uiState,
    ) { transactionTypesForNewTransaction, uiState ->
        uiState.selectedTransactionTypeIndex?.let {
            transactionTypesForNewTransaction.getOrNull(
                index = uiState.selectedTransactionTypeIndex,
            )
        }
    }.defaultObjectStateIn(
        scope = viewModelScope,
    )

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

    override val isValidTransactionData: StateFlow<Boolean> = combine(
        flow = uiState,
        flow2 = selectedTransactionType,
    ) { uiState, selectedTransactionType ->
        when (selectedTransactionType) {
            TransactionType.INCOME -> {
                uiState.amount.isNotNullOrBlank() &&
                        uiState.title.isNotNullOrBlank() &&
                        uiState.amount.toInt().isNotZero()
            }
            TransactionType.EXPENSE -> {
                uiState.amount.isNotNullOrBlank() &&
                        uiState.title.isNotNullOrBlank() &&
                        uiState.amount.toInt().isNotZero()
            }
            TransactionType.TRANSFER -> {
                uiState.amount.isNotNullOrBlank() &&
                        uiState.sourceFrom?.id != uiState.sourceTo?.id &&
                        uiState.amount.toInt().isNotZero()
            }
            TransactionType.ADJUSTMENT -> {
                false
            }
            TransactionType.INVESTMENT -> {
                uiState.amount.isNotNullOrBlank() &&
                        uiState.title.isNotNullOrBlank() &&
                        uiState.amount.toInt().isNotZero()
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

    private val defaultSourceIdFromDataStore: StateFlow<Int?> = dataStore
        .getDefaultSourceIdFromDataStore()
        .defaultObjectStateIn(
            scope = viewModelScope,
        )
    private val defaultExpenseCategoryIdFromDataStore: StateFlow<Int?> = dataStore
        .getDefaultExpenseCategoryIdFromDataStore()
        .defaultObjectStateIn(
            scope = viewModelScope,
        )
    private val defaultIncomeCategoryIdFromDataStore: StateFlow<Int?> = dataStore
        .getDefaultIncomeCategoryIdFromDataStore()
        .defaultObjectStateIn(
            scope = viewModelScope,
        )
    private val defaultInvestmentCategoryIdFromDataStore: StateFlow<Int?> = dataStore
        .getDefaultInvestmentCategoryIdFromDataStore()
        .defaultObjectStateIn(
            scope = viewModelScope,
        )

    init {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            launch {
                transactionTypesForNewTransaction.collectLatest {
                    updateSelectedTransactionTypeIndex(
                        updatedSelectedTransactionTypeIndex = it.indexOf(
                            element = TransactionType.EXPENSE,
                        ),
                    )
                }
            }
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
                        updatedSourceFrom = defaultSource,
                    )
                    updateSourceTo(
                        updatedSourceTo = null,
                    )
                }
            }
            launch {
                selectedTransactionType.collectLatest {
                    val uiVisibilityState: AddTransactionScreenUiVisibilityState? = when (it) {
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
                        null -> {
                            null
                        }
                    }
                    uiVisibilityState?.let {
                        updateAddTransactionScreenUiVisibilityState(
                            updatedAddTransactionScreenUiVisibilityState = uiVisibilityState,
                        )
                    }

                    when (it) {
                        TransactionType.INCOME -> {
                            updateCategory(
                                updatedCategory = incomeDefaultCategory,
                            )

                            updateSourceFrom(
                                updatedSourceFrom = null,
                            )
                            updateSourceTo(
                                updatedSourceTo = defaultSource,
                            )
                        }
                        TransactionType.EXPENSE -> {
                            updateCategory(
                                updatedCategory = expenseDefaultCategory,
                            )

                            updateSourceFrom(
                                updatedSourceFrom = defaultSource,
                            )
                            updateSourceTo(
                                updatedSourceTo = null,
                            )
                        }
                        TransactionType.TRANSFER -> {
                            updateSourceFrom(
                                updatedSourceFrom = defaultSource,
                            )
                            updateSourceTo(
                                updatedSourceTo = defaultSource,
                            )
                        }
                        TransactionType.ADJUSTMENT -> {}
                        TransactionType.INVESTMENT -> {
                            updateCategory(
                                updatedCategory = investmentDefaultCategory,
                            )

                            updateSourceFrom(
                                updatedSourceFrom = defaultSource,
                            )
                            updateSourceTo(
                                updatedSourceTo = null,
                            )
                        }
                        null -> {}
                    }
                }
            }
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
                }
                val title = if (selectedTransactionTypeValue == TransactionType.TRANSFER) {
                    TransactionType.TRANSFER.title
                } else {
                    uiStateValue.title.capitalizeWords()
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
                }

                insertTransactionsUseCase(
                    Transaction(
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
                uiStateValue.sourceFrom?.let { sourceFrom ->
                    updateSourcesUseCase(
                        sourceFrom.updateBalanceAmount(
                            updatedBalanceAmount = sourceFrom.balanceAmount.value - uiStateValue.amount.toLong(),
                        ),
                    )
                }
                uiStateValue.sourceTo?.let { sourceTo ->
                    updateSourcesUseCase(
                        sourceTo.updateBalanceAmount(
                            updatedBalanceAmount = sourceTo.balanceAmount.value + uiStateValue.amount.toLong(),
                        )
                    )
                }
                navigateUp(
                    navigationManager = navigationManager,
                )
            }
        }
    }

    // region UI changes
    override fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    ) {
        updateAddTransactionScreenUiState(
            updatedAddTransactionScreenUiState = _uiState.value.copy(
                selectedTransactionTypeIndex = updatedSelectedTransactionTypeIndex,
            ),
        )
    }

    override fun updateAmount(
        updatedAmount: String,
    ) {
        updateAddTransactionScreenUiState(
            updatedAddTransactionScreenUiState = _uiState.value.copy(
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
        updateAddTransactionScreenUiState(
            updatedAddTransactionScreenUiState = _uiState.value.copy(
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
        updateAddTransactionScreenUiState(
            updatedAddTransactionScreenUiState = _uiState.value.copy(
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
        updateAddTransactionScreenUiState(
            updatedAddTransactionScreenUiState = _uiState.value.copy(
                category = updatedCategory,
            ),
        )
    }

    override fun updateSelectedTransactionForIndex(
        updatedSelectedTransactionForIndex: Int,
    ) {
        updateAddTransactionScreenUiState(
            updatedAddTransactionScreenUiState = _uiState.value.copy(
                selectedTransactionForIndex = updatedSelectedTransactionForIndex,
            ),
        )
    }

    override fun updateSourceFrom(
        updatedSourceFrom: Source?,
    ) {
        updateAddTransactionScreenUiState(
            updatedAddTransactionScreenUiState = _uiState.value.copy(
                sourceFrom = updatedSourceFrom,
            ),
        )
    }

    override fun updateSourceTo(
        updatedSourceTo: Source?,
    ) {
        updateAddTransactionScreenUiState(
            updatedAddTransactionScreenUiState = _uiState.value.copy(
                sourceTo = updatedSourceTo,
            ),
        )
    }

    override fun updateTransactionCalendar(
        updatedTransactionCalendar: Calendar,
    ) {
        updateAddTransactionScreenUiState(
            updatedAddTransactionScreenUiState = _uiState.value.copy(
                transactionCalendar = updatedTransactionCalendar,
            ),
        )
    }
    // endregion

    private fun updateAddTransactionScreenUiState(
        updatedAddTransactionScreenUiState: AddTransactionScreenUiState,
    ) {
        _uiState.update {
            updatedAddTransactionScreenUiState
        }
    }

    private fun updateAddTransactionScreenUiVisibilityState(
        updatedAddTransactionScreenUiVisibilityState: AddTransactionScreenUiVisibilityState,
    ) {
        _uiVisibilityState.value = updatedAddTransactionScreenUiVisibilityState
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
