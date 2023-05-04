package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.EmojiLocalEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.dao.TransactionDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionFor
import kotlinx.coroutines.flow.Flow

class TransactionRepositoryImpl(
    private val transactionDao: TransactionDao,
) : TransactionRepository {
    override fun getAllTransactionsFlow(): Flow<List<Transaction>> {
        return transactionDao.getAllTransactionsFlow()
    }

    override suspend fun getAllTransactions(): List<Transaction> {
        return transactionDao.getAllTransactions()
    }

    override fun getAllTransactionDataFlow(): Flow<List<TransactionData>> {
        return transactionDao.getAllTransactionDataFlow()
    }

    override suspend fun getAllTransactionData(): List<TransactionData> {
        return transactionDao.getAllTransactionData()
    }

    override suspend fun getSearchedTransactionData(
        searchText: String,
    ): List<TransactionData> {
        return transactionDao.getSearchedTransactionData(
            searchText = searchText,
        )
    }

    override fun getRecentTransactionDataFlow(
        numberOfTransactions: Int,
    ): Flow<List<TransactionData>> {
        return transactionDao.getRecentTransactionDataFlow(
            numberOfTransactions = numberOfTransactions,
        )
    }

    override fun getTransactionsBetweenTimestampsFlow(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): Flow<List<Transaction>> {
        return transactionDao.getTransactionsBetweenTimestampsFlow(
            startingTimestamp = startingTimestamp,
            endingTimestamp = endingTimestamp,
        )
    }

    override suspend fun getTransactionsBetweenTimestamps(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): List<Transaction> {
        return transactionDao.getTransactionsBetweenTimestamps(
            startingTimestamp = startingTimestamp,
            endingTimestamp = endingTimestamp,
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

    override suspend fun checkIfTransactionForIsUsedInTransactions(
        transactionForId: Int,
    ): Boolean {
        return transactionDao.checkIfTransactionForIsUsedInTransactions(
            transactionForId = transactionForId,
        )
    }

    override suspend fun getTransaction(
        id: Int,
    ): Transaction? {
        return transactionDao.getTransaction(
            id = id,
        )
    }

    override suspend fun getTransactionData(
        id: Int,
    ): TransactionData? {
        return transactionDao.getTransactionData(
            id = id,
        )
    }

    override suspend fun insertTransaction(
        amountValue: Long,
        sourceFrom: Source?,
        sourceTo: Source?,
        transaction: Transaction,
    ): Long {
        return transactionDao.insertTransaction(
            amountValue = amountValue,
            sourceFrom = sourceFrom,
            sourceTo = sourceTo,
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

    override suspend fun deleteTransaction(
        id: Int,
        vararg sources: Source,
    ) {

        transactionDao.deleteTransaction(
            id = id,
            sources = sources,
        )
    }

    override suspend fun deleteAllTransactions() {
        transactionDao.deleteAllTransactions()
    }

    override suspend fun restoreData(
        categories: List<Category>,
        emojis: List<EmojiLocalEntity>,
        sources: List<Source>,
        transactions: List<Transaction>,
        transactionForValues: List<TransactionFor>,
    ) {
        transactionDao.restoreData(
            categories = categories,
            emojis = emojis,
            sources = sources,
            transactions = transactions,
            transactionForValues = transactionForValues,
        )
    }
}
