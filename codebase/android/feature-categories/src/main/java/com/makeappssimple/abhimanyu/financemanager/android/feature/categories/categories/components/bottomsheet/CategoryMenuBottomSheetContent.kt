package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.components.bottomsheet

import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.NavigationManager
import com.makeappssimple.abhimanyu.financemanager.android.core.navigation.util.navigateToEditCategoryScreen
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultExpenseCategory
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.util.isDefaultIncomeCategory
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
    val editItem = CategoryMenuBottomSheetItemData(
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
    val setAsDefaultItem = CategoryMenuBottomSheetItemData(
        text = stringResource(
            id = R.string.bottom_sheet_category_menu_set_as_default_category,
        ),
        onClick = {
            onSetAsDefaultClick()
        },
    )
    val deleteItem = CategoryMenuBottomSheetItemData(
        text = stringResource(
            id = R.string.bottom_sheet_category_menu_delete,
        ),
        onClick = {
            onDeleteClick()
        },
    )

    val items = mutableListOf<CategoryMenuBottomSheetItemData>()

    items.add(editItem)
    if (!isDefault) {
        items.add(setAsDefaultItem)
    }
    if (
        !isDefaultExpenseCategory(
            category = categoryTitle,
        ) && !isDefaultIncomeCategory(
            category = categoryTitle,
        ) && deleteEnabled
    ) {
        items.add(deleteItem)
    }

    CategoryMenuBottomSheet(
        items = items,
    )
}
