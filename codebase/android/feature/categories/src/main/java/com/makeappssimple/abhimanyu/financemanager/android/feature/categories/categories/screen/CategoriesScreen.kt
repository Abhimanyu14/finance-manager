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
    val uiState = rememberCategoriesScreenUIState(
        data = screenUIData,
    )
    val handleUIEvent = remember(
        key1 = viewModel,
        key2 = uiState,
    ) {
        { uiEvent: CategoriesScreenUIEvent ->
            when (uiEvent) {
                is CategoriesScreenUIEvent.OnBottomSheetDismissed -> {
                    uiState.resetScreenBottomSheetType()
                }

                is CategoriesScreenUIEvent.OnNavigationBackButtonClick -> {
                    uiState.resetScreenBottomSheetType()
                }

                is CategoriesScreenUIEvent.OnCategoriesDeleteConfirmationBottomSheet.DeleteButtonClick -> {
                    viewModel.deleteCategory(
                        id = uiEvent.categoryId,
                    )
                }

                is CategoriesScreenUIEvent.OnCategoriesDeleteConfirmationBottomSheet.EditButtonClick -> {
                    viewModel.navigateToEditCategoryScreen(
                        categoryId = uiEvent.categoryId,
                    )
                }

                is CategoriesScreenUIEvent.OnCategoriesDeleteConfirmationBottomSheet.SetAsDefaultCategoryButtonClick -> {
                    viewModel.setDefaultCategoryIdInDataStore(
                        defaultCategoryId = uiEvent.defaultCategoryId,
                        transactionType = uiEvent.transactionType,
                    )
                }

                is CategoriesScreenUIEvent.OnFloatingActionButtonClick -> {
                    viewModel.navigateToAddCategoryScreen(
                        transactionType = uiEvent.transactionType,
                    )
                }

                is CategoriesScreenUIEvent.OnCategoriesGridItemClick -> {
                    uiEvent.categoryId?.let {
                        if (uiEvent.isEditVisible || uiEvent.isSetAsDefaultVisible || uiEvent.isDeleteVisible) {
                            uiState.setScreenBottomSheetType(
                                CategoriesScreenBottomSheetType.Menu(
                                    isDeleteVisible = uiEvent.isDeleteVisible,
                                    isEditVisible = uiEvent.isEditVisible,
                                    isSetAsDefaultVisible = uiEvent.isSetAsDefaultVisible,
                                    categoryId = uiEvent.categoryId,
                                )
                            )
                            uiState.setClickedItemId(uiEvent.categoryId)
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
                    uiState.setClickedItemId(null)
                    uiState.resetScreenBottomSheetType()
                }

                is CategoriesScreenUIEvent.OnCategoriesSetAsDefaultConfirmationBottomSheet.PositiveButtonClick -> {
                    uiState.clickedItemId?.let { clickedItemIdValue ->
                        viewModel.setDefaultCategoryIdInDataStore(
                            defaultCategoryId = clickedItemIdValue,
                            transactionType = uiState.validTransactionTypes[uiState.selectedTabIndex],
                        )
                    }
                    uiState.setClickedItemId(null)
                    uiState.resetScreenBottomSheetType()
                }

                is CategoriesScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                    viewModel.navigateUp()
                }
            }
        }
    }

    CategoriesScreenUI(
        uiState = uiState,
        handleUIEvent = handleUIEvent,
    )
}
