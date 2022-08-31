package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.GetCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.GetCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourceUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.usecase.GetSourcesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.GetAllTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.usecase.GetTransactionForUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.usecase.DeleteTransactionAndRevertOtherDataUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.getDateString
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.transactions.components.TransactionsListItemViewData
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
internal class TransactionsScreenViewModelImpl @Inject constructor(
    getCategoriesUseCase: GetCategoriesUseCase,
    getSourcesUseCase: GetSourcesUseCase,
    getAllTransactionsUseCase: GetAllTransactionsUseCase,
    override val navigationManager: NavigationManager,
    private val dispatcherProvider: DispatcherProvider,
    private val deleteTransactionAndRevertOtherDataUseCase: DeleteTransactionAndRevertOtherDataUseCase,
    private val getCategoryUseCase: GetCategoryUseCase,
    private val getSourceUseCase: GetSourceUseCase,
    private val getTransactionForUseCase: GetTransactionForUseCase,
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
    private val _selectedInvestmentCategoryIndices = MutableStateFlow(
        value = emptyList<Int>(),
    )
    override val selectedInvestmentCategoryIndices: StateFlow<List<Int>> =
        _selectedInvestmentCategoryIndices
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
    override val investmentCategories: Flow<List<Category>> = categories.map {
        it.filter { category ->
            category.transactionType == TransactionType.INVESTMENT
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
            selectedInvestmentCategoryIndices,
            selectedSourceIndices,
            selectedTransactionTypesIndices,
            expenseCategories,
            incomeCategories,
            investmentCategories,
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
            val selectedInvestmentCategoryIndicesValue: List<Int> =
                flows[4] as? List<Int> ?: emptyList()
            val selectedSourceIndicesValue: List<Int> = flows[5] as? List<Int> ?: emptyList()
            val selectedTransactionTypesIndicesValue: List<Int> =
                flows[6] as? List<Int> ?: emptyList()
            val expenseCategoriesValue: List<Category> = flows[7] as? List<Category> ?: emptyList()
            val incomeCategoriesValue: List<Category> = flows[8] as? List<Category> ?: emptyList()
            val investmentCategoriesValue: List<Category> =
                flows[9] as? List<Category> ?: emptyList()
            val sourcesValue: List<Source> = flows[10] as? List<Source> ?: emptyList()
            val selectedSortOptionValue: SortOption =
                flows[11] as? SortOption ?: SortOption.LATEST_FIRST

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
                    val category = transaction.categoryId?.let { categoryId ->
                        getCategoryUseCase(
                            id = categoryId,
                        )
                    }
                    val sourceFrom = transaction.sourceFromId?.let { sourceFromId ->
                        getSourceUseCase(
                            id = sourceFromId,
                        )
                    }
                    val sourceTo = transaction.sourceToId?.let { sourceToId ->
                        getSourceUseCase(
                            id = sourceToId,
                        )
                    }
                    val transactionFor = getTransactionForUseCase(
                        id = transaction.transactionForId,
                    )
                    TransactionsListItemViewData(
                        category = category,
                        sourceFrom = sourceFrom,
                        sourceTo = sourceTo,
                        transaction = transaction,
                        transactionFor = transactionFor,
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
                    if (
                        selectedExpenseCategoryIndicesValue.isNotEmpty() ||
                        selectedIncomeCategoryIndicesValue.isNotEmpty() ||
                        selectedInvestmentCategoryIndicesValue.isNotEmpty()
                    ) {
                        selectedExpenseCategoryIndicesValue.contains(
                            expenseCategoriesValue.indexOf(
                                it.category
                            )
                        ) || selectedIncomeCategoryIndicesValue.contains(
                            incomeCategoriesValue.indexOf(
                                it.category
                            )
                        ) || selectedInvestmentCategoryIndicesValue.contains(
                            investmentCategoriesValue.indexOf(
                                it.category
                            )
                        )
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

    override fun updateSelectedInvestmentCategoryIndices(
        updatedSelectedInvestmentCategoryIndices: List<Int>,
    ) {
        _selectedInvestmentCategoryIndices.value = updatedSelectedInvestmentCategoryIndices
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
