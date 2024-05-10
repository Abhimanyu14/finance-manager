package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.category

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common.MyConfirmationBottomSheetUI

@Composable
public fun CategoriesSetAsDefaultConfirmationBottomSheet(
    transactionType: TransactionType,
    onNegativeButtonClick: () -> Unit,
    onPositiveButtonClick: () -> Unit,
) {
    MyConfirmationBottomSheetUI(
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
        onNegativeButtonClick = onPositiveButtonClick,
        onPositiveButtonClick = onNegativeButtonClick,
    )
}
