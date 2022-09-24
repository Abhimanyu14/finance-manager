package com.makeappssimple.abhimanyu.financemanager.android.core.database.util

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionType
import kotlin.math.abs

fun transactionsCleanUp(
    transactions: List<Transaction>,
): List<Transaction> {
    return transactions.map {
        when (it.transactionType) {
            TransactionType.INCOME -> {
                it.copy(
                    sourceFromId = null,
                )
            }

            TransactionType.EXPENSE -> {
                it.copy(
                    sourceToId = null,
                    amount = it.amount.copy(
                        value = abs(it.amount.value),
                    ),
                )
            }

            TransactionType.TRANSFER -> {
                it.copy(
                    categoryId = null,
                )
            }

            TransactionType.ADJUSTMENT -> {
                val sourceId = it.sourceToId
                it.copy(
                    categoryId = null,
                    sourceFromId = if (it.amount.value < 0) {
                        sourceId
                    } else {
                        it.sourceFromId
                    },
                    sourceToId = if (it.amount.value < 0) {
                        null
                    } else {
                        it.sourceToId
                    },
                    amount = it.amount.copy(
                        value = abs(it.amount.value),
                    ),
                )
            }

            TransactionType.INVESTMENT -> {
                it.copy(
                    sourceToId = null,
                )
            }
        }
    }
}
