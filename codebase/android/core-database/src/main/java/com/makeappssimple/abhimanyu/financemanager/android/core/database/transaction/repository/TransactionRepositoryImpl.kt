package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.datasource.local.CategoryDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.datasource.local.SourceDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.datasource.local.TransactionDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionDetail
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.datasource.local.TransactionForDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.getEndOfDayTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.getEndOfMonthTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.getEndOfYearTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.getStartOfDayTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.getStartOfMonthTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.database.util.getStartOfYearTimestamp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class TransactionRepositoryImpl(
    private val categoryDao: CategoryDao,
    private val sourceDao: SourceDao,
    private val transactionDao: TransactionDao,
    private val transactionForDao: TransactionForDao,
) : TransactionRepository {
    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    override val allTransactions: Flow<List<Transaction>> = transactionDao.getAllTransactions()

    override fun getAllTransactionDetails(): Flow<List<TransactionDetail>> {
        return allTransactions.map {
            it.mapNotNull { transaction ->
                getTransactionDetail(
                    transaction = transaction,
                )
            }
        }
    }

    override fun getRecentTransactions(
        numberOfTransactions: Int,
    ): Flow<List<Transaction>> {
        return transactionDao.getRecentTransactions(
            numberOfTransactions = numberOfTransactions,
        )
    }

    override fun getRecentTransactionDetails(
        numberOfTransactions: Int,
    ): Flow<List<TransactionDetail>> {
        return transactionDao.getRecentTransactions(
            numberOfTransactions = numberOfTransactions,
        ).map {
            it.mapNotNull { transaction ->
                getTransactionDetail(
                    transaction = transaction,
                )
            }
        }
    }

    override fun getCurrentDayTransactions(): Flow<List<Transaction>> {
        return transactionDao.getTransactionsBetweenTimestamps(
            startingTimestamp = getStartOfDayTimestamp(),
            endingTimestamp = getEndOfDayTimestamp(),
        )
    }

    override fun getCurrentMonthTransactions(): Flow<List<Transaction>> {
        return transactionDao.getTransactionsBetweenTimestamps(
            startingTimestamp = getStartOfMonthTimestamp(),
            endingTimestamp = getEndOfMonthTimestamp(),
        )
    }

    override fun getCurrentYearTransactions(): Flow<List<Transaction>> {
        return transactionDao.getTransactionsBetweenTimestamps(
            startingTimestamp = getStartOfYearTimestamp(),
            endingTimestamp = getEndOfYearTimestamp(),
        )
    }

    override suspend fun getTransactionsCount(): Int {
        return transactionDao.getTransactionsCount()
    }

    override suspend fun getTitleSuggestions(
        categoryId: Int,
        numberOfSuggestions: Int,
    ): List<String> {
        return transactionDao.getTitleSuggestions(
            categoryId = categoryId,
            numberOfSuggestions = numberOfSuggestions,
        )
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

    override suspend fun updateTransaction(
        transaction: Transaction,
    ) {
        transactionDao.updateTransaction(
            transaction = transaction,
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

    private suspend fun getTransactionDetail(
        transaction: Transaction,
    ): TransactionDetail? {
        val category = transaction.categoryId?.let { id ->
            categoryDao.getCategory(
                id = id,
            )
        }
        val sourceFrom = transaction.sourceFromId?.let { id ->
            sourceDao.getSource(
                id = id,
            )
        }
        val sourceTo = transaction.sourceToId?.let { id ->
            sourceDao.getSource(
                id = id,
            )
        }
        val transactionFor = transactionForDao.getTransactionFor(
            id = transaction.transactionForId,
        )
        return transactionFor?.let {
            TransactionDetail(
                category = category,
                sourceFrom = sourceFrom,
                sourceTo = sourceTo,
                transaction = transaction,
                transactionFor = transactionFor,
            )
        }
    }
}
