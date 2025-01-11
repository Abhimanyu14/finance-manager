package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.category

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common.MyConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common.MyConfirmationBottomSheetData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common.MyConfirmationBottomSheetEvent

@Composable
public fun CategoriesDeleteConfirmationBottomSheet(
    handleEvent: (event: CategoriesDeleteConfirmationBottomSheetEvent) -> Unit = {},
) {
    MyConfirmationBottomSheet(
        data = MyConfirmationBottomSheetData(
            message = stringResource(
                id = R.string.screen_categories_bottom_sheet_delete_message,
            ),
            negativeButtonText = stringResource(
                id = R.string.screen_categories_bottom_sheet_delete_negative_button_text,
            ),
            positiveButtonText = stringResource(
                id = R.string.screen_categories_bottom_sheet_delete_positive_button_text,
            ),
            title = stringResource(
                id = R.string.screen_categories_bottom_sheet_delete_title,
            ),
        ),
        handleEvent = { event ->
            when (event) {
                is MyConfirmationBottomSheetEvent.OnNegativeButtonClick -> {
                    handleEvent(CategoriesDeleteConfirmationBottomSheetEvent.OnNegativeButtonClick)
                }

                is MyConfirmationBottomSheetEvent.OnPositiveButtonClick -> {
                    handleEvent(CategoriesDeleteConfirmationBottomSheetEvent.OnPositiveButtonClick)
                }
            }
        },
    )
}
