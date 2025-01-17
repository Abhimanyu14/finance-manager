package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.state

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.bottomsheet.EditAccountScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.snackbar.EditAccountScreenSnackbarType

@Stable
internal class EditAccountScreenUIStateEvents(
    val clearBalanceAmountValue: () -> Unit = {},
    val clearMinimumAccountBalanceAmountValue: () -> Unit = {},
    val clearName: () -> Unit = {},
    val navigateUp: () -> Unit = {},
    val resetScreenBottomSheetType: () -> Unit = {},
    val updateAccount: () -> Unit = {},
    val updateBalanceAmountValue: (updatedBalanceAmountValue: TextFieldValue) -> Unit = {},
    val updateMinimumAccountBalanceAmountValue: (updatedMinimumAccountBalanceAmountValue: TextFieldValue) -> Unit = {},
    val updateName: (updatedName: TextFieldValue) -> Unit = {},
    val updateScreenBottomSheetType: (EditAccountScreenBottomSheetType) -> Unit = {},
    val updateScreenSnackbarType: (EditAccountScreenSnackbarType) -> Unit = {},
    val updateSelectedAccountTypeIndex: (updatedSelectedAccountTypeIndex: Int) -> Unit = {},
) : ScreenUIStateEvents
