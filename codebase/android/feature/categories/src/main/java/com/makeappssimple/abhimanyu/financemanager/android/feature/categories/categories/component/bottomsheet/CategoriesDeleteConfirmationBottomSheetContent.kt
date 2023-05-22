package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.component.bottomsheet

import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.MyConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.R
import kotlinx.coroutines.CoroutineScope

@Composable
internal fun CategoriesDeleteConfirmationBottomSheetContent(
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    deleteCategory: () -> Unit,
    resetBottomSheetType: () -> Unit,
    resetCategoryIdToDelete: () -> Unit,
) {
    MyConfirmationBottomSheet(
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
            toggleModalBottomSheetState(
                coroutineScope = coroutineScope,
                modalBottomSheetState = modalBottomSheetState,
            ) {
                deleteCategory()
                resetCategoryIdToDelete()
                resetBottomSheetType()
            }
        },
        onNegativeButtonClick = {
            toggleModalBottomSheetState(
                coroutineScope = coroutineScope,
                modalBottomSheetState = modalBottomSheetState,
            ) {
                resetCategoryIdToDelete()
                resetBottomSheetType()
            }
        },
    )
}
