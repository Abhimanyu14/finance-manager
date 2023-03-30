package com.makeappssimple.abhimanyu.financemanager.android.feature.transactions.add_or_edit_transaction.viewmodel

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import java.time.LocalDate
import java.time.LocalTime

@Immutable
data class AddOrEditTransactionScreenUiState(
    val selectedTransactionTypeIndex: Int?,
    val amount: TextFieldValue,
    val title: TextFieldValue,
    val description: TextFieldValue,
    val category: Category?,
    val selectedTransactionForIndex: Int,
    val sourceFrom: Source?,
    val sourceTo: Source?,
    val transactionDate: LocalDate,
    val transactionTime: LocalTime,
    val amountErrorText: String? = null,
)
