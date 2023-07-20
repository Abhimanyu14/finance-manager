package com.makeappssimple.abhimanyu.financemanager.android.core.database.datasource

import androidx.room.withTransaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.local.database.MyRoomDatabase
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.CategoryEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.EmojiEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.SourceEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionForEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.updateBalanceAmount
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionType

class CommonDataSourceImpl(
    private val myRoomDatabase: MyRoomDatabase,
) : CommonDataSource {
    override suspend fun deleteTransaction(
        id: Int,
    ) {
        with(
            receiver = myRoomDatabase,
        ) {
            withTransaction {
                val transactionData = transactionDao().getTransactionData(
                    id = id,
                )

                val updatesSources = buildList {
                    transactionData?.sourceFrom?.let {
                        add(
                            it.updateBalanceAmount(
                                updatedBalanceAmount = it.balanceAmount.value + transactionData.transaction.amount.value,
                            )
                        )
                    }
                    transactionData?.sourceTo?.let {
                        add(
                            it.updateBalanceAmount(
                                updatedBalanceAmount = it.balanceAmount.value - transactionData.transaction.amount.value,
                            )
                        )
                    }
                }.toTypedArray()

                if (transactionData?.transaction?.transactionType == TransactionType.REFUND) {
                    transactionData.transaction.originalTransactionId?.let { originalTransactionId ->
                        transactionDao().getTransaction(
                            id = originalTransactionId,
                        )?.let { originalTransaction ->
                            val originalTransactionRefundTransactionIds =
                                originalTransaction.refundTransactionIds?.run {
                                    this.toMutableList()
                                } ?: mutableListOf()
                            originalTransactionRefundTransactionIds.remove(
                                element = id,
                            )
                            val refundTransactionIds =
                                if (originalTransactionRefundTransactionIds.isEmpty()) {
                                    null
                                } else {
                                    originalTransactionRefundTransactionIds
                                }
                            transactionDao().updateTransaction(
                                transaction = originalTransaction.copy(
                                    refundTransactionIds = refundTransactionIds,
                                ),
                            )
                        }
                    }
                }
                transactionDao().deleteTransaction(
                    id = id,
                )
                sourceDao().updateAccounts(
                    sources = updatesSources,
                )
            }
        }
    }

    override suspend fun insertTransaction(
        amountValue: Long,
        sourceFrom: SourceEntity?,
        sourceTo: SourceEntity?,
        transaction: TransactionEntity,
    ): Long {
        return with(
            receiver = myRoomDatabase,
        ) {
            withTransaction {
                val id = transactionDao().insertTransaction(
                    transaction = transaction,
                )
                sourceFrom?.let { sourceFromValue ->
                    sourceDao().updateAccounts(
                        sourceFromValue.updateBalanceAmount(
                            updatedBalanceAmount = sourceFromValue.balanceAmount.value - amountValue,
                        ),
                    )
                }
                sourceTo?.let { sourceToValue ->
                    sourceDao().updateAccounts(
                        sourceToValue.updateBalanceAmount(
                            updatedBalanceAmount = sourceToValue.balanceAmount.value + amountValue,
                        )
                    )
                }
                id
            }
        }
    }

    override suspend fun restoreData(
        categories: Array<CategoryEntity>,
        emojis: Array<EmojiEntity>,
        sources: Array<SourceEntity>,
        transactions: Array<TransactionEntity>,
        transactionForValues: Array<TransactionForEntity>,
    ) {
        with(
            receiver = myRoomDatabase,
        ) {
            withTransaction {
                categoryDao().deleteAllCategories()
                emojiDao().deleteAllEmojis()
                sourceDao().deleteAllAccounts()
                transactionDao().deleteAllTransactions()
                transactionForDao().deleteAllTransactionForValues()

                categoryDao().insertCategories(
                    categories = categories,
                )
                emojiDao().insertEmojis(
                    emojis = emojis,
                )
                sourceDao().insertAccounts(
                    sources = sources,
                )
                transactionDao().insertTransactions(
                    transactions = transactions,
                )
                transactionForDao().insertTransactionForValues(
                    transactionForValues = transactionForValues,
                )
            }
        }
    }
}
