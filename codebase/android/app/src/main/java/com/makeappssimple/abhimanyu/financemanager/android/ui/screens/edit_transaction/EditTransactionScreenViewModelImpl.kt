package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_transaction

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.GetCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.GetSourcesCountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.GetSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.UpdateSourcesUseCase
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
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.isNotNullOrBlank
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.abs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.launch
import java.util.Calendar

data class EditTransactionScreenUiState(
    val selectedTransactionTypeIndex: Int,
    val amount: String,
    val title: String,
    val description: String,
    val category: Category?,
    val selectedTransactionForIndex: Int,
    val sourceFrom: Source?,
    val sourceTo: Source?,
    val transactionCalendar: Calendar,
)

@HiltViewModel
class EditTransactionScreenViewModelImpl @Inject constructor(
    getCategoriesUseCase: GetCategoriesUseCase,
    getSourcesUseCase: GetSourcesUseCase,
    savedStateHandle: SavedStateHandle,
    override val navigationManager: NavigationManager,
    private val dispatcherProvider: DispatcherProvider,
    private val getSourcesCountUseCase: GetSourcesCountUseCase,
    private val getTransactionUseCase: GetTransactionUseCase,
    private val updateSourcesUseCase: UpdateSourcesUseCase,
    private val updateTransactionsUseCase: UpdateTransactionsUseCase,
) : EditTransactionScreenViewModel, ViewModel() {
    private val transaction: MutableStateFlow<Transaction?> = MutableStateFlow(
        value = null,
    )

    override val transactionTypesForNewTransaction: StateFlow<List<TransactionType>> = flow {
        val transactionTypesForNewTransaction = if (getSourcesCountUseCase() > 1) {
            TransactionType.values().filter {
                it != TransactionType.ADJUSTMENT
            }
        } else {
            TransactionType.values().filter {
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
            selectedTransactionTypeIndex = transactionTypesForNewTransaction.value.indexOf(
                element = TransactionType.EXPENSE,
            ),
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

    override val selectedTransactionType: StateFlow<TransactionType?> = combine(
        flow = transactionTypesForNewTransaction,
        flow2 = uiState,
    ) { transactionTypesForNewTransaction, uiState ->
        transactionTypesForNewTransaction.getOrNull(
            index = uiState.selectedTransactionTypeIndex,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = null,
    )

    override val isValidTransactionData: StateFlow<Boolean> = combine(
        uiState,
        selectedTransactionType,
    ) { uiState, selectedTransactionType ->
        when (selectedTransactionType) {
            TransactionType.INCOME -> {
                uiState.amount.isNotNullOrBlank() &&
                        uiState.title.isNotNullOrBlank()
            }
            TransactionType.EXPENSE -> {
                uiState.amount.isNotNullOrBlank() &&
                        uiState.title.isNotNullOrBlank()
            }
            TransactionType.TRANSFER -> {
                uiState.amount.isNotNullOrBlank() &&
                        uiState.sourceFrom?.id != uiState.sourceTo?.id
            }
            TransactionType.ADJUSTMENT -> {
                false
            }
            null -> {
                false
            }
        }
    }.defaultBooleanStateIn()

    override val isTitleTextFieldVisible: StateFlow<Boolean> = selectedTransactionType
        .map {
            when (it) {
                TransactionType.INCOME -> {
                    true
                }
                TransactionType.EXPENSE -> {
                    true
                }
                TransactionType.TRANSFER -> {
                    false
                }
                TransactionType.ADJUSTMENT -> {
                    false
                }
                null -> {
                    false
                }
            }
        }
        .defaultBooleanStateIn()

    override val isDescriptionTextFieldVisible: StateFlow<Boolean> = selectedTransactionType
        .map {
            false
        }
        .defaultBooleanStateIn()

    override val isCategoryTextFieldVisible: StateFlow<Boolean> = selectedTransactionType
        .map {
            when (it) {
                TransactionType.INCOME -> {
                    true
                }
                TransactionType.EXPENSE -> {
                    true
                }
                TransactionType.TRANSFER -> {
                    false
                }
                TransactionType.ADJUSTMENT -> {
                    false
                }
                null -> {
                    false
                }
            }
        }
        .defaultBooleanStateIn()

    override val isTransactionForRadioGroupVisible: StateFlow<Boolean> = selectedTransactionType
        .map {
            when (it) {
                TransactionType.INCOME -> {
                    false
                }
                TransactionType.EXPENSE -> {
                    true
                }
                TransactionType.TRANSFER -> {
                    false
                }
                TransactionType.ADJUSTMENT -> {
                    false
                }
                null -> {
                    false
                }
            }
        }
        .defaultBooleanStateIn()

    override val isSourceFromTextFieldVisible: StateFlow<Boolean> = selectedTransactionType
        .map {
            when (it) {
                TransactionType.INCOME -> {
                    false
                }
                TransactionType.EXPENSE -> {
                    true
                }
                TransactionType.TRANSFER -> {
                    true
                }
                TransactionType.ADJUSTMENT -> {
                    false
                }
                null -> {
                    false
                }
            }
        }
        .defaultBooleanStateIn()

    override val isSourceToTextFieldVisible: StateFlow<Boolean> = selectedTransactionType
        .map {
            when (it) {
                TransactionType.INCOME -> {
                    true
                }
                TransactionType.EXPENSE -> {
                    false
                }
                TransactionType.TRANSFER -> {
                    true
                }
                TransactionType.ADJUSTMENT -> {
                    false
                }
                null -> {
                    false
                }
            }
        }
        .defaultBooleanStateIn()

    init {
        val transactionId: Int = savedStateHandle.get<Int>(NavArgs.TRANSACTION_ID) ?: 0
        getTransaction(
            id = transactionId,
        )
    }

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }

    override fun insertTransaction() {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            // TODO-Abhi: Change insert transaction code to update transaction code

            selectedTransactionType.value?.let { selectedTransactionTypeValue ->
                updateTransactionsUseCase(
                    Transaction(
                        amount = Amount(
                            value = if (selectedTransactionTypeValue == TransactionType.EXPENSE) {
                                -1 * uiState.value.amount.toLong()
                            } else {
                                uiState.value.amount.toLong()
                            },
                        ),
                        categoryId = _uiState.value.category?.id ?: 0,
                        sourceFromId = _uiState.value.sourceFrom?.id ?: 0,
                        sourceToId = _uiState.value.sourceTo?.id ?: 0,
                        description = _uiState.value.description,
                        title = if (selectedTransactionTypeValue == TransactionType.TRANSFER) {
                            TransactionType.TRANSFER.title
                        } else {
                            uiState.value.title
                        },
                        creationTimestamp = Calendar.getInstance().timeInMillis,
                        transactionTimestamp = _uiState.value.transactionCalendar.timeInMillis,
                        transactionFor = when (selectedTransactionTypeValue) {
                            TransactionType.INCOME -> {
                                TransactionFor.SELF
                            }
                            TransactionType.EXPENSE -> {
                                transactionForValues[_uiState.value.selectedTransactionForIndex]
                            }
                            TransactionType.TRANSFER -> {
                                TransactionFor.SELF
                            }
                            TransactionType.ADJUSTMENT -> {
                                TransactionFor.SELF
                            }
                        },
                        transactionType = selectedTransactionTypeValue,
                    ),
                )
                // TODO-Abhi: Handle source amount refund and deduction appropriately
                _uiState.value.sourceFrom?.let { sourceFrom ->
                    updateSourcesUseCase(
                        sourceFrom.copy(
                            balanceAmount = sourceFrom.balanceAmount.copy(
                                value = sourceFrom.balanceAmount.value - uiState.value.amount.toLong(),
                            )
                        ),
                    )
                }
                _uiState.value.sourceTo?.let { sourceTo ->
                    updateSourcesUseCase(
                        sourceTo.copy(
                            balanceAmount = sourceTo.balanceAmount.copy(
                                value = sourceTo.balanceAmount.value + uiState.value.amount.toLong(),
                            )
                        ),
                    )
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
                updateInitialTransactionValue(
                    transaction = it,
                )
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
            category = categories.value.find { category ->
                category.id == transaction.categoryId
            },
            selectedTransactionForIndex = transactionForValues.indexOf(
                element = transaction.transactionFor,
            ),
            sourceFrom = sources.value.find { source ->
                source.id == transaction.sourceFromId
            },
            sourceTo = sources.value.find { source ->
                source.id == transaction.sourceToId
            },
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

    private fun <T> Flow<List<T>>.defaultListStateIn(): StateFlow<List<T>> {
        return this.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList(),
        )
    }

    private fun Flow<Boolean>.defaultBooleanStateIn(): StateFlow<Boolean> {
        return this.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = false,
        )
    }
}
