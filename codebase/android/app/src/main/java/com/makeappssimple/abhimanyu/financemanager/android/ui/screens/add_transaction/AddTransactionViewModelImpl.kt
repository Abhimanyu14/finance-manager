package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_transaction

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.GetCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.GetSourcesCountUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.GetSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.UpdateSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase.InsertTransactionUseCase
import com.makeappssimple.abhimanyu.financemanager.android.entities.amount.Amount
import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.sortOrder
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionFor
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.navigation.utils.navigateUp
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.formattedDate
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.formattedTime
import com.makeappssimple.abhimanyu.financemanager.android.utils.extensions.isNotNullOrBlank
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModelImpl @Inject constructor(
    getCategoriesUseCase: GetCategoriesUseCase,
    getSourcesUseCase: GetSourcesUseCase,
    override val navigationManager: NavigationManager,
    private val dispatcherProvider: DispatcherProvider,
    private val getSourcesCountUseCase: GetSourcesCountUseCase,
    private val insertTransactionUseCase: InsertTransactionUseCase,
    private val updateSourcesUseCase: UpdateSourcesUseCase,
) : AddTransactionViewModel, ViewModel() {
    private var expenseDefaultSource: Source? = null
    private var incomeDefaultSource: Source? = null

    override val transactionForValues: Array<TransactionFor> = TransactionFor.values()
    override val transactionTypes: Array<TransactionType> = TransactionType.values()
    override val categories: Flow<List<Category>> = getCategoriesUseCase()
    override val categoryTextFieldValue: TextFieldValue by derivedStateOf {
        TextFieldValue(
            text = category?.title ?: "",
        )
    }
    override val sourceFromTextFieldValue: TextFieldValue by derivedStateOf {
        TextFieldValue(
            text = sourceFrom?.name ?: "",
        )
    }
    override val sourceToTextFieldValue: TextFieldValue by derivedStateOf {
        TextFieldValue(
            text = sourceTo?.name ?: "",
        )
    }
    override val transactionDateTextFieldValue: TextFieldValue by derivedStateOf {
        TextFieldValue(
            text = transactionCalendar.formattedDate(),
        )
    }
    override val transactionTimeTextFieldValue: TextFieldValue by derivedStateOf {
        TextFieldValue(
            text = transactionCalendar.formattedTime(),
        )
    }
    override val sources: Flow<List<Source>> = flow {
        getSourcesUseCase().collectIndexed { _, value ->
            expenseDefaultSource = value.firstOrNull {
                it.name.contains(
                    other = "Cash",
                    ignoreCase = true,
                )
            }
            incomeDefaultSource = expenseDefaultSource
            _sourceFrom = expenseDefaultSource
            emit(
                value = value.sortedWith(
                    comparator = compareBy {
                        it.type.sortOrder
                    }
                ),
            )
        }
    }

    private var _expenseDefaultCategory: Category? = null
    override var expenseDefaultCategory: Category? = _expenseDefaultCategory

    private var _incomeDefaultCategory: Category? = null
    override var incomeDefaultCategory: Category? = _incomeDefaultCategory

    private var _transactionCalendar: Calendar by mutableStateOf(
        Calendar.getInstance()
    )
    override var transactionCalendar: Calendar = _transactionCalendar


    private var _selectedTransactionForIndex: Int by mutableStateOf(
        value = transactionForValues.indexOf(
            element = TransactionFor.SELF,
        ),
    )
    override val selectedTransactionForIndex: Int = _selectedTransactionForIndex

    private var _category: Category? by mutableStateOf(
        value = null,
    )
    override val category: Category? = _category

    private var _sourceFrom: Source? by mutableStateOf(
        value = null,
    )
    override val sourceFrom: Source? = _sourceFrom

    private var _sourceTo: Source? by mutableStateOf(
        value = null,
    )
    override val sourceTo: Source? = _sourceTo


    private var _amount: String by mutableStateOf(
        value = "",
    )
    override val amount: String = _amount

    private var _selectedTransactionTypeIndex: Int by mutableStateOf(
        value = transactionTypes.indexOf(
            element = TransactionType.EXPENSE,
        ),
    )
    override val selectedTransactionTypeIndex: Int = _selectedTransactionTypeIndex

    private var _title: String by mutableStateOf(
        value = "",
    )
    override val title: String = _title

    private var _description: String by mutableStateOf(
        value = "",
    )
    override val description: String = _description

    private val _transactionTypesForNewTransaction = MutableStateFlow(
        value = emptyList<TransactionType>(),
    )
    override val transactionTypesForNewTransaction: StateFlow<List<TransactionType>> =
        _transactionTypesForNewTransaction

    init {
        initTransactionTypesForNewTransaction()
    }

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }

    override fun updateSelectedTransactionTypeIndex(
        updatedSelectedTransactionTypeIndex: Int,
    ) {
        _selectedTransactionTypeIndex = updatedSelectedTransactionTypeIndex
        when (transactionTypes[selectedTransactionTypeIndex]) {
            TransactionType.INCOME -> {
                _sourceFrom = null
                _sourceTo = incomeDefaultSource
                _category = incomeDefaultCategory
            }
            TransactionType.EXPENSE -> {
                _sourceFrom = expenseDefaultSource
                _sourceTo = null
                _category = expenseDefaultCategory
            }
            TransactionType.TRANSFER -> {
                _sourceFrom = expenseDefaultSource
                _sourceTo = incomeDefaultSource
            }
            TransactionType.ADJUSTMENT -> {}
        }
    }

    override fun insertTransaction() {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            insertTransactionUseCase(
                transaction = Transaction(
                    amount = Amount(
                        value = if (transactionTypes[selectedTransactionTypeIndex] == TransactionType.EXPENSE) {
                            -1 * amount.toLong()
                        } else {
                            amount.toLong()
                        },
                    ),
                    categoryId = category?.id ?: 0,
                    sourceFromId = sourceFrom?.id ?: 0,
                    sourceToId = sourceTo?.id ?: 0,
                    description = description,
                    title = if (transactionTypes[selectedTransactionTypeIndex] == TransactionType.TRANSFER) {
                        TransactionType.TRANSFER.title
                    } else {
                        title
                    },
                    creationTimestamp = Calendar.getInstance().timeInMillis,
                    transactionTimestamp = transactionCalendar.timeInMillis,
                    transactionFor = when (transactionTypes[selectedTransactionTypeIndex]) {
                        TransactionType.INCOME -> {
                            TransactionFor.SELF
                        }
                        TransactionType.EXPENSE -> {
                            transactionForValues[selectedTransactionForIndex]
                        }
                        TransactionType.TRANSFER -> {
                            TransactionFor.SELF
                        }
                        TransactionType.ADJUSTMENT -> {
                            TransactionFor.SELF
                        }
                    },
                    transactionType = transactionTypes[selectedTransactionTypeIndex],
                ),
            )
            sourceFrom?.let { sourceFrom ->
                updateSourcesUseCase(
                    sourceFrom.copy(
                        balanceAmount = sourceFrom.balanceAmount.copy(
                            value = sourceFrom.balanceAmount.value - amount.toLong(),
                        )
                    ),
                )
            }
            sourceTo?.let { sourceTo ->
                updateSourcesUseCase(
                    sourceTo.copy(
                        balanceAmount = sourceTo.balanceAmount.copy(
                            value = sourceTo.balanceAmount.value + amount.toLong(),
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
        return when (transactionTypes[selectedTransactionTypeIndex]) {
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
        return when (transactionTypes[selectedTransactionTypeIndex]) {
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
        return when (transactionTypes[selectedTransactionTypeIndex]) {
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
        return when (transactionTypes[selectedTransactionTypeIndex]) {
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
        return when (transactionTypes[selectedTransactionTypeIndex]) {
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

    override fun isValidTransactionData(): Boolean {
        return when (transactionTypes[selectedTransactionTypeIndex]) {
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
    }

    private fun initTransactionTypesForNewTransaction() {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            val sourcesCount = getSourcesCountUseCase()
            _transactionTypesForNewTransaction.value = if (sourcesCount > 1) {
                TransactionType.values().filter {
                    it != TransactionType.ADJUSTMENT
                }
            } else {
                TransactionType.values().filter {
                    it != TransactionType.ADJUSTMENT && it != TransactionType.TRANSFER
                }
            }
        }
    }

    override fun updateTitle(
        updatedTitle: String,
    ) {
        _title = updatedTitle
    }

    override fun clearTitle() {
        _title = ""
    }

    override fun updateDescription(
        updatedDescription: String,
    ) {
        _description = updatedDescription
    }

    override fun clearDescription() {
        _description = ""
    }

    override fun updateAmount(
        updatedAmount: String,
    ) {
        _amount = updatedAmount
    }

    override fun clearAmount() {
        _amount = ""
    }

    override fun updateSourceFrom(
        updatedSourceFrom: Source,
    ) {
        _sourceFrom = updatedSourceFrom
    }

    override fun updateSourceTo(
        updatedSourceTo: Source,
    ) {
        _sourceTo = updatedSourceTo
    }

    override fun updateCategory(
        updatedCategory: Category?,
    ) {
        _category = updatedCategory
    }

    override fun updateSelectedTransactionForIndex(
        updatedSelectedTransactionForIndex: Int,
    ) {
        _selectedTransactionForIndex = updatedSelectedTransactionForIndex
    }

    override fun updateTransactionCalendar(
        updatedTransactionCalendar: Calendar,
    ) {
        _transactionCalendar = updatedTransactionCalendar
    }

    override fun updateExpenseDefaultCategory(
        updatedExpenseDefaultCategory: Category?,
    ) {
        _expenseDefaultCategory = updatedExpenseDefaultCategory
    }

    override fun updateIncomeDefaultCategory(
        updatedIncomeDefaultCategory: Category?,
    ) {
        _incomeDefaultCategory = updatedIncomeDefaultCategory
    }
}
