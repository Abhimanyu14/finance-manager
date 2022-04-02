package com.makeappssimple.abhimanyu.financemanager.android.data.transaction.repository

import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.TransactionDao
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.Transaction

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class TransactionRepositoryImpl(
    private val transactionDao: TransactionDao,
) : TransactionRepository {
    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    override val transactions = transactionDao.getTransactions()

    override suspend fun getTransaction(
        id: Int,
    ): Transaction? {
        return transactionDao.getTransaction(
            id = id,
        )
    }

    override suspend fun insertTransaction(
        transaction: Transaction,
    ) {
        transactionDao.insertTransaction(
            transaction = transaction,
        )
    }

    override suspend fun deleteTransaction(
        id: Int,
    ) {
        transactionDao.deleteTransaction(
            id = id,
        )
    }

    override suspend fun deleteTransactions(
        vararg transactions: Transaction,
    ) {
        transactionDao.deleteTransactions(
            transactions = transactions,
        )
    }
}
