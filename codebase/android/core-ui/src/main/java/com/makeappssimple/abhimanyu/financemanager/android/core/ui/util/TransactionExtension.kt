package com.makeappssimple.abhimanyu.financemanager.android.core.ui.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType

@Composable
fun Transaction.getAmountTextColor(): Color {
    return when (this.transactionType) {
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
            when {
                this.amount.value > 0 -> {
                    MaterialTheme.colorScheme.onTertiaryContainer
                }
                this.amount.value < 0 -> {
                    MaterialTheme.colorScheme.error
                }
                else -> {
                    MaterialTheme.colorScheme.onBackground
                }
            }
        }
        TransactionType.INVESTMENT -> {
            MaterialTheme.colorScheme.primary
        }
    }
}
