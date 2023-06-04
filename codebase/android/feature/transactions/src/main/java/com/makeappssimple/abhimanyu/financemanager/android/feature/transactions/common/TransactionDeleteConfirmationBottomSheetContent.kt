package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottom_sheet.MyConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R

@Composable
internal fun TransactionDeleteConfirmationBottomSheetContent(
    transactionIdToDelete: Int?,
    resetBottomSheetType: () -> Unit,
    resetTransactionIdToDelete: () -> Unit,
    deleteTransaction: () -> Unit,
) {
    MyConfirmationBottomSheet(
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
