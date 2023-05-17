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

class TransactionDataSourceImpl(
    private val myRoomDatabase: MyRoomDatabase,
) : TransactionDataSource {
    override suspend fun deleteTransaction(
        id: Int,
        vararg sources: SourceEntity,
    ) {
        with(myRoomDatabase) {
            withTransaction {
                val transaction = transactionDao().getTransaction(
                    id = id,
                )
                if (transaction?.transactionType == TransactionType.REFUND) {
                    transaction.originalTransactionId?.let { originalTransactionId ->
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
                sourceDao().updateSources(
                    sources = sources,
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
        return with(myRoomDatabase) {
            withTransaction {
                val id = transactionDao().insertTransaction(
                    transaction = transaction,
                )
                sourceFrom?.let { sourceFromValue ->
                    sourceDao().updateSources(
                        sourceFromValue.updateBalanceAmount(
                            updatedBalanceAmount = sourceFromValue.balanceAmount.value - amountValue,
                        ),
                    )
                }
                sourceTo?.let { sourceToValue ->
                    sourceDao().updateSources(
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
        with(myRoomDatabase) {
            withTransaction {
                categoryDao().deleteAllCategories()
                emojiDao().deleteAllEmojis()
                sourceDao().deleteAllSources()
                transactionDao().deleteAllTransactions()
                transactionForDao().deleteAllTransactionForValues()

                categoryDao().insertCategories(
                    categories = categories,
                )
                emojiDao().insertEmojis(
                    emojis = emojis,
                )
                sourceDao().insertSources(
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
