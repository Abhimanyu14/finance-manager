package com.makeappssimple.abhimanyu.financemanager.android.ui.screens.add_transaction.viewmodel

import com.makeappssimple.abhimanyu.financemanager.android.entities.category.Category
import com.makeappssimple.abhimanyu.financemanager.android.entities.source.Source
import java.util.Calendar

data class AddTransactionScreenUiState(
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
