package com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transactionfor.fake

import com.makeappssimple.abhimanyu.financemanager.android.core.data.repository.transactionfor.TransactionForRepository
import com.makeappssimple.abhimanyu.financemanager.android.core.model.TransactionFor
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

public class FakeTransactionForRepositoryImpl : TransactionForRepository {
    override fun getAllTransactionForValuesFlow(): Flow<ImmutableList<TransactionFor>> {
        return flow {
            emptyList<TransactionFor>()
        }
    }

    override suspend fun getAllTransactionForValues(): ImmutableList<TransactionFor> {
        return persistentListOf()
    }

    override suspend fun getTransactionFor(
        id: Int,
    ): TransactionFor? {
        return null
    }

    override suspend fun insertTransactionForValues(
        vararg transactionForValues: TransactionFor,
    ): ImmutableList<Long> {
        return persistentListOf()
    }

    override suspend fun updateTransactionForValues(
        vararg transactionForValues: TransactionFor,
    ): Boolean {
        return false
    }

    override suspend fun deleteTransactionFor(
        id: Int,
    ): Boolean {
        return false
    }
}
