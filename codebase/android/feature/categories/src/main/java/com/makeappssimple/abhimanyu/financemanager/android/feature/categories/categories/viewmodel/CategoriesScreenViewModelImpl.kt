package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.DispatcherProvider
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.defaultObjectStateIn
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.DeleteCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.GetAllCategoriesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.CheckIfCategoryIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.griditem.CategoriesGridItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultInvestmentCategory
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen.CategoriesScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen.CategoriesScreenUIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CategoriesScreenViewModelImpl @Inject constructor(
    getAllCategoriesFlowUseCase: GetAllCategoriesFlowUseCase,
    private val checkIfCategoryIsUsedInTransactionsUseCase: CheckIfCategoryIsUsedInTransactionsUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
    private val dispatcherProvider: DispatcherProvider,
    private val myPreferencesRepository: MyPreferencesRepository,
    private val navigator: Navigator,
) : CategoriesScreenViewModel, ViewModel() {
    private val selectedTabIndex: MutableStateFlow<Int> = MutableStateFlow(
        value = 0,
    )

    private val defaultDataId: Flow<DefaultDataId?> = myPreferencesRepository.getDefaultDataId()
    private val categoriesTransactionTypeMap: Flow<Map<TransactionType, List<Category>>> =
        getAllCategoriesFlowUseCase()
            .map { categories ->
                categories.groupBy { category ->
                    category.transactionType
                }
            }
    private val categoriesGridItemDataMap: Flow<Map<TransactionType, List<CategoriesGridItemData>>> =
        combine(
            categoriesTransactionTypeMap,
            defaultDataId,
        ) {
                categoriesTransactionTypeMap,
                defaultDataId,
            ->
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
                    }.orEmpty()
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
                    }.orEmpty()
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
                    }.orEmpty()
            mapOf(
                TransactionType.EXPENSE to expenseCategoriesGridItemDataList,
                TransactionType.INCOME to incomeCategoriesGridItemDataList,
                TransactionType.INVESTMENT to investmentCategoriesGridItemDataList,
            )
        }

    override val screenUIData: StateFlow<MyResult<CategoriesScreenUIData>?> = combine(
        selectedTabIndex,
        categoriesGridItemDataMap,
    ) {
            selectedTabIndex,
            categoriesGridItemDataMap,
        ->
        if (
            selectedTabIndex.isNull() ||
            categoriesGridItemDataMap.isNull()
        ) {
            MyResult.Loading
        } else {
            MyResult.Success(
                data = CategoriesScreenUIData(
                    selectedTabIndex = selectedTabIndex,
                    categoriesGridItemDataMap = categoriesGridItemDataMap,
                ),
            )
        }
    }.defaultObjectStateIn(
        scope = viewModelScope,
    )

    override fun handleUIEvents(
        uiEvent: CategoriesScreenUIEvent,
    ) {
        when (uiEvent) {
            is CategoriesScreenUIEvent.DeleteCategory -> {
                deleteCategory(
                    id = uiEvent.categoryId,
                )
            }

            is CategoriesScreenUIEvent.NavigateToAddCategoryScreen -> {
                navigateToAddCategoryScreen(
                    transactionType = uiEvent.transactionType,
                )
            }

            is CategoriesScreenUIEvent.NavigateToEditCategoryScreen -> {
                navigateToEditCategoryScreen(
                    categoryId = uiEvent.categoryId,
                )
            }

            CategoriesScreenUIEvent.NavigateUp -> {
                navigateUp()
            }

            is CategoriesScreenUIEvent.SetDefaultCategoryIdInDataStore -> {
                setDefaultCategoryIdInDataStore(
                    defaultCategoryId = uiEvent.defaultCategoryId,
                    transactionType = uiEvent.transactionType,
                )
            }

            is CategoriesScreenUIEvent.UpdateSelectedTabIndex -> {
                updateSelectedTabIndex(
                    updatedSelectedTabIndex = uiEvent.updatedSelectedTabIndex,
                )
            }
        }
    }

    private fun deleteCategory(
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

    private fun navigateToAddCategoryScreen(
        transactionType: String,
    ) {
        navigator.navigateToAddCategoryScreen(
            transactionType = transactionType,
        )
    }

    private fun navigateToEditCategoryScreen(
        categoryId: Int,
    ) {
        navigator.navigateToEditCategoryScreen(
            categoryId = categoryId,
        )
    }

    private fun navigateUp() {
        navigator.navigateUp()
    }

    private fun setDefaultCategoryIdInDataStore(
        defaultCategoryId: Int,
        transactionType: TransactionType,
    ) {
        viewModelScope.launch(
            context = dispatcherProvider.io,
        ) {
            when (transactionType) {
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

                TransactionType.TRANSFER -> {}

                TransactionType.ADJUSTMENT -> {}

                TransactionType.REFUND -> {}
            }
        }
    }

    private fun updateSelectedTabIndex(
        updatedSelectedTabIndex: Int,
    ) {
        selectedTabIndex.value = updatedSelectedTabIndex
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
