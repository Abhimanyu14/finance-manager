package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.transactions.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.GetCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.GetCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.GetSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.source.usecase.GetSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase.GetAllTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.usecase.DeleteTransactionAndRevertOtherDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.ui.screens.transactions.components.TransactionsListItemViewData
import com.makeappssimple.abhimanyu.financemanager.android.utils.getDateString
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.abs
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class TransactionsScreenViewModelImpl @Inject constructor(
    getCategoriesUseCase: GetCategoriesUseCase,
    getSourcesUseCase: GetSourcesUseCase,
    getAllTransactionsUseCase: GetAllTransactionsUseCase,
    override val navigationManager: NavigationManager,
    private val dispatcherProvider: DispatcherProvider,
    private val deleteTransactionAndRevertOtherDataUseCase: DeleteTransactionAndRevertOtherDataUseCase,
    private val getCategoryUseCase: GetCategoryUseCase,
    private val getSourceUseCase: GetSourceUseCase,
) : TransactionsScreenViewModel, ViewModel() {
    private val categories: Flow<List<Category>> = getCategoriesUseCase()
    private val allTransactions: Flow<List<Transaction>> = getAllTransactionsUseCase()

    // region Search
    private val _searchText = MutableStateFlow(
        value = "",
    )
    override val searchText: StateFlow<String> = _searchText
    // endregion

    // region Filter
    private val _selectedExpenseCategoryIndices = MutableStateFlow(
        value = emptyList<Int>(),
    )
    override val selectedExpenseCategoryIndices: StateFlow<List<Int>> =
        _selectedExpenseCategoryIndices
    private val _selectedIncomeCategoryIndices = MutableStateFlow(
        value = emptyList<Int>(),
    )
    override val selectedIncomeCategoryIndices: StateFlow<List<Int>> =
        _selectedIncomeCategoryIndices
    private val _selectedSourceIndices = MutableStateFlow(
        value = emptyList<Int>(),
    )
    override val selectedSourceIndices: StateFlow<List<Int>> = _selectedSourceIndices
    private val _selectedTransactionTypesIndices = MutableStateFlow(
        value = emptyList<Int>(),
    )
    override val selectedTransactionTypesIndices: StateFlow<List<Int>> =
        _selectedTransactionTypesIndices
    // endregion

    // region Sort
    private val _selectedSortOption = MutableStateFlow(
        value = SortOption.LATEST_FIRST,
    )
    override val selectedSortOption: StateFlow<SortOption> = _selectedSortOption
    // endregion

    override val expenseCategories: Flow<List<Category>> = categories.map {
        it.filter { category ->
            category.transactionType == TransactionType.EXPENSE
        }
    }
    override val incomeCategories: Flow<List<Category>> = categories.map {
        it.filter { category ->
            category.transactionType == TransactionType.INCOME
        }
    }
    override val sources: Flow<List<Source>> = getSourcesUseCase()

    val transactionTypes: List<TransactionType> = TransactionType.values().toList()
    override val transactionsListItemViewData: Flow<Map<String, List<TransactionsListItemViewData>>> =
        combine(
            allTransactions,
            searchText,
            selectedExpenseCategoryIndices,
            selectedIncomeCategoryIndices,
            selectedSourceIndices,
            selectedTransactionTypesIndices,
            expenseCategories,
            incomeCategories,
            sources,
            selectedSortOption,
        ) { flows ->
            val allTransactionsValue: List<Transaction> =
                flows[0] as? List<Transaction> ?: emptyList()
            val searchTextValue: String = flows[1] as String
            val selectedExpenseCategoryIndicesValue: List<Int> =
                flows[2] as? List<Int> ?: emptyList()
            val selectedIncomeCategoryIndicesValue: List<Int> =
                flows[3] as? List<Int> ?: emptyList()
            val selectedSourceIndicesValue: List<Int> = flows[4] as? List<Int> ?: emptyList()
            val selectedTransactionTypesIndicesValue: List<Int> =
                flows[5] as? List<Int> ?: emptyList()
            val expenseCategoriesValue: List<Category> = flows[6] as? List<Category> ?: emptyList()
            val incomeCategoriesValue: List<Category> = flows[7] as? List<Category> ?: emptyList()
            val sourcesValue: List<Source> = flows[8] as? List<Source> ?: emptyList()
            val selectedSortOptionValue: SortOption =
                flows[9] as? SortOption ?: SortOption.LATEST_FIRST

            allTransactionsValue
                .asSequence()
                // Search
                .filter {
                    if (searchTextValue.isNotBlank()) {
                        it.title.contains(
                            other = searchTextValue,
                            ignoreCase = true,
                        )
                    } else {
                        true
                    }
                }
                .filter {
                    if (selectedTransactionTypesIndicesValue.isNotEmpty()) {
                        selectedTransactionTypesIndicesValue.contains(
                            transactionTypes.indexOf(it.transactionType)
                        )
                    } else {
                        true
                    }
                }
                .toList()
                .map { transaction ->
                    val category = if (transaction.categoryId != null) {
                        getCategoryUseCase(
                            id = transaction.categoryId,
                        )
                    } else {
                        null
                    }
                    val sourceFrom = if (transaction.sourceFromId != null) {
                        getSourceUseCase(
                            id = transaction.sourceFromId,
                        )
                    } else {
                        null
                    }
                    val sourceTo = if (transaction.sourceToId != null) {
                        getSourceUseCase(
                            id = transaction.sourceToId,
                        )
                    } else {
                        null
                    }
                    TransactionsListItemViewData(
                        category = category,
                        transaction = transaction,
                        sourceFrom = sourceFrom,
                        sourceTo = sourceTo,
                    )
                }
                .filter {
                    if (selectedSourceIndicesValue.isNotEmpty()) {
                        selectedSourceIndicesValue.contains(sourcesValue.indexOf(it.sourceFrom)) ||
                                selectedSourceIndicesValue.contains(sourcesValue.indexOf(it.sourceTo))
                    } else {
                        true
                    }
                }
                .filter {
                    if (selectedExpenseCategoryIndicesValue.isNotEmpty() || selectedIncomeCategoryIndicesValue.isNotEmpty()) {
                        selectedExpenseCategoryIndicesValue.contains(expenseCategoriesValue.indexOf(
                            it.category)) ||
                                selectedIncomeCategoryIndicesValue.contains(incomeCategoriesValue.indexOf(
                                    it.category))
                    } else {
                        true
                    }
                }
                .sortedWith(compareBy {
                    when (selectedSortOptionValue) {
                        SortOption.AMOUNT_ASC -> {
                            abs(it.transaction.amount.value)
                        }
                        SortOption.AMOUNT_DESC -> {
                            -abs(it.transaction.amount.value)
                        }
                        SortOption.LATEST_FIRST -> {
                            -it.transaction.transactionTimestamp
                        }
                        SortOption.OLDEST_FIRST -> {
                            it.transaction.transactionTimestamp
                        }
                    }
                })
                .groupBy {
                    if (selectedSortOptionValue == SortOption.LATEST_FIRST || selectedSortOptionValue == SortOption.OLDEST_FIRST) {
                        getDateString(
                            it.transaction.transactionTimestamp
                        )
                    } else {
                        ""
                    }
                }
        }.flowOn(
            context = dispatcherProvider.io,
        )


    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }

    // region Search
    override fun updateSearchText(
        updatedSearchText: String,
    ) {
        _searchText.value = updatedSearchText
    }
    // endregion

    // region Filter
    override fun updateSelectedExpenseCategoryIndices(
        updatedSelectedExpenseCategoryIndices: List<Int>,
    ) {
        _selectedExpenseCategoryIndices.value = updatedSelectedExpenseCategoryIndices
    }

    override fun updateSelectedIncomeCategoryIndices(
        updatedSelectedIncomeCategoryIndices: List<Int>,
    ) {
        _selectedIncomeCategoryIndices.value = updatedSelectedIncomeCategoryIndices
    }

    override fun updateSelectedSourceIndices(
        updatedSelectedSourceIndices: List<Int>,
    ) {
        _selectedSourceIndices.value = updatedSelectedSourceIndices
    }

    override fun updateSelectedTransactionTypesIndices(
        updatedSelectedTransactionTypesIndices: List<Int>,
    ) {
        _selectedTransactionTypesIndices.value = updatedSelectedTransactionTypesIndices
    }
    // endregion

    // region Sort
    override fun updateSelectedSortOption(
        updatedSelectedSortOption: SortOption,
    ) {
        _selectedSortOption.value = updatedSelectedSortOption
    }
    // endregion

    override fun deleteTransaction(
        id: Int,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            deleteTransactionAndRevertOtherDataUseCase(
                id = id,
            )
        }
    }
}
