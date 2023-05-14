package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase.DeleteCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.category.usecase.GetAllCategoriesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.transaction.usecase.CheckIfCategoryIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.datastore.MyDataStore
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.Logger
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.grid_item.CategoriesGridItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultInvestmentCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
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

    private val defaultExpenseCategoryId: Flow<Int?> = dataStore
        .getDefaultExpenseCategoryId()
    private val defaultIncomeCategoryId: Flow<Int?> = dataStore
        .getDefaultIncomeCategoryId()
    private val defaultInvestmentCategoryId: Flow<Int?> = dataStore
        .getDefaultInvestmentCategoryId()
    private val categoriesTransactionTypeMap: Flow<Map<TransactionType, List<Category>>> =
        getAllCategoriesFlowUseCase()
            .map { categories ->
                categories.groupBy { category ->
                    category.transactionType
                }
            }
    override val categoriesGridItemDataMap: Flow<Map<TransactionType, List<CategoriesGridItemData>>> =
        combine(
            categoriesTransactionTypeMap,
            defaultExpenseCategoryId,
            defaultIncomeCategoryId,
            defaultInvestmentCategoryId,
        ) {
                categoriesTransactionTypeMap,
                defaultExpenseCategoryId,
                defaultIncomeCategoryId,
                defaultInvestmentCategoryId,
            ->
            val expenseCategoriesGridItemDataList =
                categoriesTransactionTypeMap[TransactionType.EXPENSE]?.map { category ->
                    val isDefault = if (defaultExpenseCategoryId.isNull()) {
                        isDefaultExpenseCategory(
                            category = category.title,
                        )
                    } else {
                        defaultExpenseCategoryId == category.id
                    }
                    val isUsedInTransactions = checkIfCategoryIsUsedInTransactionsUseCase(
                        categoryId = category.id,
                    )
                    val isDeleteEnabled = !isDefault && !isUsedInTransactions

                    getCategoriesGridItemData(
                        isDefault = isDefault,
                        isDeleteEnabled = isDeleteEnabled,
                        category = category,
                    )
                } ?: emptyList()
            val incomeCategoriesGridItemDataList =
                categoriesTransactionTypeMap[TransactionType.INCOME]?.map { category ->
                    val isDefault = if (defaultIncomeCategoryId.isNull()) {
                        isDefaultIncomeCategory(
                            category = category.title,
                        )
                    } else {
                        defaultIncomeCategoryId == category.id
                    }
                    val isUsedInTransactions = checkIfCategoryIsUsedInTransactionsUseCase(
                        categoryId = category.id,
                    )
                    val isDeleteEnabled = !isDefault && !isUsedInTransactions

                    getCategoriesGridItemData(
                        isDefault = isDefault,
                        isDeleteEnabled = isDeleteEnabled,
                        category = category,
                    )
                } ?: emptyList()
            val investmentCategoriesGridItemDataList =
                categoriesTransactionTypeMap[TransactionType.INVESTMENT]?.map { category ->
                    val isDefault = if (defaultInvestmentCategoryId.isNull()) {
                        isDefaultInvestmentCategory(
                            category = category.title,
                        )
                    } else {
                        defaultInvestmentCategoryId == category.id
                    }
                    val isUsedInTransactions = checkIfCategoryIsUsedInTransactionsUseCase(
                        categoryId = category.id,
                    )
                    val isDeleteEnabled = !isDefault && !isUsedInTransactions

                    getCategoriesGridItemData(
                        isDefault = isDefault,
                        isDeleteEnabled = isDeleteEnabled,
                        category = category,
                    )
                } ?: emptyList()
            mapOf(
                TransactionType.EXPENSE to expenseCategoriesGridItemDataList,
                TransactionType.INCOME to incomeCategoriesGridItemDataList,
                TransactionType.INVESTMENT to investmentCategoriesGridItemDataList,
            )
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

    private fun getCategoriesGridItemData(
        isDefault: Boolean,
        isDeleteEnabled: Boolean,
        category: Category,
    ): CategoriesGridItemData {
        val isEditVisible = !isDefaultExpenseCategory(
            category = category.title,
        ) && !isDefaultIncomeCategory(
            category = category.title,
        ) && !isDefaultInvestmentCategory(
            category = category.title,
        )
        val isSetAsDefaultVisible = !isDefault
        val isDeleteVisible = !isDefaultExpenseCategory(
            category = category.title,
        ) && !isDefaultIncomeCategory(
            category = category.title,
        ) && !isDefaultInvestmentCategory(
            category = category.title,
        ) && isDeleteEnabled

        return CategoriesGridItemData(
            isDeleteVisible = isDeleteVisible,
            isEditVisible = isEditVisible,
            isSetAsDefaultVisible = isSetAsDefaultVisible,
            isSelected = isDefault,
            category = category,
        )
    }
}
