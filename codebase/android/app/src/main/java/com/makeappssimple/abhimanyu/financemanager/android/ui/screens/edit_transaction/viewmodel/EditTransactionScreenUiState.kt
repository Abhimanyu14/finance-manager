package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.edit_transaction.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import java.util.Calendar

data class EditTransactionScreenUiState(
    val selectedTransactionTypeIndex: Int?,
    val amount: String,
    val title: String,
    val description: String,
    val category: Category?,
    val selectedTransactionForIndex: Int,
    val sourceFrom: Source?,
    val sourceTo: Source?,
    val transactionCalendar: Calendar,
)
