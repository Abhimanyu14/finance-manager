package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.transactionfor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common.MyConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common.MyConfirmationBottomSheetData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common.MyConfirmationBottomSheetEvent

@Immutable
public sealed class TransactionForValuesDeleteConfirmationBottomSheetEvent {
    public data object OnNegativeButtonClick :
        TransactionForValuesDeleteConfirmationBottomSheetEvent()

    public data object OnPositiveButtonClick :
        TransactionForValuesDeleteConfirmationBottomSheetEvent()
}

@Composable
public fun TransactionForValuesDeleteConfirmationBottomSheet(
    handleEvent: (event: TransactionForValuesDeleteConfirmationBottomSheetEvent) -> Unit = {},
) {
    MyConfirmationBottomSheet(
        data = MyConfirmationBottomSheetData(
            message = stringResource(
                id = R.string.bottom_sheet_transaction_for_values_menu_message,
            ),
            negativeButtonText = stringResource(
                id = R.string.bottom_sheet_transaction_for_values_menu_negative_button_text,
            ),
            positiveButtonText = stringResource(
                id = R.string.bottom_sheet_transaction_for_values_menu_positive_button_text,
            ),
            title = stringResource(
                id = R.string.bottom_sheet_transaction_for_values_menu_delete_title,
            ),
        ),
        handleEvent = { event ->
            when (event) {
                is MyConfirmationBottomSheetEvent.OnNegativeButtonClick -> {
                    handleEvent(TransactionForValuesDeleteConfirmationBottomSheetEvent.OnNegativeButtonClick)
                }

                is MyConfirmationBottomSheetEvent.OnPositiveButtonClick -> {
                    handleEvent(TransactionForValuesDeleteConfirmationBottomSheetEvent.OnPositiveButtonClick)
                }
            }
        },
    )
}
