package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.screen

import androidx.compose.runtime.Immutable

@Immutable
public data class AddAccountScreenUIVisibilityData(
    val minimumBalanceAmountTextField: Boolean = false,
    val nameTextFieldErrorText: Boolean = false,
)
