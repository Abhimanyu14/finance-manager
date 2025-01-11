package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.state.AddTransactionForScreenTitleError

public data class AddTransactionForScreenDataValidationState(
    val isCtaButtonEnabled: Boolean = false,
    val titleError: AddTransactionForScreenTitleError = AddTransactionForScreenTitleError.None,
)
