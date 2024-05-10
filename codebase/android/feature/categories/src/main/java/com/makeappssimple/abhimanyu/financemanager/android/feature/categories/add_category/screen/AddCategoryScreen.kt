package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.screen.AddOrEditCategoryScreenUI
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.screen.AddOrEditCategoryScreenUIData
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.screen.AddOrEditCategoryScreenUIEvent
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.screen.rememberAddOrEditCategoryScreenUIState
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_or_edit_category.viewmodel.AddOrEditCategoryScreenViewModel

@Composable
public fun AddCategoryScreen(
    screenViewModel: AddOrEditCategoryScreenViewModel = hiltViewModel(),
) {
    val viewModel = remember {
        screenViewModel
    }
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside AddCategoryScreen",
    )

    val screenUIData: MyResult<AddOrEditCategoryScreenUIData>? by viewModel.screenUIData.collectAsStateWithLifecycle()
    val uiState = rememberAddOrEditCategoryScreenUIState(
        data = screenUIData,
        isEdit = false,
    )
    val handleUIEvent = remember(
        key1 = viewModel,
        key2 = uiState,
    ) {
        { uiEvent: AddOrEditCategoryScreenUIEvent ->
            when (uiEvent) {
                is AddOrEditCategoryScreenUIEvent.OnCtaButtonClick -> {
                    viewModel.insertCategory()
                }

                is AddOrEditCategoryScreenUIEvent.OnClearTitleButtonClick -> {
                    viewModel.clearTitle()
                }

                is AddOrEditCategoryScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                    viewModel.navigateUp()
                }

                is AddOrEditCategoryScreenUIEvent.OnEmojiUpdated -> {
                    viewModel.updateEmoji(
                        updatedEmoji = uiEvent.updatedEmoji,
                    )
                }

                is AddOrEditCategoryScreenUIEvent.OnEmojiBottomSheetSearchTextUpdated -> {
                    viewModel.updateSearchText(
                        updatedSearchText = uiEvent.updatedSearchText,
                    )
                }

                is AddOrEditCategoryScreenUIEvent.OnSelectedTransactionTypeIndexUpdated -> {
                    viewModel.updateSelectedTransactionTypeIndex(
                        updatedIndex = uiEvent.updatedIndex,
                    )
                }

                is AddOrEditCategoryScreenUIEvent.OnTitleUpdated -> {
                    viewModel.updateTitle(
                        updatedTitle = uiEvent.updatedTitle,
                    )
                }
            }
        }
    }

    LaunchedEffect(
        key1 = Unit,
    ) {
        viewModel.initViewModel()
    }

    AddOrEditCategoryScreenUI(
        uiState = uiState,
        handleUIEvent = handleUIEvent,
    )
}
