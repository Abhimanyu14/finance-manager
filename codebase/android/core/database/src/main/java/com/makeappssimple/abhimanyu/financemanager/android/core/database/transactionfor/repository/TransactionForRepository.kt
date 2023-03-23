package com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.repository

import com.makeappssimple.abhimanyu.financemanager.android.core.database.transactionfor.model.TransactionFor
import kotlinx.coroutines.flow.Flow

interface TransactionForRepository {
    fun getAllTransactionForValuesFlow(): Flow<List<TransactionFor>>

    suspend fun getTransactionFor(
        id: Int,
    ): TransactionFor?

    suspend fun insertTransactionForValues(
        vararg transactionForValues: TransactionFor,
    )

    suspend fun updateTransactionForValues(
        vararg transactionForValues: TransactionFor,
    )

    suspend fun deleteTransactionFor(
        id: Int,
    )
}
