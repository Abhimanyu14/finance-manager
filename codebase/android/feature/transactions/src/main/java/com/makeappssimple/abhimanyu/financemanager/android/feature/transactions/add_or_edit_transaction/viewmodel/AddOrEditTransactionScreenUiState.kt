package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.model.Source
import java.time.LocalDate
import java.time.LocalTime

@Immutable
data class AddOrEditTransactionScreenUiState(
    val selectedTransactionTypeIndex: Int? = null,
    val amount: TextFieldValue = TextFieldValue(),
    val title: TextFieldValue = TextFieldValue(),
    val description: TextFieldValue = TextFieldValue(),
    val category: Category? = null,
    val selectedTransactionForIndex: Int = 0,
    val sourceFrom: Source? = null,
    val sourceTo: Source? = null,
    val transactionDate: LocalDate = LocalDate.MIN,
    val transactionTime: LocalTime = LocalTime.MIN,
    val amountErrorText: String? = null,
)
