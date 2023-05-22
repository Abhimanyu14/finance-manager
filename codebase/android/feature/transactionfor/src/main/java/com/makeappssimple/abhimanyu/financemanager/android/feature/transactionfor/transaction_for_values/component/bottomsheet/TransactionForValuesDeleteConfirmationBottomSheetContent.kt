package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.transaction_for_values.component.bottomsheet

import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.common.toggleModalBottomSheetState
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.components.bottom_sheet.MyConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.R
import kotlinx.coroutines.CoroutineScope

@Composable
internal fun TransactionForValuesDeleteConfirmationBottomSheetContent(
    coroutineScope: CoroutineScope,
    modalBottomSheetState: ModalBottomSheetState,
    transactionForIdToDelete: Int?,
    resetBottomSheetType: () -> Unit,
    resetTransactionForIdToDelete: () -> Unit,
    deleteTransactionFor: () -> Unit,
) {
    MyConfirmationBottomSheet(
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
            toggleModalBottomSheetState(
                coroutineScope = coroutineScope,
                modalBottomSheetState = modalBottomSheetState,
            ) {
                transactionForIdToDelete?.let {
                    deleteTransactionFor()
                    resetTransactionForIdToDelete()
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
                resetTransactionForIdToDelete()
            }
        },
    )
}
