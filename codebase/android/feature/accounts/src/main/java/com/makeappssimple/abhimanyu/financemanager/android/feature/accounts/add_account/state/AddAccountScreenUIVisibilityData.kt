package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.state

import androidx.compose.runtime.Immutable

@Immutable
internal data class AddAccountScreenUIVisibilityData(
    val minimumBalanceAmountTextField: Boolean = false,
    val nameTextFieldErrorText: Boolean = false,
)
