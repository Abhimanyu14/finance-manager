package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.logError
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.viewmodel.CategoriesScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.viewmodel.CategoriesScreenViewModelImpl

@Composable
fun CategoriesScreen(
    screenViewModel: CategoriesScreenViewModel = hiltViewModel<CategoriesScreenViewModelImpl>(),
) {
    logError(
        message = "Inside CategoriesScreen",
    )
    val defaultExpenseCategoryId: Int? by screenViewModel.defaultExpenseCategoryId.collectAsStateWithLifecycle(
        initialValue = null,
    )
    val defaultIncomeCategoryId: Int? by screenViewModel.defaultIncomeCategoryId.collectAsStateWithLifecycle(
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
    val expenseCategories: List<Category> by screenViewModel.expenseCategories.collectAsStateWithLifecycle(
        initialValue = emptyList(),
    )
    val incomeCategories: List<Category> by screenViewModel.incomeCategories.collectAsStateWithLifecycle(
        initialValue = emptyList(),
    )

    LaunchedEffect(
        key1 = Unit,
    ) {
        screenViewModel.trackScreen()
    }

    CategoriesScreenView(
        data = CategoriesScreenViewData(
            defaultExpenseCategoryId = defaultExpenseCategoryId,
            defaultIncomeCategoryId = defaultIncomeCategoryId,
            selectedTabIndex = selectedTabIndex,
            expenseCategoryIsUsedInTransactions = expenseCategoryIsUsedInTransactions,
            incomeCategoryIsUsedInTransactions = incomeCategoryIsUsedInTransactions,
            expenseCategories = expenseCategories,
            incomeCategories = incomeCategories,
            navigationManager = screenViewModel.navigationManager,
            deleteCategory = { categoryId ->
                screenViewModel.deleteCategory(
                    id = categoryId,
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
