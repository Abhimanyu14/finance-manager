package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.MyNavigationDirections
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenViewState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.grid_item.CategoriesGridItemData
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.viewmodel.CategoriesScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.viewmodel.CategoriesScreenViewModelImpl

@Composable
fun CategoriesScreen(
    screenViewModel: CategoriesScreenViewModel = hiltViewModel<CategoriesScreenViewModelImpl>(),
) {
    screenViewModel.logger.logError(
        message = "Inside CategoriesScreen",
    )
    val selectedTabIndex: Int by screenViewModel.selectedTabIndex.collectAsStateWithLifecycle()
    val categoriesGridItemDataMap: Map<TransactionType, List<CategoriesGridItemData>> by screenViewModel
        .categoriesGridItemDataMap.collectAsStateWithLifecycle(
            initialValue = emptyMap(),
        )

    CategoriesScreenView(
        data = CategoriesScreenViewData(
            selectedTabIndex = selectedTabIndex,
            categoriesGridItemDataMap = categoriesGridItemDataMap,
        ),
        events = CategoriesScreenViewEvents(
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
