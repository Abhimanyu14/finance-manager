package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.transactions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.cre.R
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.common.MyConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.common.MyConfirmationBottomSheetData
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.common.MyConfirmationBottomSheetEvent

@Composable
public fun TransactionDeleteConfirmationBottomSheet(
    handleEvent: (event: TransactionDeleteConfirmationBottomSheetEvent) -> Unit = {},
) {
    MyConfirmationBottomSheet(
        data = MyConfirmationBottomSheetData(
            message = stringResource(
                id = R.string.screen_transactions_bottom_sheet_delete_message,
            ),
            negativeButtonText = stringResource(
                id = R.string.screen_transactions_bottom_sheet_delete_negative_button_text,
            ),
            positiveButtonText = stringResource(
                id = R.string.screen_transactions_bottom_sheet_delete_positive_button_text,
            ),
            title = stringResource(
                id = R.string.screen_transactions_bottom_sheet_delete_title,
            ),
        ),
        handleEvent = { event ->
            when (event) {
                is MyConfirmationBottomSheetEvent.OnNegativeButtonClick -> {
                    handleEvent(TransactionDeleteConfirmationBottomSheetEvent.OnNegativeButtonClick)
                }

                is MyConfirmationBottomSheetEvent.OnPositiveButtonClick -> {
                    handleEvent(TransactionDeleteConfirmationBottomSheetEvent.OnPositiveButtonClick)
                }
            }
        },
    )
}
