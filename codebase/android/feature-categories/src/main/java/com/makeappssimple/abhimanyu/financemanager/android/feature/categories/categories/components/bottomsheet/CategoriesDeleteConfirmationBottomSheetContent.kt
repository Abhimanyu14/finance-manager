package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.components.bottomsheet

import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.bottom_sheet.ConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.bottom_sheet.ConfirmationBottomSheetData
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.R
import kotlinx.coroutines.CoroutineScope

@Composable
internal fun CategoriesDeleteConfirmationBottomSheetContent(
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    categoryIdToDelete: Int?,
    resetBottomSheetType: () -> Unit,
    resetCategoryIdToDelete: () -> Unit,
    resetExpenseExpandedItemIndex: () -> Unit,
    resetIncomeExpandedItemIndex: () -> Unit,
    deleteCategory: () -> Unit,
) {
    ConfirmationBottomSheet(
        data = ConfirmationBottomSheetData(
            title = stringResource(
                id = R.string.screen_sources_bottom_sheet_delete_title,
            ),
            message = stringResource(
                id = R.string.screen_sources_bottom_sheet_delete_message,
            ),
            positiveButtonText = stringResource(
                id = R.string.screen_sources_bottom_sheet_delete_positive_button_text,
            ),
            negativeButtonText = stringResource(
                id = R.string.screen_sources_bottom_sheet_delete_negative_button_text,
            ),
            onPositiveButtonClick = {
                toggleModalBottomSheetState(
                    coroutineScope = coroutineScope,
                    modalBottomSheetState = modalBottomSheetState,
                ) {
                    categoryIdToDelete?.let {
                        deleteCategory()
                        resetCategoryIdToDelete()
                        resetExpenseExpandedItemIndex()
                        resetIncomeExpandedItemIndex()
                    }
                    resetBottomSheetType()
                }
            },
            onNegativeButtonClick = {
                toggleModalBottomSheetState(
                    coroutineScope = coroutineScope,
                    modalBottomSheetState = modalBottomSheetState,
                ) {
                    resetBottomSheetType()
                    resetCategoryIdToDelete()
                }
            },
        ),
    )
}
