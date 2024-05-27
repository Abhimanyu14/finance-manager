package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.screen

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIStateEvents

@Stable
internal class AddAccountScreenUIStateEvents(
    val clearMinimumAccountBalanceAmountValue: () -> Unit,
    val clearName: () -> Unit,
    val resetScreenBottomSheetType: () -> Unit,
    val updateMinimumAccountBalanceAmountValue: (updatedMinimumAccountBalanceAmountValue: TextFieldValue) -> Unit,
    val updateName: (updatedName: TextFieldValue) -> Unit,
    val updateSelectedAccountTypeIndex: (updatedIndex: Int) -> Unit,
) : ScreenUIStateEvents
