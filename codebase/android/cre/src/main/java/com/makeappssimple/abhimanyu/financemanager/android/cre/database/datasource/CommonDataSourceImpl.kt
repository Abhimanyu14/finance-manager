package com.makeappssimple.abhimanyu.financemanager.android.cre.database.datasource

import androidx.room.withTransaction
import com.makeappssimple.abhimanyu.financemanager.android.cre.database.local.database.MyRoomDatabase
import com.makeappssimple.abhimanyu.financemanager.android.cre.database.model.AccountEntity
import com.makeappssimple.abhimanyu.financemanager.android.cre.database.model.CategoryEntity
import com.makeappssimple.abhimanyu.financemanager.android.cre.database.model.TransactionEntity
import com.makeappssimple.abhimanyu.financemanager.android.cre.database.model.TransactionForEntity
import com.makeappssimple.abhimanyu.financemanager.android.cre.database.model.updateBalanceAmount
import com.makeappssimple.abhimanyu.financemanager.android.cre.model.TransactionType

public class CommonDataSourceImpl(
    private val myRoomDatabase: MyRoomDatabase,
) : CommonDataSource {
    override suspend fun deleteTransaction(
        id: Int,
    ): Boolean {
        return with(
            receiver = myRoomDatabase,
        ) {
            withTransaction {
                val transactionData = transactionDao().getTransactionData(
                    id = id,
                )

                val updatedAccounts = mutableListOf<AccountEntity>()
                transactionData?.accountFrom?.let {
                    updatedAccounts.add(
                        it.updateBalanceAmount(
                            updatedBalanceAmount = it.balanceAmount.value + transactionData.transaction.amount.value,
                        )
                    )
                }
                transactionData?.accountTo?.let {
                    updatedAccounts.add(
                        it.updateBalanceAmount(
                            updatedBalanceAmount = it.balanceAmount.value - transactionData.transaction.amount.value,
                        )
                    )
                }

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
                accountDao().updateAccounts(
                    accounts = updatedAccounts.toTypedArray(),
                )
            }
            true
        }
    }

    override suspend fun insertTransaction(
        amountValue: Long,
        accountFrom: AccountEntity?,
        accountTo: AccountEntity?,
        transaction: TransactionEntity,
    ): Long {
        return with(
            receiver = myRoomDatabase,
        ) {
            withTransaction {
                val id = transactionDao().insertTransaction(
                    transaction = transaction,
                )
                accountFrom?.let { accountFromValue ->
                    accountDao().updateAccounts(
                        accountFromValue.updateBalanceAmount(
                            updatedBalanceAmount = accountFromValue.balanceAmount.value - amountValue,
                        ),
                    )
                }
                accountTo?.let { accountToValue ->
                    accountDao().updateAccounts(
                        accountToValue.updateBalanceAmount(
                            updatedBalanceAmount = accountToValue.balanceAmount.value + amountValue,
                        )
                    )
                }
                id
            }
        }
    }

    override suspend fun restoreData(
        categories: Array<CategoryEntity>,
        accounts: Array<AccountEntity>,
        transactions: Array<TransactionEntity>,
        transactionForValues: Array<TransactionForEntity>,
    ): Boolean {
        return with(
            receiver = myRoomDatabase,
        ) {
            withTransaction {
                categoryDao().deleteAllCategories()
                accountDao().deleteAllAccounts()
                transactionDao().deleteAllTransactions()
                transactionForDao().deleteAllTransactionForValues()

                categoryDao().insertCategories(
                    categories = categories,
                )
                accountDao().insertAccounts(
                    accounts = accounts,
                )
                transactionDao().insertTransactions(
                    transactions = transactions,
                )
                transactionForDao().insertTransactionForValues(
                    transactionForValues = transactionForValues,
                )
            }
            true
        }
    }
}
