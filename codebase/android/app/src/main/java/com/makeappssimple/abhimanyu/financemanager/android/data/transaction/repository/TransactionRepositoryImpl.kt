package com.makeappssimple.abhimanyu.financemanager.android.data.transaction.repository

import com.makeappssimple.abhimanyu.financemanager.android.data.transaction.datasource.local.TransactionDao
import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.Transaction
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class TransactionRepositoryImpl(
    private val transactionDao: TransactionDao,
) : TransactionRepository {
    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    override val transactions: Flow<List<Transaction>> = transactionDao.getTransactions()

    override fun getRecentTransactions(
        numberOfTransactions: Int,
    ): Flow<List<Transaction>> {
        return transactionDao.getRecentTransactions(
            numberOfTransactions = numberOfTransactions,
        )
    }

    override suspend fun getTransactionsCount(): Int {
        return transactionDao.getTransactionsCount()
    }

    override suspend fun checkIfCategoryIsUsedInTransactions(
        categoryId: Int,
    ): Boolean {
        return transactionDao.checkIfCategoryIsUsedInTransactions(
            categoryId = categoryId,
        )
    }

    override suspend fun checkIfSourceIsUsedInTransactions(
        sourceId: Int,
    ): Boolean {
        return transactionDao.checkIfSourceIsUsedInTransactions(
            sourceId = sourceId,
        )
    }

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

    override suspend fun insertTransactions(
        vararg transactions: Transaction,
    ) {
        transactionDao.insertTransactions(
            transactions = transactions,
        )
    }

    override suspend fun updateTransactions(
        vararg transactions: Transaction,
    ) {
        transactionDao.updateTransactions(
            transactions = transactions,
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

    override suspend fun deleteAllTransactions() {
        transactionDao.deleteAllTransactions()
    }
}
