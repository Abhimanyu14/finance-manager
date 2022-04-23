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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class EditTransactionScreenViewModelImpl @Inject constructor(
    getCategoriesUseCase: GetCategoriesUseCase,
    getSourcesUseCase: GetSourcesUseCase,
    savedStateHandle: SavedStateHandle,
    override val navigationManager: NavigationManager,
    private val dispatcherProvider: DispatcherProvider,
    private val getSourcesCountUseCase: GetSourcesCountUseCase,
    private val getTransactionUseCase: GetTransactionUseCase,
    private val updateTransactionsUseCase: UpdateTransactionsUseCase,
    private val updateSourcesUseCase: UpdateSourcesUseCase,
) : EditTransactionScreenViewModel, ViewModel() {
    private val transaction: MutableStateFlow<Transaction?> = MutableStateFlow(
        value = null,
    )
    override val transactionForValues: Array<TransactionFor> = TransactionFor.values()
    override val transactionTypes: Array<TransactionType> = TransactionType.values()
    override val categories: StateFlow<List<Category>> = getCategoriesUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList(),
    )
    override val sources: StateFlow<List<Source>> = flow {
        getSourcesUseCase().collectIndexed { _, value ->
            //            expenseDefaultSource = value.firstOrNull {
            //                it.name.contains(
            //                    other = "Cash",
            //                    ignoreCase = true,
            //                )
            //            }
            //            incomeDefaultSource = expenseDefaultSource
            //            _sourceFrom.value = expenseDefaultSource
            emit(
                value = value.sortedWith(
                    comparator = compareBy {
                        it.type.sortOrder
                    }
                ),
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList(),
    )

    private var _transactionCalendar = MutableStateFlow(
        value = Calendar.getInstance(),
    )
    override var transactionCalendar: StateFlow<Calendar> = _transactionCalendar

    private var _selectedTransactionForIndex: MutableStateFlow<Int> = MutableStateFlow(
        value = transactionForValues.indexOf(
            element = TransactionFor.SELF,
        ),
    )
    override val selectedTransactionForIndex: StateFlow<Int> = _selectedTransactionForIndex

    private var _category: MutableStateFlow<Category?> = MutableStateFlow(
        value = null,
    )
    override val category: StateFlow<Category?> = _category

    private var _sourceFrom: MutableStateFlow<Source?> = MutableStateFlow(
        value = null,
    )
    override val sourceFrom: StateFlow<Source?> = _sourceFrom

    private var _sourceTo: MutableStateFlow<Source?> = MutableStateFlow(
        value = null,
    )
    override val sourceTo: StateFlow<Source?> = _sourceTo


    private var _amount: MutableStateFlow<String> = MutableStateFlow(
        value = "",
    )
    override val amount: StateFlow<String> = _amount

    private var _selectedTransactionTypeIndex: MutableStateFlow<Int> = MutableStateFlow(
        value = transactionTypes.indexOf(
            element = TransactionType.EXPENSE,
        ),
    )
    override val selectedTransactionTypeIndex: StateFlow<Int> = _selectedTransactionTypeIndex

    private var _title: MutableStateFlow<String> = MutableStateFlow(
        value = "",
    )
    override val title: StateFlow<String> = _title

    private var _description: MutableStateFlow<String> = MutableStateFlow(
        value = "",
    )
    override val description: StateFlow<String> = _description

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
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList(),
    )

    override val isValidTransactionData: StateFlow<Boolean> = combine(
        flow = selectedTransactionTypeIndex,
        flow2 = amount,
        flow3 = title,
        flow4 = sourceFrom,
        flow5 = sourceTo,
    ) { selectedTransactionTypeIndex, amount, title, sourceFrom, sourceTo ->
        when (transactionTypes[selectedTransactionTypeIndex]) {
            TransactionType.INCOME -> {
                amount.isNotNullOrBlank() && title.isNotNullOrBlank()
            }
            TransactionType.EXPENSE -> {
                amount.isNotNullOrBlank() && title.isNotNullOrBlank()
            }
            TransactionType.TRANSFER -> {
                amount.isNotNullOrBlank() && sourceFrom?.id != sourceTo?.id
            }
            TransactionType.ADJUSTMENT -> {
                false
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = false,
    )

    init {
        val transactionId: Int = savedStateHandle.get<Int>(NavArgs.TRANSACTION_ID) ?: 0
        getTransaction(
            id = transactionId,
        )
    }

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }

    override fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    ) {
        _selectedTransactionTypeIndex.value = updatedSelectedTransactionTypeIndex
        //        when (transactionTypes[selectedTransactionTypeIndex.value]) {
        //            TransactionType.INCOME -> {
        //                _sourceFrom.value = null
        //                _sourceTo.value = incomeDefaultSource
        //                _category.value = _incomeDefaultCategory
        //            }
        //            TransactionType.EXPENSE -> {
        //                _sourceFrom.value = expenseDefaultSource
        //                _sourceTo.value = null
        //                _category.value = _expenseDefaultCategory
        //            }
        //            TransactionType.TRANSFER -> {
        //                _sourceFrom.value = expenseDefaultSource
        //                _sourceTo.value = incomeDefaultSource
        //            }
        //            TransactionType.ADJUSTMENT -> {}
        //        }
    }

    override fun insertTransaction() {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            updateTransactionsUseCase(
                Transaction(
                    amount = Amount(
                        value = if (transactionTypes[selectedTransactionTypeIndex.value] == TransactionType.EXPENSE) {
                            -1 * amount.value.toLong()
                        } else {
                            amount.value.toLong()
                        },
                    ),
                    categoryId = category.value?.id ?: 0,
                    sourceFromId = sourceFrom.value?.id ?: 0,
                    sourceToId = sourceTo.value?.id ?: 0,
                    description = description.value,
                    title = if (transactionTypes[selectedTransactionTypeIndex.value] == TransactionType.TRANSFER) {
                        TransactionType.TRANSFER.title
                    } else {
                        title.value
                    },
                    creationTimestamp = Calendar.getInstance().timeInMillis,
                    transactionTimestamp = transactionCalendar.value.timeInMillis,
                    transactionFor = when (transactionTypes[selectedTransactionTypeIndex.value]) {
                        TransactionType.INCOME -> {
                            TransactionFor.SELF
                        }
                        TransactionType.EXPENSE -> {
                            transactionForValues[selectedTransactionForIndex.value]
                        }
                        TransactionType.TRANSFER -> {
                            TransactionFor.SELF
                        }
                        TransactionType.ADJUSTMENT -> {
                            TransactionFor.SELF
                        }
                    },
                    transactionType = transactionTypes[selectedTransactionTypeIndex.value],
                ),
            )
            sourceFrom.value?.let { sourceFrom ->
                updateSourcesUseCase(
                    sourceFrom.copy(
                        balanceAmount = sourceFrom.balanceAmount.copy(
                            value = sourceFrom.balanceAmount.value - amount.value.toLong(),
                        )
                    ),
                )
            }
            sourceTo.value?.let { sourceTo ->
                updateSourcesUseCase(
                    sourceTo.copy(
                        balanceAmount = sourceTo.balanceAmount.copy(
                            value = sourceTo.balanceAmount.value + amount.value.toLong(),
                        )
                    ),
                )
            }
            navigateUp(
                navigationManager = navigationManager,
            )
        }
    }

    override fun isTitleTextFieldVisible(): Boolean {
        return when (transactionTypes[selectedTransactionTypeIndex.value]) {
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
        }
    }

    override fun isDescriptionTextFieldVisible(): Boolean {
        return false
    }

    override fun isCategoryTextFieldVisible(): Boolean {
        return when (transactionTypes[selectedTransactionTypeIndex.value]) {
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
        }
    }

    override fun isTransactionForRadioGroupVisible(): Boolean {
        return when (transactionTypes[selectedTransactionTypeIndex.value]) {
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
        }
    }

    override fun isSourceFromTextFieldVisible(): Boolean {
        return when (transactionTypes[selectedTransactionTypeIndex.value]) {
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
        }
    }

    override fun isSourceToTextFieldVisible(): Boolean {
        return when (transactionTypes[selectedTransactionTypeIndex.value]) {
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
        }
    }

    override fun updateTitle(
        updatedTitle: String,
    ) {
        _title.value = updatedTitle
    }

    override fun clearTitle() {
        _title.value = ""
    }

    override fun updateDescription(
        updatedDescription: String,
    ) {
        _description.value = updatedDescription
    }

    override fun clearDescription() {
        _description.value = ""
    }

    override fun updateAmount(
        updatedAmount: String,
    ) {
        _amount.value = updatedAmount
    }

    override fun clearAmount() {
        _amount.value = ""
    }

    override fun updateSourceFrom(
        updatedSourceFrom: Source,
    ) {
        _sourceFrom.value = updatedSourceFrom
    }

    override fun updateSourceTo(
        updatedSourceTo: Source,
    ) {
        _sourceTo.value = updatedSourceTo
    }

    override fun updateCategory(
        updatedCategory: Category?,
    ) {
        _category.value = updatedCategory
    }

    override fun updateSelectedTransactionForIndex(
        updatedSelectedTransactionForIndex: Int,
    ) {
        _selectedTransactionForIndex.value = updatedSelectedTransactionForIndex
    }

    override fun updateTransactionCalendar(
        updatedTransactionCalendar: Calendar,
    ) {
        _transactionCalendar.value = updatedTransactionCalendar
    }

    private fun getTransaction(
        id: Int,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            transaction.value = getTransactionUseCase(
                id = id,
            )
            updateInitialTransactionValue()
        }
    }

    private fun updateInitialTransactionValue() {
        transaction.value?.let {
            _selectedTransactionTypeIndex.value = transactionTypes.indexOf(
                element = it.transactionType,
            )
            _amount.value = abs(it.amount.value).toString()
            _title.value = it.title
            _description.value = it.description
            _selectedTransactionForIndex.value = transactionForValues.indexOf(
                element = it.transactionFor,
            )
            _category.value = categories.value.find { category ->
                category.id == it.categoryId
            }
            _sourceFrom.value = sources.value.find { source ->
                source.id == it.sourceFromId
            }
            _sourceTo.value = sources.value.find { source ->
                source.id == it.sourceToId
            }
            _transactionCalendar.value = Calendar.getInstance().apply {
                timeInMillis = it.transactionTimestamp
            }
        }
    }
}
