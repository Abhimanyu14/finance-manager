package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.edit_transaction.viewmodel

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
import com.makeappssimple.abhimanyu.financemanager.android.core.database.amount.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.GetCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.sortOrder
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourcesCountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.UpdateSourcesBalanceAmountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetTitleSuggestionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase.GetAllTransactionForValuesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase.UpdateTransactionUseCase
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
import kotlinx.coroutines.flow.collect
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
internal class EditTransactionScreenViewModelImpl @Inject constructor(
    getCategoriesUseCase: GetCategoriesUseCase,
    getSourcesUseCase: GetSourcesUseCase,
    getTitleSuggestionsUseCase: GetTitleSuggestionsUseCase,
    getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
    savedStateHandle: SavedStateHandle,
    override val navigationManager: NavigationManager,
    private val dispatcherProvider: DispatcherProvider,
    private val getSourcesCountUseCase: GetSourcesCountUseCase,
    private val getTransactionUseCase: GetTransactionUseCase,
    private val updateSourcesBalanceAmountUseCase: UpdateSourcesBalanceAmountUseCase,
    private val updateTransactionUseCase: UpdateTransactionUseCase,
) : EditTransactionScreenViewModel, ViewModel() {
    private var originalTransactionId: Int? = null

    private var defaultSource: Source? = null
    private var expenseDefaultCategory: Category? = null
    private var incomeDefaultCategory: Category? = null
    private var investmentDefaultCategory: Category? = null

    private val originalTransaction: MutableStateFlow<Transaction?> = MutableStateFlow(
        value = null,
    )
    private var originalTransactionCategory: Category? = null
    private var originalTransactionSourceFrom: Source? = null
    private var originalTransactionSourceTo: Source? = null
    private val categories: StateFlow<List<Category>> = getCategoriesUseCase().defaultListStateIn(
        scope = viewModelScope,
    )

    override val transactionTypesForNewTransaction: StateFlow<List<TransactionType>> = flow {
        val sourceCount = getSourcesCountUseCase()
        val transactionTypesForNewTransaction = TransactionType.values().filter {
            if (sourceCount > 1) {
                it != TransactionType.ADJUSTMENT && it != TransactionType.REFUND
            } else {
                it != TransactionType.ADJUSTMENT && it != TransactionType.REFUND && it != TransactionType.TRANSFER
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

    private val _uiState: MutableStateFlow<EditTransactionScreenUiState> = MutableStateFlow(
        value = EditTransactionScreenUiState(
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
    override val uiState: StateFlow<EditTransactionScreenUiState> = _uiState

    private val _uiVisibilityState: MutableStateFlow<EditTransactionScreenUiVisibilityState> =
        MutableStateFlow(
            value = EditTransactionScreenUiVisibilityState.Expense,
        )
    override val uiVisibilityState: StateFlow<EditTransactionScreenUiVisibilityState> =
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

            TransactionType.REFUND -> {
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

    init {
        savedStateHandle.get<Int>(NavArgs.TRANSACTION_ID)?.let { id ->
            originalTransactionId = id
            getTransaction(
                id = id,
            )
        }
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            launch {
                transactionTypesForNewTransaction.collectLatest {
                    if (it.isNotEmpty()) {
                        updateSelectedTransactionTypeIndex(
                            updatedSelectedTransactionTypeIndex = it.indexOf(
                                element = TransactionType.EXPENSE,
                            ),
                        )
                    }
                }
            }
            launch {
                categories.collectLatest {
                    if (it.isNotEmpty()) {
                        expenseDefaultCategory = it.firstOrNull { category ->
                            isDefaultExpenseCategory(
                                category = category.title,
                            )
                        }
                        incomeDefaultCategory = it.firstOrNull { category ->
                            isDefaultIncomeCategory(
                                category = category.title,
                            )
                        }
                        investmentDefaultCategory = it.firstOrNull { category ->
                            isDefaultInvestmentCategory(
                                category = category.title,
                            )
                        }
                    }
                }
            }
            launch {
                sources.collectLatest {
                    if (it.isNotEmpty()) {
                        defaultSource = it.firstOrNull { source ->
                            isCashSource(
                                source = source.name,
                            )
                        }
                    }
                }
            }
            launch {
                selectedTransactionType.collectLatest {
                    it ?: return@collectLatest
                    val uiVisibilityState: EditTransactionScreenUiVisibilityState? = when (it) {
                        TransactionType.INCOME -> {
                            EditTransactionScreenUiVisibilityState.Income
                        }

                        TransactionType.EXPENSE -> {
                            EditTransactionScreenUiVisibilityState.Expense
                        }

                        TransactionType.TRANSFER -> {
                            EditTransactionScreenUiVisibilityState.Transfer
                        }

                        TransactionType.ADJUSTMENT -> {
                            null
                        }

                        TransactionType.INVESTMENT -> {
                            EditTransactionScreenUiVisibilityState.Investment
                        }

                        TransactionType.REFUND -> {
                            EditTransactionScreenUiVisibilityState.Refund
                        }
                    }
                    uiVisibilityState?.let {
                        updateEditTransactionScreenUiVisibilityState(
                            updatedEditTransactionScreenUiVisibilityState = uiVisibilityState,
                        )
                    }
                    when (it) {
                        TransactionType.INCOME -> {
                            val updatedCategory =
                                if (selectedTransactionType.value == originalTransaction.value?.transactionType) {
                                    originalTransactionCategory ?: incomeDefaultCategory
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
                                updatedSourceTo = originalTransactionSourceTo ?: defaultSource,
                            )
                        }

                        TransactionType.EXPENSE -> {
                            val updatedCategory =
                                if (selectedTransactionType.value == originalTransaction.value?.transactionType) {
                                    originalTransactionCategory ?: expenseDefaultCategory
                                } else {
                                    expenseDefaultCategory
                                }
                            updateCategory(
                                updatedCategory = updatedCategory,
                            )

                            updateSourceFrom(
                                updatedSourceFrom = originalTransactionSourceFrom ?: defaultSource,
                            )
                            updateSourceTo(
                                updatedSourceTo = null,
                            )
                        }

                        TransactionType.TRANSFER -> {
                            updateSourceFrom(
                                updatedSourceFrom = originalTransactionSourceFrom ?: defaultSource,
                            )
                            updateSourceTo(
                                updatedSourceTo = originalTransactionSourceTo ?: defaultSource,
                            )
                        }

                        TransactionType.ADJUSTMENT -> {}

                        TransactionType.INVESTMENT -> {
                            val updatedCategory =
                                if (selectedTransactionType.value == originalTransaction.value?.transactionType) {
                                    originalTransactionCategory ?: investmentDefaultCategory
                                } else {
                                    investmentDefaultCategory
                                }
                            updateCategory(
                                updatedCategory = updatedCategory,
                            )

                            updateSourceFrom(
                                updatedSourceFrom = originalTransactionSourceFrom ?: defaultSource,
                            )
                            updateSourceTo(
                                updatedSourceTo = null,
                            )
                        }

                        TransactionType.REFUND -> {
                            TODO()
                        }
                    }
                }
            }
            launch {
                combine(
                    flow = transactionTypesForNewTransaction,
                    flow2 = sources,
                    flow3 = categories,
                    flow4 = originalTransaction,
                ) { transactionTypesForNewTransaction, sources, categories, transaction ->
                    if (transactionTypesForNewTransaction.isNotEmpty() &&
                        sources.isNotEmpty() &&
                        categories.isNotEmpty() &&
                        transaction != null
                    ) {
                        originalTransactionCategory = getCategory(
                            categoryId = transaction.categoryId,
                        )
                        originalTransactionSourceFrom = getSource(
                            sourceId = transaction.sourceFromId,
                        )
                        originalTransactionSourceTo = getSource(
                            sourceId = transaction.sourceToId,
                        )
                        updateOriginalTransactionValue(
                            transaction = transaction,
                        )
                    }
                }.collect()
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
                        TODO()
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
                        TODO()
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

                    TransactionType.REFUND -> {
                        1
                    }
                }

                originalTransaction.value?.let { transaction ->
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
                    originalTransactionSourceFrom?.let { transactionSourceFrom ->
                        sourceBalanceAmountChangeMap[transactionSourceFrom.id] =
                            (sourceBalanceAmountChangeMap[transactionSourceFrom.id]
                                ?: 0) + transaction.amount.value
                    }
                    uiStateValue.sourceFrom?.let { sourceFrom ->
                        sourceBalanceAmountChangeMap[sourceFrom.id] =
                            (sourceBalanceAmountChangeMap[sourceFrom.id]
                                ?: 0) - uiState.value.amount.toLong()
                    }
                    originalTransactionSourceTo?.let { transactionSourceTo ->
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
        updateEditTransactionScreenUiState(
            updatedEditTransactionScreenUiState = _uiState.value.copy(
                selectedTransactionTypeIndex = updatedSelectedTransactionTypeIndex,
            ),
        )
    }

    override fun updateAmount(
        updatedAmount: String,
    ) {
        updateEditTransactionScreenUiState(
            updatedEditTransactionScreenUiState = _uiState.value.copy(
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
        updateEditTransactionScreenUiState(
            updatedEditTransactionScreenUiState = _uiState.value.copy(
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
        updateEditTransactionScreenUiState(
            updatedEditTransactionScreenUiState = _uiState.value.copy(
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
        updateEditTransactionScreenUiState(
            updatedEditTransactionScreenUiState = _uiState.value.copy(
                category = updatedCategory,
            ),
        )
    }

    override fun updateSelectedTransactionForIndex(
        updatedSelectedTransactionForIndex: Int,
    ) {
        updateEditTransactionScreenUiState(
            updatedEditTransactionScreenUiState = _uiState.value.copy(
                selectedTransactionForIndex = updatedSelectedTransactionForIndex,
            ),
        )
    }

    override fun updateSourceFrom(
        updatedSourceFrom: Source?,
    ) {
        updateEditTransactionScreenUiState(
            updatedEditTransactionScreenUiState = _uiState.value.copy(
                sourceFrom = updatedSourceFrom,
            ),
        )
    }

    override fun updateSourceTo(
        updatedSourceTo: Source?,
    ) {
        updateEditTransactionScreenUiState(
            updatedEditTransactionScreenUiState = _uiState.value.copy(
                sourceTo = updatedSourceTo,
            ),
        )
    }

    override fun updateTransactionCalendar(
        updatedTransactionCalendar: Calendar,
    ) {
        updateEditTransactionScreenUiState(
            updatedEditTransactionScreenUiState = _uiState.value.copy(
                transactionCalendar = updatedTransactionCalendar,
            ),
        )
    }
    // endregion

    private fun getTransaction(
        id: Int,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            getTransactionUseCase(
                id = id,
            )?.let {
                originalTransaction.value = it
            }
        }
    }

    private fun updateOriginalTransactionValue(
        transaction: Transaction,
    ) {
        val initialEditTransactionScreenUiState = EditTransactionScreenUiState(
            selectedTransactionTypeIndex = transactionTypesForNewTransaction.value.indexOf(
                element = transaction.transactionType,
            ),
            amount = abs(transaction.amount.value).toString(),
            title = transaction.title,
            description = transaction.description,
            category = originalTransactionCategory,
            selectedTransactionForIndex = transactionForValues.value.indexOf(
                element = transactionForValues.value.firstOrNull {
                    it.id == transaction.transactionForId
                },
            ),
            sourceFrom = originalTransactionSourceFrom,
            sourceTo = originalTransactionSourceTo,
            transactionCalendar = Calendar.getInstance(Locale.getDefault()).apply {
                timeInMillis = transaction.transactionTimestamp
            },
        )
        updateEditTransactionScreenUiState(
            updatedEditTransactionScreenUiState = initialEditTransactionScreenUiState,
        )
    }

    private fun updateEditTransactionScreenUiState(
        updatedEditTransactionScreenUiState: EditTransactionScreenUiState,
    ) {
        _uiState.update {
            updatedEditTransactionScreenUiState
        }
    }

    private fun updateEditTransactionScreenUiVisibilityState(
        updatedEditTransactionScreenUiVisibilityState: EditTransactionScreenUiVisibilityState,
    ) {
        _uiVisibilityState.value = updatedEditTransactionScreenUiVisibilityState
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
