package com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.Transaction
import com.makeappssimple.abhimanyu.financemanager.android.core.database.transaction.model.TransactionData
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    val allTransactions: Flow<List<Transaction>>

    fun getAllTransactionData(): Flow<List<TransactionData>>

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

    suspend fun getTransaction(
        id: Int,
    ): Transaction?

    suspend fun insertTransaction(
        transaction: Transaction,
    )

    suspend fun insertTransactions(
        vararg transactions: Transaction,
    )

    suspend fun updateTransaction(
        transaction: Transaction,
    )

    suspend fun updateTransactions(
        vararg transactions: Transaction,
    )

    suspend fun deleteTransaction(
        id: Int,
    )

    suspend fun deleteTransactions(
        vararg transactions: Transaction,
    )

    suspend fun deleteAllTransactions()
}
