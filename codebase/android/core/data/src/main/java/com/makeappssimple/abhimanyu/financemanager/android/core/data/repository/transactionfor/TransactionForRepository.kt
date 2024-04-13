package com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transactionfor

import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import kotlinx.coroutines.flow.Flow

interface TransactionForRepository {
    fun getAllTransactionForValuesFlow(): Flow<List<TransactionFor>>

    suspend fun getAllTransactionForValues(): List<TransactionFor>

    suspend fun getTransactionFor(
        id: Int,
    ): TransactionFor?

    suspend fun insertTransactionForValues(
        vararg transactionForValues: TransactionFor,
    ): List<Long>

    suspend fun updateTransactionForValues(
        vararg transactionForValues: TransactionFor,
    ): Boolean

    suspend fun deleteTransactionFor(
        id: Int,
    ): Boolean
}
