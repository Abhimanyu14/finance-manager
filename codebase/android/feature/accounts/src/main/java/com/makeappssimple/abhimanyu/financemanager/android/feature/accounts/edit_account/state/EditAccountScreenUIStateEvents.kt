package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.state

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.bottomsheet.EditAccountScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.snackbar.EditAccountScreenSnackbarType

@Stable
internal class EditAccountScreenUIStateEvents(
    val clearMinimumAccountBalanceAmountValue: () -> Unit = {},
    val clearName: () -> Unit = {},
    val navigateUp: () -> Unit = {},
    val resetScreenBottomSheetType: () -> Unit = {},
    val setName: (updatedName: TextFieldValue) -> Unit = {},
    val setBalanceAmountValue: (updatedBalanceAmountValue: TextFieldValue) -> Unit = {},
    val setMinimumAccountBalanceAmountValue: (updatedMinimumAccountBalanceAmountValue: TextFieldValue) -> Unit = {},
    val setScreenBottomSheetType: (EditAccountScreenBottomSheetType) -> Unit = {},
    val setScreenSnackbarType: (EditAccountScreenSnackbarType) -> Unit = {},
    val setSelectedAccountTypeIndex: (updatedSelectedAccountTypeIndex: Int) -> Unit = {},
    val updateAccount: (
        selectedAccountTypeIndex: Int,
        name: String,
        balanceAmountValue: String,
        minimumAccountBalanceAmountValue: String,
    ) -> Unit = { _, _, _, _ -> },
) : ScreenUIStateEvents
