package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.add_account.state.AddAccountScreenNameError

public data class AddAccountScreenDataValidationState(
    val isCtaButtonEnabled: Boolean = false,
    val nameError: AddAccountScreenNameError = AddAccountScreenNameError.None,
)
