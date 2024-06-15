package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.state

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents

@Stable
internal class EditAccountScreenUIStateEvents(
    val resetScreenBottomSheetType: () -> Unit = {},
    val setName: (updatedName: TextFieldValue) -> Unit = {},
    val setBalanceAmountValue: (updatedBalanceAmountValue: TextFieldValue) -> Unit = {},
    val setMinimumAccountBalanceAmountValue: (updatedMinimumAccountBalanceAmountValue: TextFieldValue) -> Unit = {},
    val setSelectedAccountTypeIndex: (updatedSelectedAccountTypeIndex: Int) -> Unit = {},
) : ScreenUIStateEvents
