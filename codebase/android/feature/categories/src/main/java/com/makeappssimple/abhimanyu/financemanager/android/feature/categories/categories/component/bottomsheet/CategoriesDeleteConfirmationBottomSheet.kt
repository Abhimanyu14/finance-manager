package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.component.bottomsheet

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.MyConfirmationBottomSheetUI
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.R

@Composable
internal fun CategoriesDeleteConfirmationBottomSheet(
    deleteCategory: () -> Unit,
    resetBottomSheetType: () -> Unit,
    resetCategoryIdToDelete: () -> Unit,
) {
    MyConfirmationBottomSheetUI(
        title = stringResource(
            id = R.string.screen_categories_bottom_sheet_delete_title,
        ),
        message = stringResource(
            id = R.string.screen_categories_bottom_sheet_delete_message,
        ),
        positiveButtonText = stringResource(
            id = R.string.screen_categories_bottom_sheet_delete_positive_button_text,
        ),
        negativeButtonText = stringResource(
            id = R.string.screen_categories_bottom_sheet_delete_negative_button_text,
        ),
        onPositiveButtonClick = {
            deleteCategory()
            resetCategoryIdToDelete()
            resetBottomSheetType()
        },
        onNegativeButtonClick = {
            resetCategoryIdToDelete()
            resetBottomSheetType()
        },
    )
}
