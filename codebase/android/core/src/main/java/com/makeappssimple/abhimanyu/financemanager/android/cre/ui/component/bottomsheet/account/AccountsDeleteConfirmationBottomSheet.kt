package com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.account

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.cre.R
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.common.MyConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.common.MyConfirmationBottomSheetData
import com.makeappssimple.abhimanyu.financemanager.android.cre.ui.component.bottomsheet.common.MyConfirmationBottomSheetEvent

@Composable
public fun AccountsDeleteConfirmationBottomSheet(
    handleEvent: (event: AccountsDeleteConfirmationBottomSheetEvent) -> Unit = {},
) {
    MyConfirmationBottomSheet(
        data = MyConfirmationBottomSheetData(
            message = stringResource(
                id = R.string.screen_accounts_bottom_sheet_delete_message,
            ),
            negativeButtonText = stringResource(
                id = R.string.screen_accounts_bottom_sheet_delete_negative_button_text,
            ),
            positiveButtonText = stringResource(
                id = R.string.screen_accounts_bottom_sheet_delete_positive_button_text,
            ),
            title = stringResource(
                id = R.string.screen_accounts_bottom_sheet_delete_title,
            ),
        ),
        handleEvent = { event ->
            when (event) {
                is MyConfirmationBottomSheetEvent.OnNegativeButtonClick -> {
                    handleEvent(AccountsDeleteConfirmationBottomSheetEvent.OnNegativeButtonClick)
                }

                is MyConfirmationBottomSheetEvent.OnPositiveButtonClick -> {
                    handleEvent(AccountsDeleteConfirmationBottomSheetEvent.OnPositiveButtonClick)
                }
            }
        },
    )
}
