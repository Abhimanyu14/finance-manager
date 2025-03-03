package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.state

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.bottomsheet.AddAccountScreenBottomSheetType
import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.snackbar.AddAccountScreenSnackbarType

@Stable
internal class AddAccountScreenUIStateEvents(
    val clearMinimumAccountBalanceAmountValue: () -> Unit = {},
    val clearName: () -> Unit = {},
    val insertAccount: () -> Unit = {},
    val navigateUp: () -> Unit = {},
    val resetScreenBottomSheetType: () -> Unit = {},
    val resetScreenSnackbarType: () -> Unit = {},
    val updateMinimumAccountBalanceAmountValue: (updatedMinimumAccountBalanceAmountValue: TextFieldValue) -> Unit = {},
    val updateName: (updatedName: TextFieldValue) -> Unit = {},
    val updateScreenBottomSheetType: (AddAccountScreenBottomSheetType) -> Unit = {},
    val updateScreenSnackbarType: (AddAccountScreenSnackbarType) -> Unit = {},
    val updateSelectedAccountTypeIndex: (updatedIndex: Int) -> Unit = {},
) : ScreenUIStateEvents
