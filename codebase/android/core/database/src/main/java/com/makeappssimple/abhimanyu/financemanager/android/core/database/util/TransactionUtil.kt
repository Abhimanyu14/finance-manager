package com.makeappssimple.abhimanyu.financemanager.android.core.database.util

import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import kotlin.math.abs

fun transactionsCleanUp(
    transactions: List<TransactionEntity>,
): List<TransactionEntity> {
    return transactions.map {
        when (it.transactionType) {
            TransactionType.INCOME -> {
                it.copy(
                    accountFromId = null,
                )
            }

            TransactionType.EXPENSE -> {
                it.copy(
                    accountToId = null,
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
                val accountId = it.accountToId
                it.copy(
                    categoryId = null,
                    accountFromId = if (it.amount.value < 0) {
                        accountId
                    } else {
                        it.accountFromId
                    },
                    accountToId = if (it.amount.value < 0) {
                        null
                    } else {
                        it.accountToId
                    },
                    amount = it.amount.copy(
                        value = abs(it.amount.value),
                    ),
                )
            }

            TransactionType.INVESTMENT -> {
                it.copy(
                    accountToId = null,
                )
            }

            TransactionType.REFUND -> {
                it.copy(
                    accountFromId = null,
                )
            }
        }
    }
}
