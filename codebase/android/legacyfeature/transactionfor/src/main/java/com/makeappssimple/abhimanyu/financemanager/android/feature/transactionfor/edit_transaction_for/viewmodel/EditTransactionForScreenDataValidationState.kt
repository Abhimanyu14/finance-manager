package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.edit_transaction_for.state.EditTransactionForScreenTitleError

public data class EditTransactionForScreenDataValidationState(
    val isCtaButtonEnabled: Boolean = false,
    val titleError: EditTransactionForScreenTitleError = EditTransactionForScreenTitleError.None,
)
