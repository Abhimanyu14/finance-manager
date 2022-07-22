package com.makeappssimple.abhimanyu.financemanager.android.utils

import androidx.compose.ui.graphics.Color
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.DarkGray
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.OnTertiaryContainer
import com.makeappssimple.abhimanyu.financemanager.android.core.designsystem.theme.Red

val Transaction.amountTextColor: Color
    get() = when (this.transactionType) {
        TransactionType.INCOME -> {
            OnTertiaryContainer
        }
        TransactionType.EXPENSE -> {
            Red
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
                    Red
                }
                else -> {
                    DarkGray
                }
            }
        }
    }
