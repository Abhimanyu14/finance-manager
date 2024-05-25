package com.makeappssimple.abhimanyu.financemanager.android.feature.transactionfor.add_transaction_for.screen

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.ui.base.ScreenUIData

@Immutable
public data class AddTransactionForScreenUIData(
    val isValidTransactionForData: Boolean = false,
    val title: TextFieldValue = TextFieldValue(),
    val titleTextFieldError: AddTransactionForScreenUIError? = null,
) : ScreenUIData
