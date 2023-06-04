package com.makeappssimple.abhimanyu.financemanager.android.feature.categories.categories.component.bottomsheet

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.MyConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.feature.categories.R

@Composable
internal fun CategoriesSetAsDefaultConfirmationBottomSheetContent(
    transactionType: TransactionType,
    resetBottomSheetType: () -> Unit,
    resetClickedItemId: () -> Unit,
    setDefaultCategoryIdInDataStore: () -> Unit,
) {
    MyConfirmationBottomSheet(
        title = stringResource(
            id = R.string.screen_categories_bottom_sheet_set_as_default_title,
        ),
        message = stringResource(
            id = R.string.screen_categories_bottom_sheet_set_as_default_message,
            transactionType.title.lowercase(),
        ),
        positiveButtonText = stringResource(
            id = R.string.screen_categories_bottom_sheet_set_as_default_positive_button_text,
        ),
        negativeButtonText = stringResource(
            id = R.string.screen_categories_bottom_sheet_set_as_default_negative_button_text,
        ),
        onPositiveButtonClick = {
            setDefaultCategoryIdInDataStore()
            resetClickedItemId()
            resetBottomSheetType()
        },
        onNegativeButtonClick = {
            resetClickedItemId()
            resetBottomSheetType()
        },
    )
}
