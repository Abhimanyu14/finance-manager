package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.account

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common.MyConfirmationBottomSheetUI

@Composable
fun AccountsSetAsDefaultConfirmationBottomSheet(
    clickedItemId: Int?,
    resetBottomSheetType: () -> Unit,
    resetClickedItemId: () -> Unit,
    setDefaultAccountIdInDataStore: () -> Unit,
) {
    MyConfirmationBottomSheetUI(
        title = stringResource(
            id = R.string.screen_accounts_bottom_sheet_set_as_default_title,
        ),
        message = stringResource(
            id = R.string.screen_accounts_bottom_sheet_set_as_default_message,
        ),
        positiveButtonText = stringResource(
            id = R.string.screen_accounts_bottom_sheet_set_as_default_positive_button_text,
        ),
        negativeButtonText = stringResource(
            id = R.string.screen_accounts_bottom_sheet_set_as_default_negative_button_text,
        ),
        onPositiveButtonClick = {
            clickedItemId?.let {
                setDefaultAccountIdInDataStore()
                resetClickedItemId()
            }
            resetBottomSheetType()
        },
        onNegativeButtonClick = {
            resetBottomSheetType()
            resetClickedItemId()
        },
    )
}
