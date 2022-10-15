package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.getEndOfDayTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.getEndOfMonthTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.getEndOfYearTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.getStartOfDayTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.getStartOfMonthTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.common.util.getStartOfYearTimestamp
import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.model.EmojiLocalEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.datasource.local.TransactionDao
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model.TransactionFor
import kotlinx.coroutines.flow.Flow

class TransactionRepositoryImpl(
    private val transactionDao: TransactionDao,
) : TransactionRepository {
    override val allTransactions: Flow<List<Transaction>> = transactionDao.getAllTransactions()

    override fun getAllTransactionData(): Flow<List<TransactionData>> {
        return transactionDao.getAllTransactionData()
    }

    override suspend fun getSearchedTransactionData(
        searchText: String,
    ): List<TransactionData> {
        return transactionDao.getSearchedTransactionData(
            searchText = searchText,
        )
    }

    override fun getRecentTransactionData(
        numberOfTransactions: Int,
    ): Flow<List<TransactionData>> {
        return transactionDao.getRecentTransactionData(
            numberOfTransactions = numberOfTransactions,
        )
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
    ) {
        transactionDao.insertTransaction(
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
