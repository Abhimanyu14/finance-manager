package com.makeappssimple.abhimanyu.financemanager.android.core.database.util

import com.makeappssimple.abhimanyu.financemanager.android.core.common.extensions.map
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType
import kotlinx.collections.immutable.ImmutableList
import kotlin.math.abs

public fun sanitizeTransactions(
    transactions: ImmutableList<TransactionEntity>,
): ImmutableList<TransactionEntity> {
    return transactions.map { transaction ->
        when (transaction.transactionType) {
            TransactionType.INCOME -> {
                transaction.copy(
                    accountFromId = null,
                )
            }

            TransactionType.EXPENSE -> {
                transaction.copy(
                    accountToId = null,
                    amount = transaction.amount.copy(
                        value = abs(transaction.amount.value),
                    ),
                )
            }

            TransactionType.TRANSFER -> {
                transaction.copy(
                    categoryId = null,
                )
            }

            TransactionType.ADJUSTMENT -> {
                val accountId = transaction.accountToId
                transaction.copy(
                    categoryId = null,
                    accountFromId = if (transaction.amount.value < 0) {
                        accountId
                    } else {
                        transaction.accountFromId
                    },
                    accountToId = if (transaction.amount.value < 0) {
                        null
                    } else {
                        transaction.accountToId
                    },
                    amount = transaction.amount.copy(
                        value = abs(transaction.amount.value),
                    ),
                )
            }

            TransactionType.INVESTMENT -> {
                transaction.copy(
                    accountToId = null,
                )
            }

            TransactionType.REFUND -> {
                transaction.copy(
                    accountFromId = null,
                )
            }
        }
    }
}
