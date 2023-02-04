package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.database.category.model.Category
import com.makeappssimple.abhimanyu.financemanager.android.core.database.emoji.model.EmojiLocalEntity
import com.makeappssimple.abhimanyu.financemanager.android.core.database.source.model.Source
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionData
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model.TransactionFor
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    val allTransactions: Flow<List<Transaction>>

    fun getAllTransactionData(): Flow<List<TransactionData>>

    suspend fun getSearchedTransactionData(
        searchText: String,
    ): List<TransactionData>

    fun getRecentTransactionData(
        numberOfTransactions: Int,
    ): Flow<List<TransactionData>>

    fun getCurrentDayTransactions(): Flow<List<Transaction>>

    fun getCurrentMonthTransactions(): Flow<List<Transaction>>

    fun getCurrentYearTransactions(): Flow<List<Transaction>>

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
