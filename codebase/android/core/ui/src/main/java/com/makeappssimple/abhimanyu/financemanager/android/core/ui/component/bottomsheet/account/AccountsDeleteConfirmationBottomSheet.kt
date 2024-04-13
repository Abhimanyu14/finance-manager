package com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.account

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.R
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.component.bottomsheet.common.MyConfirmationBottomSheetUI

@Composable
public fun AccountsDeleteConfirmationBottomSheet(
    accountIdToDelete: Int?,
    resetBottomSheetType: () -> Unit,
    resetAccountIdToDelete: () -> Unit,
    resetExpandedItemIndex: () -> Unit,
    deleteAccount: () -> Unit,
) {
    MyConfirmationBottomSheetUI(
        title = stringResource(
            id = R.string.screen_accounts_bottom_sheet_delete_title,
        ),
        message = stringResource(
            id = R.string.screen_accounts_bottom_sheet_delete_message,
        ),
        positiveButtonText = stringResource(
            id = R.string.screen_accounts_bottom_sheet_delete_positive_button_text,
        ),
        negativeButtonText = stringResource(
            id = R.string.screen_accounts_bottom_sheet_delete_negative_button_text,
        ),
        onPositiveButtonClick = {
            accountIdToDelete?.let {
                deleteAccount()
                resetAccountIdToDelete()
                resetExpandedItemIndex()
            }
            resetBottomSheetType()
        },
        onNegativeButtonClick = {
            resetBottomSheetType()
            resetAccountIdToDelete()
        },
    )
}
