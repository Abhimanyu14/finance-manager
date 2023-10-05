package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.rememberCommonScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.screen.AddOrEditCategoryScreenUI
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.screen.AddOrEditCategoryScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.screen.AddOrEditCategoryScreenUIEvent
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.screen.rememberAddOrEditCategoryScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.viewmodel.AddOrEditCategoryScreenViewModel
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.viewmodel.AddOrEditCategoryScreenViewModelImpl

@Composable
fun AddCategoryScreen(
    screenViewModel: AddOrEditCategoryScreenViewModel = hiltViewModel<AddOrEditCategoryScreenViewModelImpl>(),
) {
    val viewModel = remember {
        screenViewModel
    }
    viewModel.myLogger.logError(
        message = "Inside AddCategoryScreen",
    )

    val screenUIData: MyResult<AddOrEditCategoryScreenUIData>? by viewModel.screenUIData.collectAsStateWithLifecycle()
    val handleUIEvents = remember(
        key1 = viewModel,
    ) {
        { uiEvent: AddOrEditCategoryScreenUIEvent ->
            when (uiEvent) {
                AddOrEditCategoryScreenUIEvent.OnCtaButtonClick -> {
                    viewModel.insertCategory()
                }

                else -> {
                    viewModel.handleUIEvents(
                        uiEvent = uiEvent,
                    )
                }
            }
        }
    }

    AddOrEditCategoryScreenUI(
        uiState = rememberAddOrEditCategoryScreenUIState(
            data = screenUIData,
            isEdit = false,
        ),
        state = rememberCommonScreenUIState(),
        handleUIEvents = handleUIEvents,
    )
}
