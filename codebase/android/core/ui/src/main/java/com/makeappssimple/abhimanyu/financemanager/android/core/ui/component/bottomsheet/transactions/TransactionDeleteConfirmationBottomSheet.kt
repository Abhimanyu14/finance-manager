package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common.MyConfirmationBottomSheetUI

@Composable
fun TransactionDeleteConfirmationBottomSheet(
    transactionIdToDelete: Int?,
    resetBottomSheetType: () -> Unit,
    resetTransactionIdToDelete: () -> Unit,
    deleteTransaction: () -> Unit,
) {
    MyConfirmationBottomSheetUI(
        title = stringResource(
            id = R.string.screen_transactions_bottom_sheet_delete_title,
        ),
        message = stringResource(
            id = R.string.screen_transactions_bottom_sheet_delete_message,
        ),
        positiveButtonText = stringResource(
            id = R.string.screen_transactions_bottom_sheet_delete_positive_button_text,
        ),
        negativeButtonText = stringResource(
            id = R.string.screen_transactions_bottom_sheet_delete_negative_button_text,
        ),
        onPositiveButtonClick = {
            transactionIdToDelete?.let {
                deleteTransaction()
                resetTransactionIdToDelete()
            }
            resetBottomSheetType()
        },
        onNegativeButtonClick = {
            resetBottomSheetType()
            resetTransactionIdToDelete()
        },
    )
}
