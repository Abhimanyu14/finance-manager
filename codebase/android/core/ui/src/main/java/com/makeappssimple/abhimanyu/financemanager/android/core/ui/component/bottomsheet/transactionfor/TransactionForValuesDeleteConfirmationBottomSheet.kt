package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactionfor

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common.MyConfirmationBottomSheetUI

@Composable
public fun TransactionForValuesDeleteConfirmationBottomSheet(
    onNegativeButtonClick: () -> Unit,
    onPositiveButtonClick: () -> Unit,
) {
    MyConfirmationBottomSheetUI(
        title = stringResource(
            id = R.string.bottom_sheet_transaction_for_values_menu_delete_title,
        ),
        message = stringResource(
            id = R.string.bottom_sheet_transaction_for_values_menu_message,
        ),
        positiveButtonText = stringResource(
            id = R.string.bottom_sheet_transaction_for_values_menu_positive_button_text,
        ),
        negativeButtonText = stringResource(
            id = R.string.bottom_sheet_transaction_for_values_menu_negative_button_text,
        ),
        onNegativeButtonClick = onNegativeButtonClick,
        onPositiveButtonClick = onPositiveButtonClick,
    )
}
