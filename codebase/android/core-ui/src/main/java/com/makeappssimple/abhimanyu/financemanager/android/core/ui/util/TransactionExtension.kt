package com.makeappssimple.abhimanyu.financemanager.android.core.ui.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType

@Composable
fun Transaction.getAmountTextColor(): Color {
    return calculateAmountTextColor(
        sourceFromId = this.sourceFromId,
        transactionType = this.transactionType,
    )
}

@Composable
private fun calculateAmountTextColor(
    sourceFromId: Int?,
    transactionType: TransactionType,
): Color {
    return when (transactionType) {
        TransactionType.INCOME -> {
            MaterialTheme.colorScheme.onTertiaryContainer
        }
        TransactionType.EXPENSE -> {
            MaterialTheme.colorScheme.error
        }
        TransactionType.TRANSFER -> {
            MaterialTheme.colorScheme.onBackground
        }
        TransactionType.ADJUSTMENT -> {
            if (sourceFromId != null) {
                MaterialTheme.colorScheme.error
            } else {
                MaterialTheme.colorScheme.onTertiaryContainer
            }
        }
        TransactionType.INVESTMENT -> {
            MaterialTheme.colorScheme.primary
        }
    }
}
