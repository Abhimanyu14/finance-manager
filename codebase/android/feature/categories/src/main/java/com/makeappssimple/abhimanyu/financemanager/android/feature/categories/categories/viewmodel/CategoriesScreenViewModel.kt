package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.groupBy
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.Sextuple
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.DeleteCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.GetAllCategoriesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.CheckIfCategoryIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.tabrow.MyTabData
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.Navigator
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.griditem.CategoriesGridItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultInvestmentCategory
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.bottomsheet.CategoriesScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.state.CategoriesScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.state.CategoriesScreenUIStateAndStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.state.CategoriesScreenUIStateEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
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
    private val _categoriesGridItemDataMap: MutableStateFlow<ImmutableMap<TransactionType, ImmutableList<CategoriesGridItemData>>> =
        MutableStateFlow(
            value = persistentMapOf(),
        )
    private val categoriesGridItemDataMap: StateFlow<ImmutableMap<TransactionType, ImmutableList<CategoriesGridItemData>>> =
        _categoriesGridItemDataMap

    // region UI data
    private val isLoading: MutableStateFlow<Boolean> = MutableStateFlow(
        value = true,
    )
    private val screenBottomSheetType: MutableStateFlow<CategoriesScreenBottomSheetType> =
        MutableStateFlow(
            value = CategoriesScreenBottomSheetType.None,
        )
    private val categoryIdToDelete: MutableStateFlow<Int?> = MutableStateFlow(
        value = null,
    )
    private val clickedItemId: MutableStateFlow<Int?> = MutableStateFlow(
        value = null,
    )
    private val selectedCategoryTypeIndex: MutableStateFlow<Int> = MutableStateFlow(
        value = 0,
    )
    // endregion

    internal val uiStateAndStateEvents: MutableStateFlow<CategoriesScreenUIStateAndStateEvents> =
        MutableStateFlow(
            value = CategoriesScreenUIStateAndStateEvents(),
        )

    internal fun initViewModel() {
        fetchData()
        observeData()
    }

    private fun fetchData() {
        val defaultDataId: Flow<DefaultDataId?> = myPreferencesRepository.getDefaultDataIdFlow()
        val categoriesTransactionTypeMap: Flow<Map<TransactionType, ImmutableList<Category>>> =
            getAllCategoriesFlowUseCase()
                .map { categories ->
                    categories.groupBy { category ->
                        category.transactionType
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
                        .map { category ->
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
                val incomeCategoriesGridItemDataList =
                    categoriesTransactionTypeMap[TransactionType.INCOME]
                        ?.sortedBy {
                            it.title
                        }
                        .map { category ->
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
                val investmentCategoriesGridItemDataList =
                    categoriesTransactionTypeMap[TransactionType.INVESTMENT]
                        ?.sortedBy {
                            it.title
                        }
                        .map { category ->
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
                _categoriesGridItemDataMap.update {
                    persistentMapOf(
                        TransactionType.EXPENSE to expenseCategoriesGridItemDataList,
                        TransactionType.INCOME to incomeCategoriesGridItemDataList,
                        TransactionType.INVESTMENT to investmentCategoriesGridItemDataList,
                    )
                }
            }
        }
        viewModelScope.launch {
            isLoading.update {
                false
            }
        }
    }

    private fun observeData() {
        observeForUiStateAndStateEventsChanges()
    }

    private fun observeForUiStateAndStateEventsChanges() {
        viewModelScope.launch {
            com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combine(
                isLoading,
                screenBottomSheetType,
                categoryIdToDelete,
                clickedItemId,
                selectedCategoryTypeIndex,
                categoriesGridItemDataMap,
            ) {
                    isLoading,
                    screenBottomSheetType,
                    categoryIdToDelete,
                    clickedItemId,
                    selectedCategoryTypeIndex,
                    categoriesGridItemDataMap,
                ->
                Sextuple(
                    isLoading,
                    screenBottomSheetType,
                    categoryIdToDelete,
                    clickedItemId,
                    selectedCategoryTypeIndex,
                    categoriesGridItemDataMap,
                )
            }.collectLatest {
                    (
                        isLoading,
                        screenBottomSheetType,
                        categoryIdToDelete,
                        clickedItemId,
                        selectedCategoryTypeIndex,
                        categoriesGridItemDataMap,
                    ),
                ->
                val validTransactionTypes = persistentListOf(
                    TransactionType.EXPENSE,
                    TransactionType.INCOME,
                    TransactionType.INVESTMENT,
                )

                uiStateAndStateEvents.update {
                    CategoriesScreenUIStateAndStateEvents(
                        state = CategoriesScreenUIState(
                            isBottomSheetVisible = screenBottomSheetType != CategoriesScreenBottomSheetType.None,
                            screenBottomSheetType = screenBottomSheetType,
                            isLoading = isLoading,
                            categoryIdToDelete = categoryIdToDelete,
                            clickedItemId = clickedItemId,
                            selectedTabIndex = selectedCategoryTypeIndex,
                            tabData = validTransactionTypes.map {
                                MyTabData(
                                    title = it.title,
                                )
                            },
                            validTransactionTypes = validTransactionTypes,
                            categoriesGridItemDataMap = categoriesGridItemDataMap,
                        ),
                        events = CategoriesScreenUIStateEvents(
                            deleteCategory = ::deleteCategory,
                            navigateToAddCategoryScreen = ::navigateToAddCategoryScreen,
                            navigateToEditCategoryScreen = ::navigateToEditCategoryScreen,
                            navigateUp = ::navigateUp,
                            resetScreenBottomSheetType = ::resetScreenBottomSheetType,
                            setCategoryIdToDelete = ::setCategoryIdToDelete,
                            setClickedItemId = ::setClickedItemId,
                            setDefaultCategoryIdInDataStore = ::setDefaultCategoryIdInDataStore,
                            setScreenBottomSheetType = ::setScreenBottomSheetType,
                            setSelectedCategoryTypeIndex = ::setSelectedCategoryTypeIndex,
                        ),
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

    // region state events
    private fun deleteCategory(
        id: Int,
    ) {
        viewModelScope.launch {
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

    private fun resetScreenBottomSheetType() {
        setScreenBottomSheetType(
            updatedCategoriesScreenBottomSheetType = CategoriesScreenBottomSheetType.None,
        )
    }

    private fun setCategoryIdToDelete(
        updatedCategoryIdToDelete: Int?,
    ) {
        categoryIdToDelete.update {
            updatedCategoryIdToDelete
        }
    }

    private fun setClickedItemId(
        updatedClickedItemId: Int?,
    ) {
        clickedItemId.update {
            updatedClickedItemId
        }
    }

    private fun setDefaultCategoryIdInDataStore(
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

    private fun setScreenBottomSheetType(
        updatedCategoriesScreenBottomSheetType: CategoriesScreenBottomSheetType,
    ) {
        screenBottomSheetType.update {
            updatedCategoriesScreenBottomSheetType
        }
    }

    private fun setSelectedCategoryTypeIndex(
        updatedSelectedCategoryTypeIndex: Int,
    ) {
        selectedCategoryTypeIndex.update {
            updatedSelectedCategoryTypeIndex
        }
    }
    // endregion
}
