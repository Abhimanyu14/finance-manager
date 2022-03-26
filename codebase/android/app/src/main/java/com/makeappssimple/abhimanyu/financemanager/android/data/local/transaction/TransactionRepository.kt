package com.makeappssimple.abhimanyu.financemanager.android.data.local.transaction

import com.makeappssimple.abhimanyu.financemanager.android.models.Transaction

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class TransactionRepository(
    private val transactionDao: TransactionDao,
) {
    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val transactions = transactionDao.getTransactions()

    suspend fun getTransaction(
        id: Int,
    ): Transaction? {
        return transactionDao.getTransaction(
            id = id,
        )
    }

    suspend fun insertTransaction(
        transaction: Transaction,
    ) {
        transactionDao.insertTransaction(
            transaction = transaction,
        )
    }

    suspend fun deleteTransaction(
        id: Int,
    ) {
        transactionDao.deleteTransaction(
            id = id,
        )
    }

    suspend fun deleteTransactions(
        vararg transactions: Transaction,
    ) {
        transactionDao.deleteTransactions(
            transactions = transactions,
        )
    }
}
