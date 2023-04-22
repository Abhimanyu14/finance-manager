package com.makeappssimple.abhimanyu.financemanager.android.core.ui.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.isNotNull
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType

@Composable
fun Transaction.getAmountTextColor(): Color {
    return this.transactionType.calculateAmountTextColor(
        isBalanceReduced = this.sourceFromId.isNotNull(),
    )
}

@Composable
private fun TransactionType.calculateAmountTextColor(
    isBalanceReduced: Boolean,
): Color {
    return when (this) {
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
            if (isBalanceReduced) {
                MaterialTheme.colorScheme.error
            } else {
                MaterialTheme.colorScheme.onTertiaryContainer
            }
        }

        TransactionType.INVESTMENT -> {
            MaterialTheme.colorScheme.primary
        }

        TransactionType.REFUND -> {
            MaterialTheme.colorScheme.onTertiaryContainer
        }
    }
}
