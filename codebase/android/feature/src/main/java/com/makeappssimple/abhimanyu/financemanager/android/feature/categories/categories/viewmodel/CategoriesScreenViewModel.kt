package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.viewmodel

import androidx.lifecycle.viewModelScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.coroutines.di.ApplicationScope
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.combineAndCollectLatest
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.groupBy
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNull
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.preferences.MyPreferencesRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.DeleteCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.GetAllCategoriesFlowUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.category.SetDefaultCategoryUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.data.usecase.transaction.CheckIfCategoryIsUsedInTransactionsUseCase
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.component.tabrow.MyTabData
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LogKit
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.DefaultDataId
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationKit
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.griditem.CategoriesGridItemData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultInvestmentCategory
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.bottomsheet.CategoriesScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.state.CategoriesScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.state.CategoriesScreenUIStateEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CategoriesScreenViewModel @Inject constructor(
    @ApplicationScope coroutineScope: CoroutineScope,
    private val checkIfCategoryIsUsedInTransactionsUseCase: CheckIfCategoryIsUsedInTransactionsUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
    private val getAllCategoriesFlowUseCase: GetAllCategoriesFlowUseCase,
    private val myPreferencesRepository: MyPreferencesRepository,
    private val setDefaultCategoryUseCase: SetDefaultCategoryUseCase,
    private val navigationKit: NavigationKit,
    internal val logKit: LogKit,
) : ScreenViewModel(
    viewModelScope = coroutineScope,
), CategoriesScreenUIStateDelegate by CategoriesScreenUIStateDelegateImpl(
    coroutineScope = coroutineScope,
    deleteCategoryUseCase = deleteCategoryUseCase,
    setDefaultCategoryUseCase = setDefaultCategoryUseCase,
    navigationKit = navigationKit,
) {
    // region initial data
    private val categoriesGridItemDataMap: MutableStateFlow<ImmutableMap<TransactionType, ImmutableList<CategoriesGridItemData>>> =
        MutableStateFlow(
            value = persistentMapOf(),
        )
    // endregion

    // region uiStateAndStateEvents
    internal val uiState: MutableStateFlow<CategoriesScreenUIState> =
        MutableStateFlow(
            value = CategoriesScreenUIState(),
        )
    internal val uiStateEvents: CategoriesScreenUIStateEvents = CategoriesScreenUIStateEvents(
        deleteCategory = ::deleteCategory,
        navigateToAddCategoryScreen = ::navigateToAddCategoryScreen,
        navigateToEditCategoryScreen = ::navigateToEditCategoryScreen,
        navigateUp = ::navigateUp,
        resetScreenBottomSheetType = ::resetScreenBottomSheetType,
        resetScreenSnackbarType = ::resetScreenSnackbarType,
        setCategoryIdToDelete = ::updateCategoryIdToDelete,
        setClickedItemId = ::updateClickedItemId,
        setDefaultCategoryIdInDataStore = ::updateDefaultCategoryIdInDataStore,
        setScreenBottomSheetType = ::updateScreenBottomSheetType,
        setScreenSnackbarType = ::updateScreenSnackbarType,
    )
    // endregion

    // region initViewModel
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
            combineAndCollectLatest(
                defaultDataId,
                categoriesTransactionTypeMap
            ) { (defaultDataId, categoriesTransactionTypeMap) ->
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
                categoriesGridItemDataMap.update {
                    persistentMapOf(
                        TransactionType.EXPENSE to expenseCategoriesGridItemDataList,
                        TransactionType.INCOME to incomeCategoriesGridItemDataList,
                        TransactionType.INVESTMENT to investmentCategoriesGridItemDataList,
                    )
                }
            }
        }
        viewModelScope.launch {
            startLoading()
            completeLoading()
        }
    }

    private fun observeData() {
        observeForUiStateAndStateEvents()
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
    // endregion

    // region observeForUiStateAndStateEvents
    private fun observeForUiStateAndStateEvents() {
        observeForRefreshSignal()
        observeForCategoriesGridItemDataMap()
    }

    private fun observeForRefreshSignal() {
        viewModelScope.launch {
            refreshSignal.collectLatest {
                updateUiStateAndStateEvents(
                    categoriesGridItemDataMap = categoriesGridItemDataMap.value,
                )
            }
        }
    }

    private fun observeForCategoriesGridItemDataMap() {
        viewModelScope.launch {
            categoriesGridItemDataMap.collectLatest { categoriesGridItemDataMap ->
                updateUiStateAndStateEvents(
                    categoriesGridItemDataMap = categoriesGridItemDataMap,
                )
            }
        }
    }

    private fun updateUiStateAndStateEvents(
        categoriesGridItemDataMap: ImmutableMap<TransactionType, ImmutableList<CategoriesGridItemData>>,
    ) {
        val tabData = validTransactionTypes.map {
            MyTabData(
                title = it.title,
            )
        }

        uiState.update {
            CategoriesScreenUIState(
                isBottomSheetVisible = screenBottomSheetType != CategoriesScreenBottomSheetType.None,
                screenBottomSheetType = screenBottomSheetType,
                screenSnackbarType = screenSnackbarType,
                isLoading = isLoading,
                categoryIdToDelete = categoryIdToDelete,
                clickedItemId = clickedItemId,
                tabData = tabData,
                validTransactionTypes = validTransactionTypes,
                categoriesGridItemDataMap = categoriesGridItemDataMap,
            )
        }
    }
    // endregion
}
