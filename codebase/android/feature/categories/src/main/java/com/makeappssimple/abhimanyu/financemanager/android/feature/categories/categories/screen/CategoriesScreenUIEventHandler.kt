package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen

import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.viewmodel.CategoriesScreenViewModel

public class CategoriesScreenUIEventHandler internal constructor(
    private val viewModel: CategoriesScreenViewModel,
    private val uiStateAndEvents: CategoriesScreenUIStateAndEvents,
) {
    public fun handleUIEvent(
        uiEvent: CategoriesScreenUIEvent,
    ) {
        when (uiEvent) {
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
                uiStateAndEvents.events.setSelectedCategoryTypeIndex(
                    uiEvent.updatedSelectedTabIndex,
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
