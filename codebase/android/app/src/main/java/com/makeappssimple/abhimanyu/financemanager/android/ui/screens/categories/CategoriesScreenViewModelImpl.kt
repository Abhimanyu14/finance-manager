package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.DeleteCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.category.usecase.GetCategoriesUseCase
import com.makeappssimple.abhimanyu.financemanager.android.data.local.datastore.MyDataStore
import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.usecase.CheckIfCategoryIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class CategoriesScreenViewModelImpl @Inject constructor(
    getCategoriesUseCase: GetCategoriesUseCase,
    override val navigationManager: NavigationManager,
    private val checkIdCategoryIsUsedInTransactionsUseCase: CheckIfCategoryIsUsedInTransactionsUseCase,
    private val dataStore: MyDataStore,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
    private val dispatcherProvider: DispatcherProvider,
) : CategoriesScreenViewModel, ViewModel() {
    private val transactionTypes = listOf(
        TransactionType.EXPENSE,
        TransactionType.INCOME,
    )

    private var _selectedTabIndex: MutableStateFlow<Int> = MutableStateFlow(
        value = 0,
    )
    override val selectedTabIndex: StateFlow<Int> = _selectedTabIndex

    private val categories: Flow<List<Category>> = getCategoriesUseCase()
    override val filteredCategories: Flow<List<Category>> = combine(
        flow = categories,
        flow2 = selectedTabIndex,
    ) { categories, selectedTabIndex ->
        categories.filter { category ->
            category.transactionType == transactionTypes[selectedTabIndex]
        }
    }
    override val categoriesIsUsedInTransactions: Flow<List<Boolean>> = filteredCategories
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
            if (transactionType == TransactionType.EXPENSE) {
                dataStore.setDefaultExpenseCategoryIdInDataStore(
                    defaultExpenseCategoryId = defaultCategoryId,
                )
            } else if (transactionType == TransactionType.INCOME) {
                dataStore.setDefaultIncomeCategoryIdInDataStore(
                    defaultIncomeCategoryId = defaultCategoryId,
                )
            }
        }
    }
}
