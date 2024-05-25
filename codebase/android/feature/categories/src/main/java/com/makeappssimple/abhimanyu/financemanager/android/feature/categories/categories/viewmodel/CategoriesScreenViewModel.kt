package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.DeleteCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.GetAllCategoriesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.CheckIfCategoryIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.griditem.CategoriesGridItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultInvestmentCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
public class CategoriesScreenViewModel @Inject constructor(
    private val checkIfCategoryIsUsedInTransactionsUseCase: CheckIfCategoryIsUsedInTransactionsUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
    private val getAllCategoriesFlowUseCase: GetAllCategoriesFlowUseCase,
    private val myPreferencesRepository: MyPreferencesRepository,
    private val navigator: Navigator,
) : ScreenViewModel, ViewModel() {
    private val _categoriesGridItemDataMap: MutableStateFlow<Map<TransactionType, ImmutableList<CategoriesGridItemData>>> =
        MutableStateFlow(
            value = emptyMap(),
        )
    public val categoriesGridItemDataMap: StateFlow<Map<TransactionType, ImmutableList<CategoriesGridItemData>>> =
        _categoriesGridItemDataMap

    public fun deleteCategory(
        id: Int,
    ) {
        viewModelScope.launch {
            deleteCategoryUseCase(
                id = id,
            )
        }
    }

    public fun initViewModel() {
        fetchData()
    }

    public fun navigateToAddCategoryScreen(
        transactionType: String,
    ) {
        navigator.navigateToAddCategoryScreen(
            transactionType = transactionType,
        )
    }

    public fun navigateToEditCategoryScreen(
        categoryId: Int,
    ) {
        navigator.navigateToEditCategoryScreen(
            categoryId = categoryId,
        )
    }

    public fun navigateUp() {
        navigator.navigateUp()
    }

    public fun setDefaultCategoryIdInDataStore(
        defaultCategoryId: Int,
        transactionType: TransactionType,
    ) {
        viewModelScope.launch {
            @Suppress("UNUSED_VARIABLE")
            val result = when (transactionType) {
                TransactionType.EXPENSE -> {
                    myPreferencesRepository.setDefaultExpenseCategoryId(
                        defaultExpenseCategoryId = defaultCategoryId,
                    )
                }

                TransactionType.INCOME -> {
                    myPreferencesRepository.setDefaultIncomeCategoryId(
                        defaultIncomeCategoryId = defaultCategoryId,
                    )
                }

                TransactionType.INVESTMENT -> {
                    myPreferencesRepository.setDefaultInvestmentCategoryId(
                        defaultInvestmentCategoryId = defaultCategoryId,
                    )
                }

                TransactionType.TRANSFER -> {
                    false
                }

                TransactionType.ADJUSTMENT -> {
                    false
                }

                TransactionType.REFUND -> {
                    false
                }
            }
            // TODO(Abhi): Use the result to show snackbar
        }
    }

    private fun fetchData() {
        val defaultDataId: Flow<DefaultDataId?> = myPreferencesRepository.getDefaultDataIdFlow()
        val categoriesTransactionTypeMap: Flow<Map<TransactionType, ImmutableList<Category>>> =
            getAllCategoriesFlowUseCase()
                .map { categories ->
                    categories
                        .groupBy { category ->
                            category.transactionType
                        }
                        .mapValues { (_, value) ->
                            value.toImmutableList()
                        }
                }

        viewModelScope.launch {
            combine(
                defaultDataId,
                categoriesTransactionTypeMap
            ) {
                    defaultDataId,
                    categoriesTransactionTypeMap,
                ->
                Pair(
                    first = defaultDataId,
                    second = categoriesTransactionTypeMap,
                )
            }.collectLatest { (defaultDataId, categoriesTransactionTypeMap) ->
                val expenseCategoriesGridItemDataList =
                    categoriesTransactionTypeMap[TransactionType.EXPENSE]
                        ?.sortedBy {
                            it.title
                        }
                        ?.map { category ->
                            val isDefault =
                                if (defaultDataId.isNull() || defaultDataId.expenseCategory == 0) {
                                    isDefaultExpenseCategory(
                                        category = category.title,
                                    )
                                } else {
                                    defaultDataId.expenseCategory == category.id
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
                        }
                        .orEmpty()
                val incomeCategoriesGridItemDataList =
                    categoriesTransactionTypeMap[TransactionType.INCOME]
                        ?.sortedBy {
                            it.title
                        }
                        ?.map { category ->
                            val isDefault =
                                if (defaultDataId.isNull() || defaultDataId.incomeCategory == 0) {
                                    isDefaultIncomeCategory(
                                        category = category.title,
                                    )
                                } else {
                                    defaultDataId.incomeCategory == category.id
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
                        }
                        .orEmpty()
                val investmentCategoriesGridItemDataList =
                    categoriesTransactionTypeMap[TransactionType.INVESTMENT]
                        ?.sortedBy {
                            it.title
                        }
                        ?.map { category ->
                            val isDefault =
                                if (defaultDataId.isNull() || defaultDataId.investmentCategory == 0) {
                                    isDefaultInvestmentCategory(
                                        category = category.title,
                                    )
                                } else {
                                    defaultDataId.investmentCategory == category.id
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
                        }
                        .orEmpty()
                _categoriesGridItemDataMap.update {
                    mapOf(
                        TransactionType.EXPENSE to expenseCategoriesGridItemDataList.toImmutableList(),
                        TransactionType.INCOME to incomeCategoriesGridItemDataList.toImmutableList(),
                        TransactionType.INVESTMENT to investmentCategoriesGridItemDataList.toImmutableList(),
                    )
                }
            }
        }
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
