package com.makeappssimple.abhimanyu.financemanager.android.data.transaction.repository

import com.makeappssimple.abhimanyu.financemanager.android.entities.transaction.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    val transactions: Flow<List<Transaction>>

    suspend fun getTransaction(
        id: Int,
    ): Transaction?

    suspend fun insertTransaction(
        transaction: Transaction,
    )

    suspend fun insertTransactions(
        vararg transactions: Transaction,
    )

    suspend fun deleteTransaction(
        id: Int,
    )

    suspend fun deleteTransactions(
        vararg transactions: Transaction,
    )
}