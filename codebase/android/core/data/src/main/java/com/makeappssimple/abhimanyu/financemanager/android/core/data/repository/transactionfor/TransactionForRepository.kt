package com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transactionfor

import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import kotlinx.coroutines.flow.Flow

public interface TransactionForRepository {
    public fun getAllTransactionForValuesFlow(): Flow<List<TransactionFor>>

    public suspend fun getAllTransactionForValues(): List<TransactionFor>

    public suspend fun getTransactionFor(
        id: Int,
    ): TransactionFor?

    public suspend fun insertTransactionForValues(
        vararg transactionForValues: TransactionFor,
    ): List<Long>

    public suspend fun updateTransactionForValues(
        vararg transactionForValues: TransactionFor,
    ): Boolean

    public suspend fun deleteTransactionFor(
        id: Int,
    ): Boolean
}
