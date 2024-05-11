package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.makeappssimple.abhimanyu.financemanager.android.core.common.result.MyResult
import com.makeappssimple.abhimanyu.financemanager.android.core.logger.LocalMyLogger
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.viewmodel.CategoriesScreenViewModel

@Composable
public fun CategoriesScreen(
    screenViewModel: CategoriesScreenViewModel = hiltViewModel(),
) {
    val viewModel = remember {
        screenViewModel
    }
    val myLogger = LocalMyLogger.current
    myLogger.logError(
        message = "Inside CategoriesScreen",
    )

    val screenUIData: MyResult<CategoriesScreenUIData>? by viewModel.screenUIData.collectAsStateWithLifecycle()
    val uiStateAndEvents = rememberCategoriesScreenUIStateAndEvents(
        data = screenUIData,
    )
    val handleUIEvent = remember(
        key1 = viewModel,
        key2 = uiStateAndEvents,
    ) {
        { uiEvent: CategoriesScreenUIEvent ->
            when (uiEvent) {
                is CategoriesScreenUIEvent.OnBottomSheetDismissed -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is CategoriesScreenUIEvent.OnNavigationBackButtonClick -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is CategoriesScreenUIEvent.OnFloatingActionButtonClick -> {
                    viewModel.navigateToAddCategoryScreen(
                        transactionType = uiEvent.transactionType,
                    )
                }

                is CategoriesScreenUIEvent.OnCategoriesGridItemClick -> {
                    uiEvent.categoryId?.let {
                        if (uiEvent.isEditVisible || uiEvent.isSetAsDefaultVisible || uiEvent.isDeleteVisible) {
                            uiStateAndEvents.events.setScreenBottomSheetType(
                                CategoriesScreenBottomSheetType.Menu(
                                    isDeleteVisible = uiEvent.isDeleteVisible,
                                    isEditVisible = uiEvent.isEditVisible,
                                    isSetAsDefaultVisible = uiEvent.isSetAsDefaultVisible,
                                    categoryId = uiEvent.categoryId,
                                )
                            )
                            uiStateAndEvents.events.setClickedItemId(uiEvent.categoryId)
                        }
                    }
                    Unit
                }

                is CategoriesScreenUIEvent.OnSelectedTabIndexUpdated -> {
                    viewModel.updateSelectedTabIndex(
                        updatedSelectedTabIndex = uiEvent.updatedSelectedTabIndex,
                    )
                }

                is CategoriesScreenUIEvent.OnCategoriesSetAsDefaultConfirmationBottomSheet.NegativeButtonClick -> {
                    uiStateAndEvents.events.setClickedItemId(null)
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is CategoriesScreenUIEvent.OnCategoriesSetAsDefaultConfirmationBottomSheet.PositiveButtonClick -> {
                    uiStateAndEvents.state.clickedItemId?.let { clickedItemIdValue ->
                        viewModel.setDefaultCategoryIdInDataStore(
                            defaultCategoryId = clickedItemIdValue,
                            transactionType = uiStateAndEvents.state.validTransactionTypes[uiStateAndEvents.state.selectedTabIndex],
                        )
                    }
                    uiStateAndEvents.events.setClickedItemId(null)
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is CategoriesScreenUIEvent.OnCategoryMenuBottomSheet.DeleteButtonClick -> {
                    uiStateAndEvents.events.setCategoryIdToDelete(uiEvent.categoryId)
                    uiStateAndEvents.events.setScreenBottomSheetType(CategoriesScreenBottomSheetType.DeleteConfirmation)
                }

                is CategoriesScreenUIEvent.OnCategoryMenuBottomSheet.EditButtonClick -> {
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                    viewModel.navigateToEditCategoryScreen(
                        categoryId = uiEvent.categoryId,
                    )
                }

                is CategoriesScreenUIEvent.OnCategoryMenuBottomSheet.SetAsDefaultButtonClick -> {
                    uiStateAndEvents.events.setClickedItemId(uiEvent.categoryId)
                    uiStateAndEvents.events.setScreenBottomSheetType(CategoriesScreenBottomSheetType.SetAsDefaultConfirmation)
                }

                is CategoriesScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                    viewModel.navigateUp()
                }

                is CategoriesScreenUIEvent.OnCategoriesDeleteConfirmationBottomSheet.NegativeButtonClick -> {
                    uiStateAndEvents.events.setCategoryIdToDelete(null)
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }

                is CategoriesScreenUIEvent.OnCategoriesDeleteConfirmationBottomSheet.PositiveButtonClick -> {
                    uiStateAndEvents.state.categoryIdToDelete?.let { categoryIdToDeleteValue ->
                        viewModel.deleteCategory(
                            id = categoryIdToDeleteValue,
                        )
                    }
                    uiStateAndEvents.events.setCategoryIdToDelete(null)
                    uiStateAndEvents.events.resetScreenBottomSheetType()
                }
            }
        }
    }

    CategoriesScreenUI(
        uiState = uiStateAndEvents.state,
        handleUIEvent = handleUIEvent,
    )
}
