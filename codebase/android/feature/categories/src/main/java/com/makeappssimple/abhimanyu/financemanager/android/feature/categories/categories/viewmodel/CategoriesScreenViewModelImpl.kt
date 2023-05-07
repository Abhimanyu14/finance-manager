package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase.DeleteCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase.GetAllCategoriesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.CheckIfCategoryIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
internal class CategoriesScreenViewModelImpl @Inject constructor(
    getAllCategoriesFlowUseCase: GetAllCategoriesFlowUseCase,
    override val logger: Logger,
    override val navigationManager: NavigationManager,
    private val checkIfCategoryIsUsedInTransactionsUseCase: CheckIfCategoryIsUsedInTransactionsUseCase,
    private val dataStore: MyDataStore,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
    private val dispatcherProvider: DispatcherProvider,
) : CategoriesScreenViewModel, ViewModel() {
    private val _selectedTabIndex: MutableStateFlow<Int> = MutableStateFlow(
        value = 0,
    )
    override val selectedTabIndex: StateFlow<Int> = _selectedTabIndex

    private val categoriesTransactionTypeMap: Flow<Map<TransactionType, List<Category>>> =
        getAllCategoriesFlowUseCase()
            .map { categories ->
                categories.groupBy { category ->
                    category.transactionType
                }
            }
    override val expenseCategories: Flow<List<Category>> = categoriesTransactionTypeMap.map {
        it[TransactionType.EXPENSE] ?: emptyList()
    }
    override val incomeCategories: Flow<List<Category>> = categoriesTransactionTypeMap.map {
        it[TransactionType.INCOME] ?: emptyList()
    }
    override val investmentCategories: Flow<List<Category>> = categoriesTransactionTypeMap.map {
        it[TransactionType.INVESTMENT] ?: emptyList()
    }
    override val expenseCategoryIsUsedInTransactions: Flow<List<Boolean>> = expenseCategories
        .map {
            it.map { category ->
                checkIfCategoryIsUsedInTransactionsUseCase(
                    categoryId = category.id,
                )
            }
        }
    override val incomeCategoryIsUsedInTransactions: Flow<List<Boolean>> = incomeCategories
        .map {
            it.map { category ->
                checkIfCategoryIsUsedInTransactionsUseCase(
                    categoryId = category.id,
                )
            }
        }
    override val investmentCategoryIsUsedInTransactions: Flow<List<Boolean>> = incomeCategories
        .map {
            it.map { category ->
                checkIfCategoryIsUsedInTransactionsUseCase(
                    categoryId = category.id,
                )
            }
        }
    override val defaultExpenseCategoryId: Flow<Int?> = dataStore
        .getDefaultExpenseCategoryId()
    override val defaultIncomeCategoryId: Flow<Int?> = dataStore
        .getDefaultIncomeCategoryId()
    override val defaultInvestmentCategoryId: Flow<Int?> = dataStore
        .getDefaultInvestmentCategoryId()

    override fun deleteCategory(
        id: Int,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            deleteCategoryUseCase(
                id = id,
            )
        }
    }

    override fun setDefaultCategoryIdInDataStore(
        defaultCategoryId: Int,
        transactionType: TransactionType,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            when (transactionType) {
                TransactionType.EXPENSE -> {
                    dataStore.setDefaultExpenseCategoryId(
                        defaultExpenseCategoryId = defaultCategoryId,
                    )
                }

                TransactionType.INCOME -> {
                    dataStore.setDefaultIncomeCategoryId(
                        defaultIncomeCategoryId = defaultCategoryId,
                    )
                }

                TransactionType.INVESTMENT -> {
                    dataStore.setDefaultInvestmentCategoryId(
                        defaultInvestmentCategoryId = defaultCategoryId,
                    )
                }

                TransactionType.TRANSFER -> {}

                TransactionType.ADJUSTMENT -> {}

                TransactionType.REFUND -> {}
            }
        }
    }

    override fun updateSelectedTabIndex(
        updatedSelectedTabIndex: Int,
    ) {
        _selectedTabIndex.value = updatedSelectedTabIndex
    }
}
