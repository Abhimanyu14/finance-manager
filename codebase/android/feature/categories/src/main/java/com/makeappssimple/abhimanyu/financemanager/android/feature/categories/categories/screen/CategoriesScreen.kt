package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.viewmodel.CategoriesScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.viewmodel.CategoriesScreenViewModelImpl

@Composable
fun CategoriesScreen(
    screenViewModel: CategoriesScreenViewModel = hiltViewModel<CategoriesScreenViewModelImpl>(),
) {
    screenViewModel.myLogger.logError(
        message = "Inside CategoriesScreen",
    )

    val screenUIData: MyResult<CategoriesScreenUIData>? by screenViewModel.screenUIData.collectAsStateWithLifecycle()

    CategoriesScreenUI(
        events = CategoriesScreenUIEvents(
            deleteCategory = screenViewModel::deleteCategory,
            navigateToAddCategoryScreen = screenViewModel::navigateToAddCategoryScreen,
            navigateToEditCategoryScreen = screenViewModel::navigateToEditCategoryScreen,
            navigateUp = screenViewModel::navigateUp,
            setDefaultCategoryIdInDataStore = screenViewModel::setDefaultCategoryIdInDataStore,
            updateSelectedTabIndex = screenViewModel::updateSelectedTabIndex,
        ),
        uiState = rememberCategoriesScreenUIState(
            data = screenUIData,
        ),
        state = rememberCommonScreenUIState(),
    )
}
