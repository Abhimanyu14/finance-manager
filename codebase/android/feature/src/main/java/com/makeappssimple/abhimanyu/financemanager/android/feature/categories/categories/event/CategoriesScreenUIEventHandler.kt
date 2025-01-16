package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.event

import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIEventHandler
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.bottomsheet.CategoriesScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.state.CategoriesScreenUIStateEvents

internal class CategoriesScreenUIEventHandler internal constructor(
    private val uiStateEvents: CategoriesScreenUIStateEvents,
) : ScreenUIEventHandler<CategoriesScreenUIEvent> {
    override fun handleUIEvent(
        uiEvent: CategoriesScreenUIEvent,
    ) {
        when (uiEvent) {
            is CategoriesScreenUIEvent.OnNavigationBackButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
            }

            is CategoriesScreenUIEvent.OnSnackbarDismissed -> {
                uiStateEvents.resetScreenSnackbarType()
            }

            is CategoriesScreenUIEvent.OnFloatingActionButtonClick -> {
                uiStateEvents.navigateToAddCategoryScreen(uiEvent.transactionType)
            }

            is CategoriesScreenUIEvent.OnCategoriesGridItemClick -> {
                uiEvent.categoryId?.let {
                    if (uiEvent.isEditVisible || uiEvent.isSetAsDefaultVisible || uiEvent.isDeleteVisible) {
                        uiStateEvents.setScreenBottomSheetType(
                            CategoriesScreenBottomSheetType.Menu(
                                isDeleteVisible = uiEvent.isDeleteVisible,
                                isEditVisible = uiEvent.isEditVisible,
                                isSetAsDefaultVisible = uiEvent.isSetAsDefaultVisible,
                                categoryId = uiEvent.categoryId,
                            )
                        )
                        uiStateEvents.setClickedItemId(uiEvent.categoryId)
                    }
                }
            }

            is CategoriesScreenUIEvent.OnCategoriesSetAsDefaultConfirmationBottomSheet.NegativeButtonClick -> {
                uiStateEvents.setClickedItemId(null)
                uiStateEvents.resetScreenBottomSheetType()
            }

            is CategoriesScreenUIEvent.OnCategoriesSetAsDefaultConfirmationBottomSheet.PositiveButtonClick -> {
                uiStateEvents.setDefaultCategoryIdInDataStore(uiEvent.selectedTabIndex)
                uiStateEvents.setClickedItemId(null)
                uiStateEvents.resetScreenBottomSheetType()
            }

            is CategoriesScreenUIEvent.OnCategoryMenuBottomSheet.DeleteButtonClick -> {
                uiStateEvents.setCategoryIdToDelete(uiEvent.categoryId)
                uiStateEvents.setScreenBottomSheetType(
                    CategoriesScreenBottomSheetType.DeleteConfirmation
                )
            }

            is CategoriesScreenUIEvent.OnCategoryMenuBottomSheet.EditButtonClick -> {
                uiStateEvents.resetScreenBottomSheetType()
                uiStateEvents.navigateToEditCategoryScreen(uiEvent.categoryId)
            }

            is CategoriesScreenUIEvent.OnCategoryMenuBottomSheet.SetAsDefaultButtonClick -> {
                uiStateEvents.setClickedItemId(uiEvent.categoryId)
                uiStateEvents.setScreenBottomSheetType(
                    CategoriesScreenBottomSheetType.SetAsDefaultConfirmation
                )
            }

            is CategoriesScreenUIEvent.OnTopAppBarNavigationButtonClick -> {
                uiStateEvents.navigateUp()
            }

            is CategoriesScreenUIEvent.OnCategoriesDeleteConfirmationBottomSheet.NegativeButtonClick -> {
                uiStateEvents.setCategoryIdToDelete(null)
                uiStateEvents.resetScreenBottomSheetType()
            }

            is CategoriesScreenUIEvent.OnCategoriesDeleteConfirmationBottomSheet.PositiveButtonClick -> {
                uiStateEvents.deleteCategory()
                uiStateEvents.setCategoryIdToDelete(null)
                uiStateEvents.resetScreenBottomSheetType()
            }
        }
    }
}
