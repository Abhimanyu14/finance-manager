package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.viewmodel.CategoriesScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.viewmodel.CategoriesScreenViewModelImpl

@Composable
fun CategoriesScreen(
    screenViewModel: CategoriesScreenViewModel = hiltViewModel<CategoriesScreenViewModelImpl>(),
) {
    screenViewModel.logger.logError(
        message = "Inside CategoriesScreen",
    )
    val defaultExpenseCategoryId: Int? by screenViewModel.defaultExpenseCategoryId.collectAsStateWithLifecycle(
        initialValue = null,
    )
    val defaultIncomeCategoryId: Int? by screenViewModel.defaultIncomeCategoryId.collectAsStateWithLifecycle(
        initialValue = null,
    )
    val defaultInvestmentCategoryId: Int? by screenViewModel.defaultInvestmentCategoryId.collectAsStateWithLifecycle(
        initialValue = null,
    )
    val selectedTabIndex: Int by screenViewModel.selectedTabIndex.collectAsStateWithLifecycle()
    val expenseCategoryIsUsedInTransactions: List<Boolean> by screenViewModel
        .expenseCategoryIsUsedInTransactions.collectAsStateWithLifecycle(
            initialValue = emptyList(),
        )
    val incomeCategoryIsUsedInTransactions: List<Boolean> by screenViewModel
        .incomeCategoryIsUsedInTransactions.collectAsStateWithLifecycle(
            initialValue = emptyList(),
        )
    val investmentCategoryIsUsedInTransactions: List<Boolean> by screenViewModel
        .investmentCategoryIsUsedInTransactions.collectAsStateWithLifecycle(
            initialValue = emptyList(),
        )
    val expenseCategories: List<Category> by screenViewModel.expenseCategories.collectAsStateWithLifecycle(
        initialValue = emptyList(),
    )
    val incomeCategories: List<Category> by screenViewModel.incomeCategories.collectAsStateWithLifecycle(
        initialValue = emptyList(),
    )
    val investmentCategories: List<Category> by screenViewModel.investmentCategories.collectAsStateWithLifecycle(
        initialValue = emptyList(),
    )

    CategoriesScreenView(
        data = CategoriesScreenViewData(
            defaultExpenseCategoryId = defaultExpenseCategoryId,
            defaultIncomeCategoryId = defaultIncomeCategoryId,
            defaultInvestmentCategoryId = defaultInvestmentCategoryId,
            selectedTabIndex = selectedTabIndex,
            expenseCategoryIsUsedInTransactions = expenseCategoryIsUsedInTransactions,
            incomeCategoryIsUsedInTransactions = incomeCategoryIsUsedInTransactions,
            investmentCategoryIsUsedInTransactions = investmentCategoryIsUsedInTransactions,
            expenseCategories = expenseCategories,
            incomeCategories = incomeCategories,
            investmentCategories = investmentCategories,
            deleteCategory = { categoryId ->
                screenViewModel.deleteCategory(
                    id = categoryId,
                )
            },
            navigateToAddCategoryScreen = { transactionType ->
                screenViewModel.navigationManager.navigate(
                    navigationCommand = MyNavigationDirections.AddCategory(
                        transactionType = transactionType,
                    )
                )
            },
            navigateToEditCategoryScreen = { categoryId ->
                screenViewModel.navigationManager.navigate(
                    navigationCommand = MyNavigationDirections.EditCategory(
                        categoryId = categoryId,
                    )
                )
            },
            navigateUp = {
                screenViewModel.navigationManager.navigate(
                    navigationCommand = MyNavigationDirections.NavigateUp
                )
            },
            setDefaultCategoryIdInDataStore = { defaultCategoryId: Int, transactionType: TransactionType ->
                screenViewModel.setDefaultCategoryIdInDataStore(
                    defaultCategoryId = defaultCategoryId,
                    transactionType = transactionType,
                )
            },
            updateSelectedTabIndex = { updatedSelectedTabIndex ->
                screenViewModel.updateSelectedTabIndex(
                    updatedSelectedTabIndex = updatedSelectedTabIndex,
                )
            },
        ),
        state = rememberCommonScreenViewState(),
    )
}
