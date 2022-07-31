package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.database.amount.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.GetCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.datastore.MyDataStore
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.sortOrder
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourcesCountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.UpdateSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetTitleSuggestionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.InsertTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.utils.extensions.capitalizeWords
import com.makeappssimple.abhimanyu.financemanager.android.core.database.utils.extensions.isNotNullOrBlank
import com.makeappssimple.abhimanyu.financemanager.android.core.database.utils.extensions.isNotZero
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.utils.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.utils.isCashSource
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.utils.isDefaultCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.utils.isSalaryCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale

@HiltViewModel
internal class AddTransactionScreenViewModelImpl @Inject constructor(
    dataStore: MyDataStore,
    getCategoriesUseCase: GetCategoriesUseCase,
    getSourcesUseCase: GetSourcesUseCase,
    getTitleSuggestionsUseCase: GetTitleSuggestionsUseCase,
    override val navigationManager: NavigationManager,
    private val dispatcherProvider: DispatcherProvider,
    private val getSourcesCountUseCase: GetSourcesCountUseCase,
    private val insertTransactionUseCase: InsertTransactionUseCase,
    private val updateSourcesUseCase: UpdateSourcesUseCase,
) : AddTransactionScreenViewModel, ViewModel() {
    private var defaultSource: Source? = null
    private var expenseDefaultCategory: Category? = null
    private var incomeDefaultCategory: Category? = null

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

    private val _uiState: MutableStateFlow<AddTransactionScreenUiState> = MutableStateFlow(
        value = AddTransactionScreenUiState(
            selectedTransactionTypeIndex = null,
            amount = "",
            title = "",
            description = "",
            category = null,
            selectedTransactionForIndex = transactionForValues.indexOf(
                element = TransactionFor.SELF,
            ),
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
    }.defaultObjectStateIn()

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

    private val defaultSourceIdFromDataStore: StateFlow<Int?> = dataStore
        .getDefaultSourceIdFromDataStore()
        .defaultObjectStateIn()
    private val defaultIncomeCategoryIdFromDataStore: StateFlow<Int?> = dataStore
        .getDefaultIncomeCategoryIdFromDataStore()
        .defaultObjectStateIn()
    private val defaultExpenseCategoryIdFromDataStore: StateFlow<Int?> = dataStore
        .getDefaultExpenseCategoryIdFromDataStore()
        .defaultObjectStateIn()

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
                categories.collectLatest {
                    expenseDefaultCategory = getCategory(
                        categoryId = defaultExpenseCategoryIdFromDataStore.value,
                    ) ?: it.firstOrNull { category ->
                        isDefaultCategory(
                            category = category.title,
                        )
                    }
                    incomeDefaultCategory = getCategory(
                        categoryId = defaultIncomeCategoryIdFromDataStore.value,
                    ) ?: it.firstOrNull { category ->
                        isSalaryCategory(
                            category = category.title,
                        )
                    }
                    updateCategory(
                        updatedCategory = expenseDefaultCategory,
                    )
                }
            }
            launch {
                sources.collectLatest {
                    defaultSource = getSource(
                        sourceId = defaultSourceIdFromDataStore.value,
                    ) ?: it.firstOrNull { source ->
                        isCashSource(
                            source = source.name,
                        )
                    }
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
                        null -> {}
                    }
                }
            }
            launch {
                selectedCategoryId.collectLatest {
                    val selectedCategoryIdValue = it ?: return@collectLatest
                    _titleSuggestions.value = getTitleSuggestionsUseCase(
                        categoryId = selectedCategoryIdValue,
                    )
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
                // TODO-Abhi: Amount sign change
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

                insertTransactionUseCase(
                    transaction = Transaction(
                        amount = amount,
                        categoryId = categoryId,
                        sourceFromId = sourceFromId,
                        sourceToId = sourceToId,
                        description = uiStateValue.description,
                        title = title,
                        creationTimestamp = System.currentTimeMillis(),
                        transactionTimestamp = uiStateValue.transactionCalendar.timeInMillis,
                        transactionFor = transactionFor,
                        transactionType = selectedTransactionTypeValue,
                    ),
                )
                uiStateValue.sourceFrom?.let { sourceFrom ->
                    updateSourcesUseCase(
                        sourceFrom.copy(
                            balanceAmount = sourceFrom.balanceAmount.copy(
                                value = sourceFrom.balanceAmount.value - uiStateValue.amount.toLong(),
                            )
                        ),
                    )
                }
                uiStateValue.sourceTo?.let { sourceTo ->
                    updateSourcesUseCase(
                        sourceTo.copy(
                            balanceAmount = sourceTo.balanceAmount.copy(
                                value = sourceTo.balanceAmount.value + uiStateValue.amount.toLong(),
                            )
                        ),
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
        _uiState.value = updatedAddTransactionScreenUiState
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
