package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_transaction

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.GetCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.GetSourcesCountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.GetSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.UpdateSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase.GetTitleSuggestionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase.GetTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase.UpdateTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.entities.amount.Amount
import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.sortOrder
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavArgs
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.capitalizeWords
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.isNotNullOrBlank
import com.makeappssimple.abhimanyu.financemanager.android.utils.isCashSource
import com.makeappssimple.abhimanyu.financemanager.android.utils.isDefaultCategory
import com.makeappssimple.abhimanyu.financemanager.android.utils.isSalaryCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.abs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.launch
import java.util.Calendar

@HiltViewModel
class EditTransactionScreenViewModelImpl @Inject constructor(
    getCategoriesUseCase: GetCategoriesUseCase,
    getSourcesUseCase: GetSourcesUseCase,
    getTitleSuggestionsUseCase: GetTitleSuggestionsUseCase,
    savedStateHandle: SavedStateHandle,
    override val navigationManager: NavigationManager,
    private val dispatcherProvider: DispatcherProvider,
    private val getSourcesCountUseCase: GetSourcesCountUseCase,
    private val getTransactionUseCase: GetTransactionUseCase,
    private val updateSourcesUseCase: UpdateSourcesUseCase,
    private val updateTransactionsUseCase: UpdateTransactionsUseCase,
) : EditTransactionScreenViewModel, ViewModel() {
    private var transactionId: Int? = null

    private var defaultSource: Source? = null
    private var expenseDefaultCategory: Category? = null
    private var incomeDefaultCategory: Category? = null

    private val transaction: MutableStateFlow<Transaction?> = MutableStateFlow(
        value = null,
    )
    private var transactionCategory: Category? = null
    private var transactionSourceFrom: Source? = null
    private var transactionSourceTo: Source? = null

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
    }.defaultListStateIn()
    override val transactionForValues: Array<TransactionFor> = TransactionFor.values()
    override val categories: StateFlow<List<Category>> = getCategoriesUseCase().defaultListStateIn()
    override val sources: StateFlow<List<Source>> = getSourcesUseCase()
        .transformLatest {
            emit(
                value = it.sortedBy { source ->
                    source.type.sortOrder
                },
            )
        }.defaultListStateIn()

    private var _uiState: MutableStateFlow<EditTransactionScreenUiState> = MutableStateFlow(
        value = EditTransactionScreenUiState(
            selectedTransactionTypeIndex = -1,
            amount = "",
            title = "",
            description = "",
            category = null,
            selectedTransactionForIndex = transactionForValues.indexOf(
                element = TransactionFor.SELF,
            ),
            sourceFrom = null,
            sourceTo = null,
            transactionCalendar = Calendar.getInstance(),
        ),
    )
    override val uiState: StateFlow<EditTransactionScreenUiState> = _uiState

    private val _uiVisibilityState: MutableStateFlow<EditTransactionScreenUiVisibilityState> =
        MutableStateFlow(
            value = EditTransactionScreenUiVisibilityState(),
        )
    override val uiVisibilityState: StateFlow<EditTransactionScreenUiVisibilityState> =
        _uiVisibilityState

    override val selectedTransactionType: StateFlow<TransactionType?> = combine(
        flow = transactionTypesForNewTransaction,
        flow2 = uiState,
    ) { transactionTypesForNewTransaction, uiState ->
        transactionTypesForNewTransaction.getOrNull(
            index = uiState.selectedTransactionTypeIndex,
        )
    }.defaultObjectStateIn()

    override val isValidTransactionData: StateFlow<Boolean> = combine(
        flow = uiState,
        flow2 = selectedTransactionType,
    ) { uiState, selectedTransactionType ->
        when (selectedTransactionType) {
            TransactionType.INCOME -> {
                uiState.amount.isNotNullOrBlank() &&
                        uiState.title.isNotNullOrBlank() &&
                        uiState.amount.toInt() != 0
            }
            TransactionType.EXPENSE -> {
                uiState.amount.isNotNullOrBlank() &&
                        uiState.title.isNotNullOrBlank() &&
                        uiState.amount.toInt() != 0
            }
            TransactionType.TRANSFER -> {
                uiState.amount.isNotNullOrBlank() &&
                        uiState.sourceFrom?.id != uiState.sourceTo?.id &&
                        uiState.amount.toInt() != 0
            }
            TransactionType.ADJUSTMENT -> {
                false
            }
            null -> {
                false
            }
        }
    }.defaultBooleanStateIn()

    private val selectedCategoryId: StateFlow<Int?> = uiState.map {
        it.category?.id
    }.defaultObjectStateIn()
    private val _titleSuggestions: MutableStateFlow<List<String>> = MutableStateFlow(
        value = emptyList(),
    )
    override val titleSuggestions: StateFlow<List<String>> = _titleSuggestions

    init {
        savedStateHandle.get<Int>(NavArgs.TRANSACTION_ID)?.let { id ->
            transactionId = id
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
                            isDefaultCategory(
                                category = category.title,
                            )
                        }
                        incomeDefaultCategory = it.firstOrNull { category ->
                            isSalaryCategory(
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
                    it?.let {
                        val uiVisibilityState: EditTransactionScreenUiVisibilityState? = when (it) {
                            TransactionType.INCOME -> {
                                EditTransactionScreenUiVisibilityState(
                                    isTitleTextFieldVisible = true,
                                    isDescriptionTextFieldVisible = false,
                                    isCategoryTextFieldVisible = true,
                                    isTransactionForRadioGroupVisible = false,
                                    isSourceFromTextFieldVisible = false,
                                    isSourceToTextFieldVisible = true,
                                )
                            }
                            TransactionType.EXPENSE -> {
                                EditTransactionScreenUiVisibilityState(
                                    isTitleTextFieldVisible = true,
                                    isDescriptionTextFieldVisible = false,
                                    isCategoryTextFieldVisible = true,
                                    isTransactionForRadioGroupVisible = true,
                                    isSourceFromTextFieldVisible = true,
                                    isSourceToTextFieldVisible = false,
                                )
                            }
                            TransactionType.TRANSFER -> {
                                EditTransactionScreenUiVisibilityState(
                                    isTitleTextFieldVisible = false,
                                    isDescriptionTextFieldVisible = false,
                                    isCategoryTextFieldVisible = false,
                                    isTransactionForRadioGroupVisible = false,
                                    isSourceFromTextFieldVisible = true,
                                    isSourceToTextFieldVisible = true,
                                )
                            }
                            TransactionType.ADJUSTMENT -> {
                                null
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
                                    if (selectedTransactionType.value == transaction.value?.transactionType) {
                                        transactionCategory ?: incomeDefaultCategory
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
                                    updatedSourceTo = transactionSourceTo ?: defaultSource,
                                )
                            }
                            TransactionType.EXPENSE -> {
                                val updatedCategory =
                                    if (selectedTransactionType.value == transaction.value?.transactionType) {
                                        transactionCategory ?: expenseDefaultCategory
                                    } else {
                                        incomeDefaultCategory
                                    }
                                updateCategory(
                                    updatedCategory = updatedCategory,
                                )

                                updateSourceFrom(
                                    updatedSourceFrom = transactionSourceFrom ?: defaultSource,
                                )
                                updateSourceTo(
                                    updatedSourceTo = null,
                                )
                            }
                            TransactionType.TRANSFER -> {
                                updateSourceFrom(
                                    updatedSourceFrom = transactionSourceFrom ?: defaultSource,
                                )
                                updateSourceTo(
                                    updatedSourceTo = transactionSourceTo ?: defaultSource,
                                )
                            }
                            TransactionType.ADJUSTMENT -> {}
                        }
                    }
                }
            }
            launch {
                combine(
                    flow = transactionTypesForNewTransaction,
                    flow2 = sources,
                    flow3 = categories,
                    flow4 = transaction,
                ) { transactionTypesForNewTransaction, sources, categories, transaction ->
                    if (transactionTypesForNewTransaction.isNotEmpty() &&
                        sources.isNotEmpty() &&
                        categories.isNotEmpty() &&
                        transaction != null
                    ) {
                        transactionCategory = getCategory(
                            categoryId = transaction.categoryId,
                        )
                        transactionSourceFrom = getSource(
                            sourceId = transaction.sourceFromId,
                        )
                        transactionSourceTo = getSource(
                            sourceId = transaction.sourceToId,
                        )
                        updateInitialTransactionValue(
                            transaction = transaction,
                        )
                    }
                }.collect()
            }
            launch {
                selectedCategoryId.collectLatest {
                    it?.let { selectedCategoryIdValue ->
                        _titleSuggestions.value = getTitleSuggestionsUseCase(
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
                    value = if (selectedTransactionTypeValue == TransactionType.EXPENSE) {
                        -1 * uiStateValue.amount.toLong()
                    } else {
                        uiStateValue.amount.toLong()
                    },
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
                }
                val title = if (selectedTransactionTypeValue == TransactionType.TRANSFER) {
                    TransactionType.TRANSFER.title
                } else {
                    uiStateValue.title.capitalizeWords()
                }
                val transactionFor: TransactionFor = when (selectedTransactionTypeValue) {
                    TransactionType.INCOME -> {
                        TransactionFor.SELF
                    }
                    TransactionType.EXPENSE -> {
                        transactionForValues[uiStateValue.selectedTransactionForIndex]
                    }
                    TransactionType.TRANSFER -> {
                        TransactionFor.SELF
                    }
                    TransactionType.ADJUSTMENT -> {
                        TransactionFor.SELF
                    }
                }

                transaction.value?.let { transaction ->
                    updateTransactionsUseCase(
                        transaction.copy(
                            amount = amount,
                            categoryId = categoryId,
                            sourceFromId = sourceFromId,
                            sourceToId = sourceToId,
                            description = uiStateValue.description,
                            title = title,
                            creationTimestamp = Calendar.getInstance().timeInMillis,
                            transactionTimestamp = uiStateValue.transactionCalendar.timeInMillis,
                            transactionFor = transactionFor,
                            transactionType = selectedTransactionTypeValue,
                        ),
                    )
                    uiStateValue.sourceFrom?.let { sourceFrom ->
                        val updatedSourceFromBalanceAmount = sourceFrom.balanceAmount.value +
                                (-1 * transaction.amount.value) - uiState.value.amount.toLong()
                        updateSourcesUseCase(
                            sourceFrom.copy(
                                balanceAmount = sourceFrom.balanceAmount.copy(
                                    value = updatedSourceFromBalanceAmount,
                                )
                            ),
                        )
                    }
                    uiStateValue.sourceTo?.let { sourceTo ->
                        val updatedSourceToBalanceAmount = sourceTo.balanceAmount.value -
                                transaction.amount.value + uiState.value.amount.toLong()
                        updateSourcesUseCase(
                            sourceTo.copy(
                                balanceAmount = sourceTo.balanceAmount.copy(
                                    value = updatedSourceToBalanceAmount,
                                )
                            ),
                        )
                    }
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
            val fetchedTransaction: Transaction? = getTransactionUseCase(
                id = id,
            )
            fetchedTransaction?.let {
                transaction.value = it
            }
        }
    }

    private fun updateInitialTransactionValue(
        transaction: Transaction,
    ) {
        val initialEditTransactionScreenUiState = EditTransactionScreenUiState(
            selectedTransactionTypeIndex = transactionTypesForNewTransaction.value.indexOf(
                element = transaction.transactionType,
            ),
            amount = abs(transaction.amount.value).toString(),
            title = transaction.title,
            description = transaction.description,
            category = transactionCategory,
            selectedTransactionForIndex = transactionForValues.indexOf(
                element = transaction.transactionFor,
            ),
            sourceFrom = transactionSourceFrom,
            sourceTo = transactionSourceTo,
            transactionCalendar = Calendar.getInstance().apply {
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
        _uiState.value = updatedEditTransactionScreenUiState
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

    private fun Flow<Boolean>.defaultBooleanStateIn(): StateFlow<Boolean> {
        return this.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = false,
        )
    }

    private fun <T> Flow<List<T>>.defaultListStateIn(): StateFlow<List<T>> {
        return this.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList(),
        )
    }

    private fun <T> Flow<T>.defaultObjectStateIn(): StateFlow<T?> {
        return this.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = null,
        )
    }
}
