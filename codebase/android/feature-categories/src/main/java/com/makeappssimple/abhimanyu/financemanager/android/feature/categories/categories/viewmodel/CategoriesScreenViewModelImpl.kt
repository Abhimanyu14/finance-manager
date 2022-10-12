package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.DeleteCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.usecase.GetCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.usecase.CheckIfCategoryIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
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
    getCategoriesUseCase: GetCategoriesUseCase,
    override val navigationManager: NavigationManager,
    private val checkIdCategoryIsUsedInTransactionsUseCase: CheckIfCategoryIsUsedInTransactionsUseCase,
    private val dataStore: MyDataStore,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
    private val dispatcherProvider: DispatcherProvider,
) : CategoriesScreenViewModel, ViewModel() {
    private val _selectedTabIndex: MutableStateFlow<Int> = MutableStateFlow(
        value = 0,
    )
    override val selectedTabIndex: StateFlow<Int> = _selectedTabIndex

    private val categories: Flow<List<Category>> = getCategoriesUseCase()
    override val expenseCategories: Flow<List<Category>> = categories.map { categories ->
        categories.filter { category ->
            category.transactionType == TransactionType.EXPENSE
        }
    }
    override val incomeCategories: Flow<List<Category>> = categories.map { categories ->
        categories.filter { category ->
            category.transactionType == TransactionType.INCOME
        }
    }
    override val investmentCategories: Flow<List<Category>> = categories.map { categories ->
        categories.filter { category ->
            category.transactionType == TransactionType.INVESTMENT
        }
    }
    override val expenseCategoryIsUsedInTransactions: Flow<List<Boolean>> = expenseCategories
        .map {
            it.map { category ->
                checkIdCategoryIsUsedInTransactionsUseCase(
                    categoryId = category.id,
                )
            }
        }
    override val incomeCategoryIsUsedInTransactions: Flow<List<Boolean>> = incomeCategories
        .map {
            it.map { category ->
                checkIdCategoryIsUsedInTransactionsUseCase(
                    categoryId = category.id,
                )
            }
        }
    override val investmentCategoryIsUsedInTransactions: Flow<List<Boolean>> = incomeCategories
        .map {
            it.map { category ->
                checkIdCategoryIsUsedInTransactionsUseCase(
                    categoryId = category.id,
                )
            }
        }
    override val defaultExpenseCategoryId: Flow<Int?> = dataStore
        .getDefaultExpenseCategoryIdFromDataStore()
    override val defaultIncomeCategoryId: Flow<Int?> = dataStore
        .getDefaultIncomeCategoryIdFromDataStore()
    override val defaultInvestmentCategoryId: Flow<Int?> = dataStore
        .getDefaultInvestmentCategoryIdFromDataStore()

    override fun trackScreen() {
        // TODO-Abhi: Add screen tracking code
    }

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

    override fun updateSelectedTabIndex(
        updatedSelectedTabIndex: Int,
    ) {
        _selectedTabIndex.value = updatedSelectedTabIndex
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
                    dataStore.setDefaultExpenseCategoryIdInDataStore(
                        defaultExpenseCategoryId = defaultCategoryId,
                    )
                }

                TransactionType.INCOME -> {
                    dataStore.setDefaultIncomeCategoryIdInDataStore(
                        defaultIncomeCategoryId = defaultCategoryId,
                    )
                }

                TransactionType.INVESTMENT -> {
                    dataStore.setDefaultInvestmentCategoryIdInDataStore(
                        defaultInvestmentCategoryId = defaultCategoryId,
                    )
                }

                TransactionType.TRANSFER -> {}

                TransactionType.ADJUSTMENT -> {}

                TransactionType.REFUND -> {
                    TODO()
                }
            }
        }
    }
}
