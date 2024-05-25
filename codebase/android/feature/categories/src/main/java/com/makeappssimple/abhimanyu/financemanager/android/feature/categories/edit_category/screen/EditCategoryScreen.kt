package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.edit_category.viewmodel.EditCategoryScreenViewModel

@Composable
public fun EditCategoryScreen(
    screenViewModel: EditCategoryScreenViewModel = hiltViewModel(),
) {
    val viewModel = remember {
        screenViewModel
    }
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside EditCategoryScreen",
    )

    val screenUIData: MyResult<EditCategoryScreenUIData>? by viewModel.screenUIData.collectAsStateWithLifecycle()
    val uiStateAndEvents = rememberEditCategoryScreenUIStateAndEvents(
        data = screenUIData,
    )
    val handleUIEvent = remember(
        key1 = viewModel,
        key2 = uiStateAndEvents,
    ) {
        { uiEvent: EditCategoryScreenUIEvent ->
            when (uiEvent) {
                is EditCategoryScreenUIEvent.OnBottomSheetDismissed -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                    viewModel.updateSearchText(
                        updatedSearchText = "",
                    )
                }

                is EditCategoryScreenUIEvent.OnNavigationBackButtonClick -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is EditCategoryScreenUIEvent.OnCtaButtonClick -> {
                    viewModel.updateCategory()
                }

                is EditCategoryScreenUIEvent.OnClearTitleButtonClick -> {
                    viewModel.clearTitle()
                }

                is EditCategoryScreenUIEvent.OnEmojiCircleClick -> {
                    uiStateAndEvents.events.setScreenBottomSheetType(
                        EditCategoryScreenBottomSheetType.SelectEmoji
                    )
                }

                is EditCategoryScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                    viewModel.navigateUp()
                }

                is EditCategoryScreenUIEvent.OnEmojiUpdated -> {
                    viewModel.updateEmoji(
                        updatedEmoji = uiEvent.updatedEmoji,
                    )
                }

                is EditCategoryScreenUIEvent.OnEmojiBottomSheetSearchTextUpdated -> {
                    viewModel.updateSearchText(
                        updatedSearchText = uiEvent.updatedSearchText,
                    )
                }

                is EditCategoryScreenUIEvent.OnSelectedTransactionTypeIndexUpdated -> {
                    viewModel.updateSelectedTransactionTypeIndex(
                        updatedIndex = uiEvent.updatedIndex,
                    )
                }

                is EditCategoryScreenUIEvent.OnTitleUpdated -> {
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

    EditCategoryScreenUI(
        uiState = uiStateAndEvents.state,
        handleUIEvent = handleUIEvent,
    )
}
