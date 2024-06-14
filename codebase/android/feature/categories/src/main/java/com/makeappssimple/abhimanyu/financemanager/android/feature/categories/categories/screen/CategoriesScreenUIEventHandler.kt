package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.screen

public class CategoriesScreenUIEventHandler internal constructor(
    private val uiStateAndStateEvents: CategoriesScreenUIStateAndStateEvents,
) {
    public fun handleUIEvent(
        uiEvent: CategoriesScreenUIEvent,
    ) {
        when (uiEvent) {
            is CategoriesScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
            }

            is CategoriesScreenUIEvent.OnFloatingActionButtonClick -> {
                uiStateAndStateEvents.events.navigateToAddCategoryScreen(uiEvent.transactionType)
            }

            is CategoriesScreenUIEvent.OnCategoriesGridItemClick -> {
                uiEvent.categoryId?.let {
                    if (uiEvent.isEditVisible || uiEvent.isSetAsDefaultVisible || uiEvent.isDeleteVisible) {
                        uiStateAndStateEvents.events.setScreenBottomSheetType(
                            CategoriesScreenBottomSheetType.Menu(
                                isDeleteVisible = uiEvent.isDeleteVisible,
                                isEditVisible = uiEvent.isEditVisible,
                                isSetAsDefaultVisible = uiEvent.isSetAsDefaultVisible,
                                categoryId = uiEvent.categoryId,
                            )
                        )
                        uiStateAndStateEvents.events.setClickedItemId(uiEvent.categoryId)
                    }
                }
            }

            is CategoriesScreenUIEvent.OnSelectedTabIndexUpdated -> {
                uiStateAndStateEvents.events.setSelectedCategoryTypeIndex(
                    uiEvent.updatedSelectedTabIndex,
                )
            }

            is CategoriesScreenUIEvent.OnCategoriesSetAsDefaultConfirmationBottomSheet.NegativeButtonClick -> {
                uiStateAndStateEvents.events.setClickedItemId(null)
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
            }

            is CategoriesScreenUIEvent.OnCategoriesSetAsDefaultConfirmationBottomSheet.PositiveButtonClick -> {
                uiStateAndStateEvents.state.clickedItemId?.let { clickedItemIdValue ->
                    uiStateAndStateEvents.events.setDefaultCategoryIdInDataStore(
                        clickedItemIdValue,
                        uiStateAndStateEvents.state.validTransactionTypes[uiStateAndStateEvents.state.selectedTabIndex],
                    )
                }
                uiStateAndStateEvents.events.setClickedItemId(null)
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
            }

            is CategoriesScreenUIEvent.OnCategoryMenuBottomSheet.DeleteButtonClick -> {
                uiStateAndStateEvents.events.setCategoryIdToDelete(uiEvent.categoryId)
                uiStateAndStateEvents.events.setScreenBottomSheetType(
                    CategoriesScreenBottomSheetType.DeleteConfirmation
                )
            }

            is CategoriesScreenUIEvent.OnCategoryMenuBottomSheet.EditButtonClick -> {
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
                uiStateAndStateEvents.events.navigateToEditCategoryScreen(uiEvent.categoryId)
            }

            is CategoriesScreenUIEvent.OnCategoryMenuBottomSheet.SetAsDefaultButtonClick -> {
                uiStateAndStateEvents.events.setClickedItemId(uiEvent.categoryId)
                uiStateAndStateEvents.events.setScreenBottomSheetType(
                    CategoriesScreenBottomSheetType.SetAsDefaultConfirmation
                )
            }

            is CategoriesScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateAndStateEvents.events.navigateUp()
            }

            is CategoriesScreenUIEvent.OnCategoriesDeleteConfirmationBottomSheet.NegativeButtonClick -> {
                uiStateAndStateEvents.events.setCategoryIdToDelete(null)
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
            }

            is CategoriesScreenUIEvent.OnCategoriesDeleteConfirmationBottomSheet.PositiveButtonClick -> {
                uiStateAndStateEvents.state.categoryIdToDelete?.let { categoryIdToDeleteValue ->
                    uiStateAndStateEvents.events.deleteCategory(categoryIdToDeleteValue)
                }
                uiStateAndStateEvents.events.setCategoryIdToDelete(null)
                uiStateAndStateEvents.events.resetScreenBottomSheetType()
            }
        }
    }
}
