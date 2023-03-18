package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.components.bottomsheet

import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util.navigateToEditCategoryScreen
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultIncomeCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultInvestmentCategory
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.R
import kotlinx.coroutines.CoroutineScope

@Composable
internal fun CategoryMenuBottomSheetContent(
    deleteEnabled: Boolean,
    isDefault: Boolean,
    coroutineScope: CoroutineScope,
    categoryId: Int,
    modalBottomSheetState: ModalBottomSheetState,
    navigationManager: NavigationManager,
    categoryTitle: String,
    onDeleteClick: () -> Unit,
    onSetAsDefaultClick: () -> Unit,
    resetBottomSheetType: () -> Unit,
) {
    val items = mutableListOf<CategoryMenuBottomSheetItemData>()
    if (!isDefaultExpenseCategory(categoryTitle) &&
        !isDefaultIncomeCategory(categoryTitle) &&
        !isDefaultInvestmentCategory(categoryTitle)
    ) {
        items.add(
            CategoryMenuBottomSheetItemData(
                text = stringResource(
                    id = R.string.bottom_sheet_category_menu_edit,
                ),
                onClick = {
                    toggleModalBottomSheetState(
                        coroutineScope = coroutineScope,
                        modalBottomSheetState = modalBottomSheetState,
                    ) {
                        resetBottomSheetType()
                        navigateToEditCategoryScreen(
                            navigationManager = navigationManager,
                            categoryId = categoryId,
                        )
                    }
                },
            )
        )
    }
    if (!isDefault) {
        items.add(
            CategoryMenuBottomSheetItemData(
                text = stringResource(
                    id = R.string.bottom_sheet_category_menu_set_as_default_category,
                ),
                onClick = {
                    onSetAsDefaultClick()
                },
            )
        )
    }
    if (
        !isDefaultExpenseCategory(
            category = categoryTitle,
        ) && !isDefaultIncomeCategory(
            category = categoryTitle,
        ) && deleteEnabled
    ) {
        items.add(
            CategoryMenuBottomSheetItemData(
                text = stringResource(
                    id = R.string.bottom_sheet_category_menu_delete,
                ),
                onClick = {
                    onDeleteClick()
                },
            )
        )
    }

    // Close bottom sheet if there are no menu items
    if (items.isEmpty()) {
        toggleModalBottomSheetState(
            coroutineScope = coroutineScope,
            modalBottomSheetState = modalBottomSheetState,
        ) {
            resetBottomSheetType()
        }
    }

    CategoryMenuBottomSheet(
        items = items,
    )
}
