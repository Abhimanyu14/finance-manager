package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.defaultBooleanStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.defaultListStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.capitalizeWords
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.filterDigits
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNullOrBlank
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotZero
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.toEpochMilli
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.Quadruple
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.getCurrentLocalDate
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.getCurrentLocalTime
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.getCurrentTimeMillis
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.getLocalDate
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.getLocalTime
import com.makeappssimple.abhimanyu.financemanager.android.core.database.amount.model.Amount
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.GetAllCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.sortOrder
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetAllSourcesCountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetAllSourcesUseCase
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
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultInvestmentCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.abs
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

@HiltViewModel
internal class AddOrEditTransactionScreenViewModelImpl @Inject constructor(
    savedStateHandle: SavedStateHandle,
    override val navigationManager: NavigationManager,
    private val dataStore: MyDataStore,
    private val dispatcherProvider: DispatcherProvider,
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    private val getAllSourcesCountUseCase: GetAllSourcesCountUseCase,
    private val getAllSourcesUseCase: GetAllSourcesUseCase,
    private val getAllTransactionForValuesUseCase: GetAllTransactionForValuesUseCase,
    private val getTitleSuggestionsUseCase: GetTitleSuggestionsUseCase,
    private val getTransactionDataUseCase: GetTransactionDataUseCase,
    private val insertTransactionUseCase: InsertTransactionUseCase,
    private val updateSourcesBalanceAmountUseCase: UpdateSourcesBalanceAmountUseCase,
    private val updateTransactionUseCase: UpdateTransactionUseCase,
) : AddOrEditTransactionScreenViewModel, ViewModel() {
    // Navigation parameters
    private var isEdit: Boolean? = null

    // We would have value for all editing as well as adding refund transaction
    private var originalTransactionId: Int? = null

    // Original transaction data
    private var originalTransactionData: MutableStateFlow<TransactionData?> = MutableStateFlow(
        value = null,
    )
    private var maxRefundAmount: MutableStateFlow<Amount?> = MutableStateFlow(
        value = null,
    )

    // region Default data
    // Default data from data store
    private var defaultSource: Source? = null
    private var expenseDefaultCategory: Category? = null
    private var incomeDefaultCategory: Category? = null
    private var investmentDefaultCategory: Category? = null

    // Default data from data store
    private var defaultSourceIdFromDataStore: Int? = null
    private var defaultExpenseCategoryIdFromDataStore: Int? = null
    private var defaultIncomeCategoryIdFromDataStore: Int? = null
    private var defaultInvestmentCategoryIdFromDataStore: Int? = null
    // endregion

    // region Data source
    private var _transactionTypesForNewTransaction: MutableStateFlow<List<TransactionType>> =
        MutableStateFlow(
            value = emptyList(),
        )
    override val transactionTypesForNewTransaction: StateFlow<List<TransactionType>> =
        _transactionTypesForNewTransaction

    private var _categories: MutableStateFlow<List<Category>> = MutableStateFlow(
        value = emptyList(),
    )
    private val categories: StateFlow<List<Category>> = _categories

    private val _titleSuggestions: MutableStateFlow<List<String>> = MutableStateFlow(
        value = emptyList(),
    )
    override val titleSuggestions: StateFlow<List<String>> = _titleSuggestions

    private var _transactionForValues: MutableStateFlow<List<TransactionFor>> = MutableStateFlow(
        value = emptyList(),
    )
    override val transactionForValues: StateFlow<List<TransactionFor>> = _transactionForValues

    private var _sources: MutableStateFlow<List<Source>> = MutableStateFlow(
        value = emptyList(),
    )
    override val sources: StateFlow<List<Source>> = _sources
    // endregion

    private val _uiState: MutableStateFlow<AddOrEditTransactionScreenUiState> = MutableStateFlow(
        value = AddOrEditTransactionScreenUiState(
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
            transactionDate = getCurrentLocalDate(),
            transactionTime = getCurrentLocalTime(),
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

    private val selectedCategoryId: StateFlow<Int?> = uiState.map {
        it.category?.id
    }.defaultObjectStateIn(
        scope = viewModelScope,
    )

    override val isCtaButtonEnabled: StateFlow<Boolean> = combine(
        flow = uiState,
        flow2 = selectedTransactionType,
    ) { uiState, selectedTransactionType ->
        when (selectedTransactionType) {
            TransactionType.INCOME -> {
                uiState.amount.text.isNotNullOrBlank() &&
                        uiState.title.text.isNotNullOrBlank() &&
                        uiState.amount.text.toInt().isNotZero() &&
                        uiState.amountErrorText.isNull()
            }

            TransactionType.EXPENSE -> {
                uiState.amount.text.isNotNullOrBlank() &&
                        uiState.title.text.isNotNullOrBlank() &&
                        uiState.amount.text.toInt().isNotZero() &&
                        uiState.amountErrorText.isNull()
            }

            TransactionType.TRANSFER -> {
                uiState.amount.text.isNotNullOrBlank() &&
                        uiState.sourceFrom?.id != uiState.sourceTo?.id &&
                        uiState.amount.text.toInt().isNotZero() &&
                        uiState.amountErrorText.isNull()
            }

            TransactionType.ADJUSTMENT -> {
                false
            }

            TransactionType.INVESTMENT -> {
                uiState.amount.text.isNotNullOrBlank() &&
                        uiState.title.text.isNotNullOrBlank() &&
                        uiState.amount.text.toInt().isNotZero() &&
                        uiState.amountErrorText.isNull()
            }

            TransactionType.REFUND -> {
                val maxRefundAmountValue = maxRefundAmount.value?.value ?: 0L
                if (uiState.amountErrorText == null &&
                    ((uiState.amount.text.toLongOrNull() ?: 0L) > maxRefundAmountValue)
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
                    ((uiState.amount.text.toLongOrNull() ?: 0L) <= maxRefundAmountValue)
                ) {
                    updateAddOrEditTransactionScreenUiState(
                        updatedAddOrEditTransactionScreenUiState = uiState.copy(
                            amountErrorText = null,
                        )
                    )
                    false
                } else {
                    uiState.amount.text.isNotNullOrBlank() &&
                            uiState.title.text.isNotNullOrBlank() &&
                            uiState.amount.text.toInt().isNotZero() &&
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


    init {
        getNavigationArguments(
            savedStateHandle = savedStateHandle,
        )
        fetchData()
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
                val amountValue = uiStateValue.amount.text.toLong()
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
                val originalTransactionId = if (isEdit == false && originalTransactionId != null ||
                    isEdit == true && originalTransactionData.value?.transaction?.transactionType == TransactionType.REFUND
                ) {
                    originalTransactionId
                } else {
                    null
                }

                val transactionTimestamp = LocalDateTime
                    .of(uiStateValue.transactionDate, uiStateValue.transactionTime)
                    .toEpochMilli()
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
                        description = uiStateValue.description.text,
                        title = title,
                        creationTimestamp = getCurrentTimeMillis(),
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
                    value = uiStateValue.amount.text.toLong(),
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
                            creationTimestamp = getCurrentTimeMillis(),
                            transactionTimestamp = transactionTimestamp,
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
                                ?: 0) - uiState.value.amount.text.toLong()
                    }
                    originalTransactionData.value?.sourceTo?.let { transactionSourceTo ->
                        sourceBalanceAmountChangeMap[transactionSourceTo.id] =
                            (sourceBalanceAmountChangeMap[transactionSourceTo.id]
                                ?: 0) - transaction.amount.value
                    }
                    uiStateValue.sourceTo?.let { sourceTo ->
                        sourceBalanceAmountChangeMap[sourceTo.id] =
                            (sourceBalanceAmountChangeMap[sourceTo.id]
                                ?: 0) + uiState.value.amount.text.toLong()
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
            updatedAddOrEditTransactionScreenUiState = uiState.value.copy(
                selectedTransactionTypeIndex = updatedSelectedTransactionTypeIndex,
            ),
        )
        _selectedTransactionType.value = transactionTypesForNewTransaction.value.getOrNull(
            index = updatedSelectedTransactionTypeIndex,
        )
    }

    override fun updateAmount(
        updatedAmount: TextFieldValue,
    ) {
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiState = uiState.value.copy(
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

    override fun updateTitle(
        updatedTitle: TextFieldValue,
    ) {
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiState = uiState.value.copy(
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
            updatedAddOrEditTransactionScreenUiState = uiState.value.copy(
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
            updatedAddOrEditTransactionScreenUiState = uiState.value.copy(
                category = updatedCategory,
            ),
        )
    }

    override fun updateSelectedTransactionForIndex(
        updatedSelectedTransactionForIndex: Int,
    ) {
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiState = uiState.value.copy(
                selectedTransactionForIndex = updatedSelectedTransactionForIndex,
            ),
        )
    }

    override fun updateSourceFrom(
        updatedSourceFrom: Source?,
    ) {
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiState = uiState.value.copy(
                sourceFrom = updatedSourceFrom,
            ),
        )
    }

    override fun updateSourceTo(
        updatedSourceTo: Source?,
    ) {
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiState = uiState.value.copy(
                sourceTo = updatedSourceTo,
            ),
        )
    }

    override fun updateTransactionDate(
        updatedTransactionDate: LocalDate,
    ) {
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiState = _uiState.value.copy(
                transactionDate = updatedTransactionDate,
            ),
        )
    }

    override fun updateTransactionTime(
        updatedTransactionTime: LocalTime,
    ) {
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiState = _uiState.value.copy(
                transactionTime = updatedTransactionTime,
            ),
        )
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
    // endregion

    private fun getNavigationArguments(
        savedStateHandle: SavedStateHandle,
    ) {
        savedStateHandle.get<String>(NavArgs.TRANSACTION_ID)?.let {
            originalTransactionId = it.toIntOrNull()
        }
        savedStateHandle.get<Boolean>(NavArgs.EDIT)?.let {
            isEdit = it
        }
    }

    private fun fetchData() {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            // Initial data setup

            // Default data from data store
            awaitAll(
                async {
                    defaultSourceIdFromDataStore =
                        dataStore.getDefaultSourceIdFromDataStore().first()
                },
                async {
                    defaultExpenseCategoryIdFromDataStore =
                        dataStore.getDefaultExpenseCategoryIdFromDataStore().first()
                },
                async {
                    defaultIncomeCategoryIdFromDataStore =
                        dataStore.getDefaultIncomeCategoryIdFromDataStore().first()
                },
                async {
                    defaultInvestmentCategoryIdFromDataStore =
                        dataStore.getDefaultInvestmentCategoryIdFromDataStore().first()
                },
                async {
                    _categories.value = getAllCategoriesUseCase()
                },
                async {
                    _sources.value = getAllSourcesUseCase().sortedBy { source ->
                        source.type.sortOrder
                    }
                },
                async {
                    _transactionForValues.value = getAllTransactionForValuesUseCase()
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
                            if (transactionType == originalTransactionData.value?.transaction?.transactionType) {
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
                            if (transactionType == originalTransactionData.value?.transaction?.transactionType) {
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
    }

    private fun observeSelectedCategory(
        coroutineScope: CoroutineScope,
    ) {
        coroutineScope.launch {
            selectedCategoryId.collectLatest { selectedCategoryIdValue ->
                selectedCategoryIdValue ?: return@collectLatest
                _titleSuggestions.update {
                    getTitleSuggestionsUseCase(
                        categoryId = selectedCategoryIdValue,
                    )
                }
            }
        }
    }

    private suspend fun getTransactionTypesForNewTransaction() {
        val sourceCount = getAllSourcesCountUseCase()
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

        if (isEdit == false && originalTransactionId == null) {
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
        originalTransactionId?.let { id ->
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
                    transactionTypesForNewTransaction = transactionTypesForNewTransaction,
                    transactionForValues = transactionForValues,
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
        val isAddingRefund = isEdit == false && originalTransactionId != null
        val initialAddOrEditTransactionScreenUiState = if (isAddingRefund) {
            AddOrEditTransactionScreenUiState(
                selectedTransactionTypeIndex = transactionTypesForNewTransaction.indexOf(
                    element = TransactionType.REFUND,
                ),
                amount = uiState.value.amount.copy(
                    text = (maxRefundAmount?.value ?: 0L).toString(),
                    selection = TextRange((maxRefundAmount?.value ?: 0L).toString().length),
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
                transactionDate = getCurrentLocalDate(),
                transactionTime = getCurrentLocalTime(),
            )
        } else {
            AddOrEditTransactionScreenUiState(
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
                transactionDate = getLocalDate(
                    timestamp = originalTransaction.transactionTimestamp,
                ),
                transactionTime = getLocalTime(
                    timestamp = originalTransaction.transactionTimestamp,
                ),
            )
        }
        updateAddOrEditTransactionScreenUiState(
            updatedAddOrEditTransactionScreenUiState = initialAddOrEditTransactionScreenUiState,
        )
    }

    private suspend fun calculateMaxRefundAmount() {
        val transactionId = originalTransactionId ?: return
        val transactionData = getTransactionDataUseCase(
            id = transactionId,
        )
        if (!((isEdit == true && transactionData?.transaction?.transactionType == TransactionType.REFUND) || isEdit == false)) {
            return
        }

        var transactionDataToRefund: TransactionData? = null
        if (isEdit == true) {
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

    private fun setDefaultCategory() {
        expenseDefaultCategory = getCategory(
            categoryId = defaultExpenseCategoryIdFromDataStore,
        ) ?: categories.value.firstOrNull { category ->
            isDefaultExpenseCategory(
                category = category.title,
            )
        }
        incomeDefaultCategory = getCategory(
            categoryId = defaultIncomeCategoryIdFromDataStore,
        ) ?: categories.value.firstOrNull { category ->
            isDefaultIncomeCategory(
                category = category.title,
            )
        }
        investmentDefaultCategory = getCategory(
            categoryId = defaultInvestmentCategoryIdFromDataStore,
        ) ?: categories.value.firstOrNull { category ->
            isDefaultInvestmentCategory(
                category = category.title,
            )
        }
        if (isEdit != true) {
            updateCategory(
                updatedCategory = expenseDefaultCategory,
            )
        }
    }

    private fun setDefaultSource() {
        defaultSource = getSource(
            sourceId = defaultSourceIdFromDataStore,
        ) ?: sources.value.firstOrNull { source ->
            isDefaultSource(
                source = source.name,
            )
        }
        if (isEdit != true) {
            updateSourceFrom(
                updatedSourceFrom = defaultSource,
            )
            updateSourceTo(
                updatedSourceTo = defaultSource,
            )
        }
    }

    private fun setInitialSelectedTransactionType() {
        _selectedTransactionType.value = if (isEdit == true) {
            if (originalTransactionData.value?.transaction?.transactionType == TransactionType.REFUND) {
                TransactionType.REFUND
            } else {
                uiState.value.selectedTransactionTypeIndex?.let {
                    transactionTypesForNewTransaction.value.getOrNull(
                        index = uiState.value.selectedTransactionTypeIndex ?: 0,
                    )
                }
            }
        } else {
            if (originalTransactionId != null) {
                TransactionType.REFUND
            } else {
                uiState.value.selectedTransactionTypeIndex?.let {
                    transactionTypesForNewTransaction.value.getOrNull(
                        index = uiState.value.selectedTransactionTypeIndex ?: 0,
                    )
                }
            }
        }
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
