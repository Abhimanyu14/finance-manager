package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.add_category.viewmodel.AddCategoryScreenViewModel

@Composable
public fun AddCategoryScreen(
    screenViewModel: AddCategoryScreenViewModel = hiltViewModel(),
) {
    val viewModel = remember {
        screenViewModel
    }
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside AddCategoryScreen",
    )

    val screenUIData: MyResult<AddCategoryScreenUIData>? by viewModel.screenUIData.collectAsStateWithLifecycle()
    val uiStateAndEvents = rememberAddCategoryScreenUIStateAndEvents(
        data = screenUIData,
    )
    val handleUIEvent = remember(
        key1 = viewModel,
        key2 = uiStateAndEvents,
    ) {
        { uiEvent: AddCategoryScreenUIEvent ->
            when (uiEvent) {
                is AddCategoryScreenUIEvent.OnBottomSheetDismissed -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                    viewModel.updateSearchText(
                        updatedSearchText = "",
                    )
                }

                is AddCategoryScreenUIEvent.OnNavigationBackButtonClick -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is AddCategoryScreenUIEvent.OnCtaButtonClick -> {
                    viewModel.insertCategory()
                }

                is AddCategoryScreenUIEvent.OnClearTitleButtonClick -> {
                    viewModel.clearTitle()
                }

                is AddCategoryScreenUIEvent.OnEmojiCircleClick -> {
                    uiStateAndEvents.events.setScreenBottomSheetType(
                        AddCategoryScreenBottomSheetType.SelectEmoji
                    )
                }

                is AddCategoryScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                    viewModel.navigateUp()
                }

                is AddCategoryScreenUIEvent.OnEmojiUpdated -> {
                    viewModel.updateEmoji(
                        updatedEmoji = uiEvent.updatedEmoji,
                    )
                }

                is AddCategoryScreenUIEvent.OnEmojiBottomSheetSearchTextUpdated -> {
                    viewModel.updateSearchText(
                        updatedSearchText = uiEvent.updatedSearchText,
                    )
                }

                is AddCategoryScreenUIEvent.OnSelectedTransactionTypeIndexUpdated -> {
                    viewModel.updateSelectedTransactionTypeIndex(
                        updatedIndex = uiEvent.updatedIndex,
                    )
                }

                is AddCategoryScreenUIEvent.OnTitleUpdated -> {
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

    AddCategoryScreenUI(
        uiState = uiStateAndEvents.state,
        handleUIEvent = handleUIEvent,
    )
}
