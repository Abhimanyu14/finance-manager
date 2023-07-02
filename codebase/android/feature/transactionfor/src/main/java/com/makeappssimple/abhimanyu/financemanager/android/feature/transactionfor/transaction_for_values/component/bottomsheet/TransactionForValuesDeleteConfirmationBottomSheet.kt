package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.component.bottomsheet

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.MyConfirmationBottomSheetUI
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.R

@Composable
internal fun TransactionForValuesDeleteConfirmationBottomSheet(
    transactionForIdToDelete: Int?,
    resetBottomSheetType: () -> Unit,
    resetTransactionForIdToDelete: () -> Unit,
    deleteTransactionFor: () -> Unit,
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
        onPositiveButtonClick = {
            transactionForIdToDelete?.let {
                deleteTransactionFor()
                resetTransactionForIdToDelete()
            }
            resetBottomSheetType()
        },
        onNegativeButtonClick = {
            resetBottomSheetType()
            resetTransactionForIdToDelete()
        },
    )
}
