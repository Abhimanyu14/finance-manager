package com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.feature.accounts.edit_account.state.EditAccountScreenNameError

public data class EditAccountScreenDataValidationState(
    val isCashAccount: Boolean = false,
    val isCtaButtonEnabled: Boolean = false,
    val nameError: EditAccountScreenNameError = EditAccountScreenNameError.None,
)
