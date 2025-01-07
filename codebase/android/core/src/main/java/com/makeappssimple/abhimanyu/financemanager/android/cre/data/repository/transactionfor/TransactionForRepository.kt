package com.makeappssimple.abhimanyu.financemanager.android.cre.data.repository.transactionfor

import com.makeappssimple.abhimanyu.financemanager.android.cre.model.TransactionFor
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

public interface TransactionForRepository {
    public fun getAllTransactionForValuesFlow(): Flow<ImmutableList<TransactionFor>>

    public suspend fun getAllTransactionForValues(): ImmutableList<TransactionFor>

    public suspend fun getTransactionFor(
        id: Int,
    ): TransactionFor?

    public suspend fun insertTransactionForValues(
        vararg transactionForValues: TransactionFor,
    ): ImmutableList<Long>

    public suspend fun updateTransactionForValues(
        vararg transactionForValues: TransactionFor,
    ): Boolean

    public suspend fun deleteTransactionFor(
        id: Int,
    ): Boolean
}
