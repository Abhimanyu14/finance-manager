package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_or_edit_transaction_for.screen

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIData

@Immutable
data class AddOrEditTransactionForScreenUIData(
    val isValidTransactionForData: Boolean = false,
    val title: TextFieldValue = TextFieldValue(),
    val titleTextFieldError: AddOrEditTransactionForScreenUIError? = null,
) : ScreenUIData
