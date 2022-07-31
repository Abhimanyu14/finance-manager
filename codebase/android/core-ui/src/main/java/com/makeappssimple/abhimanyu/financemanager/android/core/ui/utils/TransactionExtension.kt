package com.makeappssimple.abhimanyu.financemanager.android.core.ui.utils

import androidx.compose.ui.graphics.Color
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.DarkGray
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.Error
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.OnTertiaryContainer

val Transaction.amountTextColor: Color
    get() = when (this.transactionType) {
        TransactionType.INCOME -> {
            OnTertiaryContainer
        }
        TransactionType.EXPENSE -> {
            Error
        }
        TransactionType.TRANSFER -> {
            DarkGray
        }
        TransactionType.ADJUSTMENT -> {
            when {
                this.amount.value > 0 -> {
                    OnTertiaryContainer
                }
                this.amount.value < 0 -> {
                    Error
                }
                else -> {
                    DarkGray
                }
            }
        }
    }
