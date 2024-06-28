package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_transaction.viewmodel

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Account
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import java.time.LocalDate
import java.time.LocalTime

@Immutable
internal data class AddTransactionScreenUiStateData(
    val selectedTransactionTypeIndex: Int? = null,
    val amount: TextFieldValue = TextFieldValue(),
    val title: TextFieldValue = TextFieldValue(),
    val category: Category? = null,
    val selectedTransactionForIndex: Int = 0,
    val accountFrom: Account? = null,
    val accountTo: Account? = null,
    val transactionDate: LocalDate = LocalDate.MIN,
    val transactionTime: LocalTime = LocalTime.MIN,
    val amountErrorText: String? = null,
)
