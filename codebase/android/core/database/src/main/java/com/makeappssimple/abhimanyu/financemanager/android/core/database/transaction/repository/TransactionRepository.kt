package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.EmojiLocalEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.database.model.TransactionFor
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getAllTransactionsFlow(): Flow<List<Transaction>>

    suspend fun getAllTransactions(): List<Transaction>

    fun getAllTransactionDataFlow(): Flow<List<TransactionData>>

    suspend fun getAllTransactionData(): List<TransactionData>

    suspend fun getSearchedTransactionData(
        searchText: String,
    ): List<TransactionData>

    fun getRecentTransactionDataFlow(
        numberOfTransactions: Int,
    ): Flow<List<TransactionData>>

    fun getTransactionsBetweenTimestampsFlow(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): Flow<List<Transaction>>

    suspend fun getTransactionsBetweenTimestamps(
        startingTimestamp: Long,
        endingTimestamp: Long,
    ): List<Transaction>

    suspend fun getTransactionsCount(): Int

    suspend fun getTitleSuggestions(
        categoryId: Int,
        numberOfSuggestions: Int,
    ): List<String>

    suspend fun checkIfCategoryIsUsedInTransactions(
        categoryId: Int,
    ): Boolean

    suspend fun checkIfSourceIsUsedInTransactions(
        sourceId: Int,
    ): Boolean

    suspend fun checkIfTransactionForIsUsedInTransactions(
        transactionForId: Int,
    ): Boolean

    suspend fun getTransaction(
        id: Int,
    ): Transaction?

    suspend fun getTransactionData(
        id: Int,
    ): TransactionData?

    suspend fun insertTransaction(
        amountValue: Long,
        sourceFrom: Source?,
        sourceTo: Source?,
        transaction: Transaction,
    ): Long

    suspend fun insertTransactions(
        vararg transactions: Transaction,
    )

    suspend fun updateTransaction(
        transaction: Transaction,
    )

    suspend fun deleteTransaction(
        id: Int,
        vararg sources: Source,
    )

    suspend fun deleteAllTransactions()

    suspend fun restoreData(
        categories: List<Category>,
        emojis: List<EmojiLocalEntity>,
        sources: List<Source>,
        transactions: List<Transaction>,
        transactionForValues: List<TransactionFor>,
    )
}
