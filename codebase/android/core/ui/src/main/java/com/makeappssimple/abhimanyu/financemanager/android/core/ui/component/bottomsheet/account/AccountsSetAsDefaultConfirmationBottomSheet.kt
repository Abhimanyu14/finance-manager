package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.account

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common.MyConfirmationBottomSheet
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common.MyConfirmationBottomSheetData
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common.MyConfirmationBottomSheetEvent

@Composable
public fun AccountsSetAsDefaultConfirmationBottomSheet(
    handleEvent: (event: AccountsSetAsDefaultConfirmationBottomSheetEvent) -> Unit = {},
) {
    MyConfirmationBottomSheet(
        data = MyConfirmationBottomSheetData(
            message = stringResource(
                id = R.string.screen_accounts_bottom_sheet_set_as_default_message,
            ),
            negativeButtonText = stringResource(
                id = R.string.screen_accounts_bottom_sheet_set_as_default_negative_button_text,
            ),
            positiveButtonText = stringResource(
                id = R.string.screen_accounts_bottom_sheet_set_as_default_positive_button_text,
            ),
            title = stringResource(
                id = R.string.screen_accounts_bottom_sheet_set_as_default_title,
            ),
        ),
        handleEvent = { event ->
            when (event) {
                is MyConfirmationBottomSheetEvent.OnNegativeButtonClick -> {
                    handleEvent(AccountsSetAsDefaultConfirmationBottomSheetEvent.OnNegativeButtonClick)
                }

                is MyConfirmationBottomSheetEvent.OnPositiveButtonClick -> {
                    handleEvent(AccountsSetAsDefaultConfirmationBottomSheetEvent.OnPositiveButtonClick)
                }
            }
        },
    )
}
