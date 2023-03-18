package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.common

import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.bottom_sheet.MyConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.R
import kotlinx.coroutines.CoroutineScope

@Composable
internal fun TransactionDeleteConfirmationBottomSheetContent(
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
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
            toggleModalBottomSheetState(
                coroutineScope = coroutineScope,
                modalBottomSheetState = modalBottomSheetState,
            ) {
                transactionIdToDelete?.let {
                    deleteTransaction()
                    resetTransactionIdToDelete()
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
                resetTransactionIdToDelete()
            }
        },
    )
}
