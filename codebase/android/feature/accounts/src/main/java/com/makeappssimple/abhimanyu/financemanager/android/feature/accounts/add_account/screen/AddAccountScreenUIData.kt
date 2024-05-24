package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.screen

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.model.AccountType
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIData

@Immutable
public data class AddAccountScreenUIData(
    val errorData: AddAccountScreenUIErrorData = AddAccountScreenUIErrorData(),
    val isValidAccountData: Boolean = false,
    val accountIsNotCash: Boolean = false,
    val selectedAccountTypeIndex: Int = 0,
    val accountTypes: List<AccountType> = emptyList(),
    val balanceAmountValue: TextFieldValue = TextFieldValue(),
    val minimumBalanceAmountValue: TextFieldValue = TextFieldValue(),
    val name: TextFieldValue = TextFieldValue(),
) : ScreenUIData
